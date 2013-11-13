package edu.cmu.lti.deiis.hw5.annotators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Synonym;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class AcronymAnnotator extends JCasAnnotator_ImplBase{

  // Regex patterns (to find acronyms)
  // A) all UPPERCASE*            (ex: "IDE")
  // B) lowercase with no vowels* (ex: "sst")
  // C) MixedCase with uppercase in middle/end (ex: "LoB")
  //         *with/without numbers
  Pattern uppercasePattern=Pattern.compile("^[A-ZÃŸ]{2,6}[\\d]?$");
  Pattern lowercasePattern=Pattern.compile("^[bcdfghjklmnpqrstvwxz\\d]{2,4}$");
  Pattern mixedcasePattern=Pattern.compile("^[A-ZÃŸ]{1,6}[\\d]?[a-z]{0,3}[\\d]?[A-ZÃŸ]{1,6}[\\d]?[a-z\\d]{0,3}[\\d]?$");
  HashMap<String, ArrayList<Synonym>> acronymSynonymMap = new HashMap<String, ArrayList<Synonym>>();

  @Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

    // Loop through tokens from test doc
    ArrayList<Sentence> sentences = Utils.getSentenceListFromTestDocCAS(jCas);
    for (Sentence s : sentences) {
      ArrayList<Token> tokens = Utils.getTokenListFromSentenceList(s);
      annotateAcronyms(tokens, jCas);      
    }

    // Loop through tokens from source doc
    ArrayList<Sentence> sentences2 = Utils.getSentenceListFromSourceDocCAS(jCas);
    for (Sentence s : sentences2) {
      ArrayList<Token> tokens = Utils.getTokenListFromSentenceList(s);
      annotateAcronyms(tokens, jCas);      
    }

    // Loop through tokens from answers
    ArrayList<ArrayList<Answer>> answers = Utils.getAnswerListFromTestDocCAS(jCas);
    for (ArrayList<Answer> aList : answers) {
      for (Answer a : aList) {        
        ArrayList<Token> tokens = Utils.getTokenListFromAnswer(a);
        annotateAcronyms(tokens, jCas);      
      }
    }
	}

	private void annotateAcronyms(ArrayList<Token> tokens, JCas jCas) {

    // Loop through tokens, and Find something that looks like an acronym: 
    // A) all UPPERCASE*            (ex: "IDE")
    // B) lowercase with no vowels* (ex: "sst")
	  // C) MixedCase with uppercase in middle/end (ex: "LoB")
    //         *with/without numbers

	  int histLength = Math.min(5, tokens.size()-1);
	  
	  ArrayList<Token> prevTokens = new ArrayList<Token>(histLength);
    ArrayList<Token> nextTokens = new ArrayList<Token>(histLength);
	  
	  for (int i=0; i<tokens.size(); i++) {
	    // Store values of previous and next tokens
      if (i==0) {
        for (int j=1; j<=histLength; j++) {
          prevTokens.add(tokens.get(i));
          nextTokens.add(tokens.get(i+j));
        }
      } 
      else if (i < tokens.size()-histLength) {
        prevTokens.add(tokens.get(i-1));
        prevTokens.remove(0);
        nextTokens.add(tokens.get(i+histLength));
        nextTokens.remove(0);
      }
      else if (i < tokens.size()-histLength) {
        prevTokens.add(tokens.get(i-1));
        prevTokens.remove(0);
        nextTokens.remove(0);
      }

	    Token t = tokens.get(i);
      String text = t.getText();
      
      // If the token already exists in the acronym hashmap / DB, retrieve synonyms from hashmap.
      ArrayList<Synonym> existingSynonyms = this.acronymSynonymMap.get(t.getText());
      if (existingSynonyms != null) {
        addUpdateTokenSynonyms(t, existingSynonyms, jCas);
      } else {
          // Else: determine whether the token is an acronym
          // A) all UPPERCASE*            (ex: "IDE")
          Matcher upperMatch = uppercasePattern.matcher(text);
          while (upperMatch.find()) {
            confirmMatch(tokens.get(i), prevTokens, nextTokens, jCas);
          }
          // B) lowercase with no vowels* (ex: "sst")
          Matcher lowerMatch = lowercasePattern.matcher(text);
          while (lowerMatch.find()) {
            confirmMatch(tokens.get(i), prevTokens, nextTokens, jCas);        
          }
          // C) MixedCase with uppercase in middle/end (ex: "LoB")
          Matcher mixedMatch = mixedcasePattern.matcher(text);
          while (mixedMatch.find()) {
            confirmMatch(tokens.get(i), prevTokens, nextTokens, jCas);        
          }      
      }
    }
  }
	
  private void confirmMatch(Token t, ArrayList<Token> prevToks, ArrayList<Token> nextToks, JCas jCas) {
    
    String acronym = t.getText();
    boolean leftParensFound = false;
    boolean rightParensFound = false;
    boolean matchFound = false;
    HashMap<String, Integer> synonymMap= new HashMap<String, Integer>();


    // Determine if acronym is contained within parens
    for (int i=0; i<prevToks.size(); i++) {
      if (prevToks.get(i).getText().equalsIgnoreCase("(")) {
        leftParensFound = true;
        break;
      }
    }
    for (int i=0; i<nextToks.size(); i++) {
      if (nextToks.get(i).getText().equalsIgnoreCase(")")) {
        rightParensFound = true;
        break;
      }
    }
    
    // If acronym is contained within parens
    // 1) look to the left
    // 2) if preceding tokens form NP and the first letter of any token 
    //     = a letter within acronym
    // 3) then match.
    if (leftParensFound == true && rightParensFound == true) {
      for (int i=0; i<prevToks.size(); i++) {
        for (int j = 0; j < acronym.length(); j++){
          char c = acronym.charAt(j);
          if (Character.toLowerCase(prevToks.get(i).getText().charAt(0)) == Character.toLowerCase(c)) {
            matchFound = true;
            synonymMap.put(prevToks.get(i).getText(), 1);
          }                  
        }
      }    
    } else {
      // If acronym is not contained within parens
      // 1) look to the right
      // 2) if following tokens consist of parens with NP within, and the
      //      first letter of any token = a letter within acronym
      // 3) then match. 
      for (int i=0; i<nextToks.size(); i++) {
        for (int j = 0; j < acronym.length(); j++){
          char c = acronym.charAt(j);
          if (Character.toLowerCase(nextToks.get(i).getText().charAt(0)) == Character.toLowerCase(c)) {
            matchFound = true;
            synonymMap.put(prevToks.get(i).getText(), 1);
          }                  
        }
      }          
    }

    // If a match is found, store its expansion (noun phrase) as a synonym
    if (matchFound == true) {
      // Copy hashmap to ArrayList
      ArrayList<Synonym> newSynonyms = new ArrayList<Synonym>();
      for (Map.Entry<String, Integer> entry : synonymMap.entrySet())
      {
        Synonym newSynonym = new Synonym(jCas);
        newSynonym.setText(entry.getKey());
        newSynonyms.add(newSynonym);
      }
      addUpdateTokenSynonyms(t, newSynonyms, jCas);
      this.acronymSynonymMap.put(t.getText(), newSynonyms);

    }

  }

  private void addUpdateTokenSynonyms(Token t, ArrayList<Synonym> synonymList, JCas jCas) {
    
    // If token already has synonym list, append it to synonymList
    FSList prevSynonyms = t.getSynonyms();
    if (prevSynonyms == null) {
      // Do nothing        
    } else {
      boolean dupFlag = false;
      try {
        ArrayList<Synonym> prevSynonymsArrayList = Utils.fromFSListToCollection(prevSynonyms, Synonym.class);
        for (Synonym s : prevSynonymsArrayList) {
          for (Synonym s2 : synonymList) {
            if (s.getText().equalsIgnoreCase(s2.getText())) {
              dupFlag = true;
            }
          }
          if (dupFlag == false) {
            synonymList.add(s);          
          }
        }
      } catch (NullPointerException e) {
        // Some tokens seemed to not exist (caused null pointer exceptions). In this case, do not try to update them.
        return;
      }
      
    }

    // Set synonymList as the new FSList<Synonym> for the token
    FSList updatedSynonyms = Utils.fromCollectionToFSList(jCas, synonymList);
    t.setSynonyms(updatedSynonyms);
        
  }
}
