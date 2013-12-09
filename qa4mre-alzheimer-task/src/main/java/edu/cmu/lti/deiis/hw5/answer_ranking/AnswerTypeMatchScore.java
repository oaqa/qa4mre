package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class AnswerTypeMatchScore extends JCasAnnotator_ImplBase {
  Hashtable<String, HashSet<String>> classTable;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    classTable = new Hashtable<String, HashSet<String>>();

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader("data/taxonomy.txt"));
      String className = null;
      String line = reader.readLine();
      while (line != null) {
        if (className == null) {
          className = line;
          line = reader.readLine();
          continue;
        }
        if (line.equals("")) {
          className = reader.readLine();
          line = reader.readLine();
          continue;
        }
        if (!classTable.containsKey(className)) {
          classTable.put(className, new HashSet<String>());
        }
        classTable.get(className).add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private boolean allDigits(String str) {
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (!(c >= '0' && c <= '9'))
        return false;
    }
    return true;
  }

  public boolean specialToken(String token) {
    if (token.equals("one") || token.equals("two") || token.equals("two") || token.equals("three")
            || token.equals("four") || token.equals("five") || token.equals("six")
            || token.equals("seven") || token.equals("eight") || token.equals("night")
            || token.equals("zero"))
      return true;
    else
      return false;
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);
    for (int i = 0; i < qaSet.size(); i++) {
      Question question = qaSet.get(i).getQuestion();
      ArrayList<Answer> answers = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      if (question.getQuestionType() == QuestionTypeAnnotators.NORMAL)
        continue;
      if (question.getQuestionType() == QuestionTypeAnnotators.QUANTITY) {
        for (Answer answer : answers) {
          ArrayList<Token> tokens = Utils
                  .fromFSListToCollection(answer.getTokenList(), Token.class);
          answer.setTypeMatchScore(0.0);
          for (Token token : tokens) {
            if (allDigits(token.getText()) || specialToken(token.getText().toLowerCase())) {
              answer.setTypeMatchScore(1.0);
              break;
            }
          }
        }
        continue;
      }
      if (question.getQuestionType() == QuestionTypeAnnotators.SUBTYPE) {
        if (classTable.containsKey(question.getAnswerClass())) {
          HashSet<String> possible_value = classTable.get(question.getAnswerClass());
          for (Answer answer : answers) {
            if (possible_value.contains(answer.getText())) {
              answer.setTypeMatchScore(1.5);
              break;
            }
          }
        }
      }
      if (question.getQuestionType() == QuestionTypeAnnotators.YES_OR_NO) {
        for (Answer answer : answers) {
          if (answer.getText().toLowerCase().contains("yes")){
            answer.setTypeMatchScore(0.6);
          }else{
            if (answer.getText().toLowerCase().contains("no")){
              answer.setTypeMatchScore(0.3);
            }
          }
          
        }
      }
    }
  }
}