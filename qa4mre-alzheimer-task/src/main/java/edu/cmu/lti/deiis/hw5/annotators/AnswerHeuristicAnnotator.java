package edu.cmu.lti.deiis.hw5.annotators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.EmptyFSList;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.Dependency;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Synonym;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

/**
 * For a given Question and its candidate Answers, this module:
 * 1) Determines the Question cardinality (e.g. is it asking for something singular or
 * plural), and updates the Question.cardinality feature (1 = Singular, 2 = Plural, 
 * 3 = Unknown).
 * 2) Determines the Question target entity (e.g. is it expecting an answer that is 
 * an Integer, a Double, a Date, or a generic Entity), and updates the 
 * Question.entityType feature ("Integer", "Double", "DateTime", "Entity").
 * 3) Determines whether the Answer cardinality matches the Question cardinality, then 
 * sets the Answer.matchesQuestionCardinality feature (0 = false, 1 = true, 
 * 2 = unknown).
 * 4) Determines whether the Answer entity type matches the Question entity type, then
 * sets the Answer.matchesQuestionEntityType feature (True, False).
 * 
 * These flags can then be used downstream during Answer scoring.
 */
public class AnswerHeuristicAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {

    /*
    */

    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(jCas);
    for (int i = 0; i < qaSet.size(); i++) {
      Question question = qaSet.get(i).getQuestion();
      // System.out.println("Question: " + question.getText());

      // Determine Question entity type (Integer, Double, DateTime, or Entity)
      setEntityType(question);
      // System.out.println(question.getEntityType());

      // Determine Question cardinality (1 - Singular, 2 - Plural, 3 - Unknown)
      setCardinality(question);
      // System.out.println(question.getCardinality());

      Set<String> utarget = findTarget(question);
      // for(String st:utarget){
      // System.out.print(st+",");
      // }
      // System.out.println();
      ArrayList<Synonym> synt = new ArrayList<Synonym>();
      for (String tar : utarget) {
        Synonym s = new Synonym(jCas);
        s.setText(tar);
        synt.add(s);
      }
      FSList target = Utils.fromCollectionToFSList(jCas, synt);
      question.setTarget(target);
      // Determine whether each answer matches the Question cardinality / entity type, or not
      ArrayList<Answer> answerList = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      for (int k = 0; k < answerList.size(); k++) {
        Answer answer = answerList.get(k);
        FSList tokenList = answer.getTokenList();
        int j = 0;
        int answerCardinality = 3; // Unknown for now
        mainLoop1: while (true) {
          Token token = null;
          try {
            token = (Token) tokenList.getNthElement(j);
            // System.out.println(token);
            // If the token is a noun, check to see if it is single / plural
            // But do not break - continue, in case a first noun is NN and a second is NNS
            if (token.getPos().substring(0, 1).equalsIgnoreCase("N")) {
              if (token.getPos().substring(token.getPos().length() - 1).equalsIgnoreCase("S")) {
                answerCardinality = 2;
              } else {
                answerCardinality = 1;
              }
            }
            // If the token is "and" or "or", cardinality is definitely plural, so break.
            if (token.getText().equalsIgnoreCase("and") || token.getText().equalsIgnoreCase("or")) {
              answerCardinality = 2;
              break mainLoop1;
            }

          } catch (Exception e) {
            break;
          }
          j++;
        }

        j = 0;
        String answerEntityType = "Entity";
        Pattern integerPattern = Pattern.compile("^\\d+$");
        Pattern doublePattern = Pattern.compile("^\\d+.\\d+$");
        mainLoop2: while (true) {
          Token token = null;
          try {
            token = (Token) tokenList.getNthElement(j);
            // System.out.println(token);
            // Determine whether answer is Integer, Double, or DateTime (default is Entity)
            Matcher integerMatch = integerPattern.matcher(token.getText());
            while (integerMatch.find()) {
              answerEntityType = "Integer";
              break mainLoop2;
            }
            Matcher doubleMatch = doublePattern.matcher(token.getText());
            while (doubleMatch.find()) {
              answerEntityType = "Double";
              break mainLoop2;
            }
            if (token.getText().equalsIgnoreCase("before")
                    || token.getText().equalsIgnoreCase("after")
                    || token.getText().equalsIgnoreCase("during")) {
              answerEntityType = "DateTime";
              break mainLoop2;
            }

          } catch (Exception e) {
            break;
          }
          j++;
        }

        // If cardinality matches (or either question or answer cardinality is unknown)
        // consider them matched
        // 0 = false
        // 1 = true
        // 2 = unknown
        if (answerCardinality == question.getCardinality()) {
          answer.setMatchesQuestionCardinality(1);
        } else if (question.getCardinality() == 3 || answerCardinality == 3) {
          answer.setMatchesQuestionCardinality(2);
        } else {
          answer.setMatchesQuestionCardinality(0);
        }

        // If entity types match consider them matched
        if (answerEntityType == question.getEntityType()) {
          answer.setMatchesQuestionEntityType(true);
        } else {
          answer.setMatchesQuestionEntityType(false);
        }

      }

    }

  }

  private void setEntityType(Question question) {

    // Determine the entityType of the question:
    // "Integer" - question is looking for target entity that is an integer value (or NP containing
    // integer value)
    // "Double" - question is looking for target entity that is a double value (or NP containing
    // double value)
    // "DateTime" - question is looking for target entity that contains date or time information
    // (date, event, relative time, etc)\
    // "Entity" - question is looking for target entity that is a "thing" - not a quantity or date
    //
    // NOTE: this implementation uses a naive approach - simply assuming that the question words
    // ("When", "How many", etc)
    // appear at the beginning of the sentence. While this approach works for this dataset, a more
    // robust
    // implementation may be required for datasets that contain questions phrased differently.
    if (question.getText().contains("How many")) { // Discrete quantities
      question.setEntityType("Integer");
    } else if (question.getText().contains("How much")) { // Continuous quantities
      question.setEntityType("Double");
    } else if (question.getText().contains("When")) { // DateTime events
      question.setEntityType("DateTime");
    } else {
      question.setEntityType("Entity"); // Entities ("things"
    }

  }

  private Set<String> findTarget(Question question) {
    // System.out.println(question.getText());
    FSList Fdeplist = question.getDependencies();
    ArrayList<Token> target = new ArrayList<Token>();
    ArrayList<Token> govs = new ArrayList<Token>();
    ArrayList<Token> tlist = Utils.fromFSListToCollection(question.getTokenList(), Token.class);

    // System.out.println();
    ArrayList<Dependency> deplist = Utils.fromFSListToCollection(Fdeplist, Dependency.class);
    HashMap<Token, Token> depmap = new HashMap<Token, Token>();
    Token kind = null;
    for (Dependency dep : deplist) {
      Token depd = dep.getDependent();
      Token gov = dep.getGovernor();
      govs.add(gov);
      depmap.put(depd, gov);

      if (depd.getPos().startsWith("W")) {
        kind = depd;
      }
    }
    Token t = depmap.get(kind);
    ArrayList<String> commonl = new ArrayList<String>();
    ArrayList<String> keyl = new ArrayList<String>();
    ArrayList<String> govl = new ArrayList<String>();
    for (Token tok : tlist) {
      commonl.add(tok.getText());
    }
    for (Token tok : govs) {
      govl.add(tok.getText());
    }
    for (Token tok : depmap.keySet()) {
      keyl.add(tok.getText());
    }
    commonl.removeAll(keyl);
    commonl.retainAll(govl);
    if (commonl.size() != 1) {
      target.add(kind);
    } else {
      String head = commonl.get(0);
      target.add(kind);
      for (Dependency dep : deplist) {
        Token depd = dep.getDependent();
        Token gov = dep.getGovernor();
        if (gov.getText().equals(head) == false) {
          target.add(gov);
          target.add(depd);
        } else {
          break;
        }
      }
    }
    ArrayList<String> tarl = new ArrayList<String>();

    for (Token tk : target) {
      if (tk == null)
        continue;
      tarl.add(tk.getText());
    }

    Set<String> utarget = new HashSet<String>(tarl);

    return utarget;
  }

  private void dfs(HashMap<Token, Token> depmap, Token seed, ArrayList<Token> target) {
    // System.out.println(seed.getText());
    System.out.println("Seed: " + seed.getText());
    if (depmap.containsKey(seed)) {
      // System.out.println("No Seed: "+seed.getText());
      // for (Token tkns:depmap.get(seed)){
      // System.out.println(tkns);

      Token tkns = depmap.get(seed);
      target.add(tkns);
      // System.out.println("Seed: "+seed.getText()+" val: "+tkns.getText());
      for (Token key : depmap.keySet()) {
        if (key.getText().equals(tkns.getText())) {
          tkns = key;
          break;
        }
      }
      dfs(depmap, tkns, target);
      // }
    }
  }

  private void setCardinality(Question question) {

    // Determine cardinality of the question:
    // 1 - Singular (question target entity is singular)
    // 2 - Plural (question target entity is plural)
    // 3 - Unknown
    FSList dependencyList = question.getDependencies();
    int j = 0;
    Boolean qWordSeen = false;
    int cardinality = 3;
    // First, try to determine plurality based upon nouns / NPs
    mainLoop3: while (true) {
      Dependency dependency = null;
      try {
        dependency = (Dependency) dependencyList.getNthElement(j);
        // System.out.println(dependency);
        Token governor = dependency.getGovernor();
        Token dependent = dependency.getDependent();
        // We're looking for nouns / NPs that occur BETWEEN
        // the question word ("What", "Where", etc) and the first verb
        if (dependent.getPos().substring(0, 1).equalsIgnoreCase("W")) {
          qWordSeen = true;
        }
        if (governor.getPos().substring(0, 1).equalsIgnoreCase("N") && qWordSeen == true) {
          // If the noun / NP is plural, set cardinality to 2
          if (governor.getPos().substring(governor.getPos().length() - 1).equalsIgnoreCase("S")) {
            cardinality = 2;
          }
          // Otherwise, set cardinality to 1
          else {
            cardinality = 1;
          }
        }
        if (governor.getPos().substring(0, 1).equalsIgnoreCase("V")
                || dependent.getPos().substring(0, 1).equalsIgnoreCase("V")) {
          break mainLoop3;
        }
      } catch (Exception e) {
        break;
      }
      j++;
    }
    // If cardinality is still unknown, try another loop to look for cue words.
    // "is" --> singular, "are" --> plural, etc
    if (cardinality == 3) {
      mainLoop4: while (true) {
        Dependency dependency = null;
        try {
          dependency = (Dependency) dependencyList.getNthElement(j);
          // System.out.println(dependency);
          Token governor = dependency.getGovernor();
          Token dependent = dependency.getDependent();
          if (dependent.getText().equalsIgnoreCase("where")
                  || governor.getText().equalsIgnoreCase("where")) {
            cardinality = 3;
            break mainLoop4;
          }
          if (dependent.getText().equalsIgnoreCase("is")
                  || dependent.getText().equalsIgnoreCase("was")
                  || dependent.getText().equalsIgnoreCase("does")
                  || dependent.getText().equalsIgnoreCase("has")
                  || governor.getText().equalsIgnoreCase("is")
                  || governor.getText().equalsIgnoreCase("was")
                  || governor.getText().equalsIgnoreCase("does")
                  || governor.getText().equalsIgnoreCase("has")) {
            cardinality = 1;
            break mainLoop4;
          }
          if (dependent.getText().equalsIgnoreCase("are")
                  || dependent.getText().equalsIgnoreCase("were")
                  || dependent.getText().equalsIgnoreCase("do")
                  || dependent.getText().equalsIgnoreCase("have")
                  || governor.getText().equalsIgnoreCase("are")
                  || governor.getText().equalsIgnoreCase("were")
                  || governor.getText().equalsIgnoreCase("do")
                  || governor.getText().equalsIgnoreCase("have")) {
            cardinality = 2;
            break mainLoop4;
          }
        } catch (Exception e) {
          break mainLoop4;
        }
        j++;
      }
    }
    question.setCardinality(cardinality);

  }

}
