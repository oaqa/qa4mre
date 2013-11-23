package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.ArrayList;

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

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
  }

  private boolean allDigits(String str) {
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (!(c >= '0' && c <= '9'))
        return false;
    }
    return true;
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
            if (allDigits(token.getText())) {
              answer.setTypeMatchScore(1.0);
              break;
            }
          }
        }
      }
    }
  }
}