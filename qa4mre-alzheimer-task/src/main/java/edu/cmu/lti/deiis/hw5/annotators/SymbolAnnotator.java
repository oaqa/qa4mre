package edu.cmu.lti.deiis.hw5.annotators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Synonym;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class SymbolAnnotator extends JCasAnnotator_ImplBase{

  HashMap<String, String> symbolReplacements = new HashMap<String, String>();

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		
    symbolReplacements.put("ß", "beta"); 
		symbolReplacements.put("ÃŸ", "beta"); 
	}
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

    // Loop through tokens, and find non-English symbols in document.
    // If any token contains a non-English symbol, we will create a new
    // annotation that contains the English expansion of the symbol.

    // Loop through tokens from test doc
    ArrayList<Sentence> sentences = Utils.getSentenceListFromTestDocCAS(jCas);
    for (Sentence s : sentences) {
      ArrayList<Token> tokens = Utils.getTokenListFromSentenceList(s);
      annotateSymbols(tokens, jCas);      
    }

    // Loop through tokens from source doc
    ArrayList<Sentence> sentences2 = Utils.getSentenceListFromSourceDocCAS(jCas);
    for (Sentence s : sentences2) {
      ArrayList<Token> tokens = Utils.getTokenListFromSentenceList(s);
      annotateSymbols(tokens, jCas);      
    }

    // Loop through tokens from answers
    ArrayList<ArrayList<Answer>> answers = Utils.getAnswerListFromTestDocCAS(jCas);
    for (ArrayList<Answer> aList : answers) {
      for (Answer a : aList) {        
        ArrayList<Token> tokens = Utils.getTokenListFromAnswer(a);
        annotateSymbols(tokens, jCas);      
      }
    }
	}

	private void annotateSymbols(ArrayList<Token> tokens, JCas jCas) {
    for (Token t : tokens) {
      String text = t.getText();
      ArrayList<Synonym> synonyms = new ArrayList<Synonym>();
      // Loop through non-English symbols to see if token contains a non-English symbol
      for (Map.Entry<String, String> entry : symbolReplacements.entrySet())
      {
        String key = entry.getKey();
        String value = entry.getValue();
        Pattern pt = Pattern.compile(key);
        Matcher match= pt.matcher(text);
        while(match.find()) {
         String temp = text.replaceAll(match.group(), value);
         Synonym currentSynonym = new Synonym(jCas);
         currentSynonym.setText(temp);
         synonyms.add(currentSynonym);
        }
      }
      if (synonyms.size() > 0) {
        FSList tokenSynonyms = Utils.fromCollectionToFSList(jCas, synonyms);
        t.setSynonyms(tokenSynonyms);
      }
    }
  }
}
