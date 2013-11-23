package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.ArrayList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.utils.Utils;

public class QuestionTypeAnnotators extends JCasAnnotator_ImplBase {
  
  public final static int NORMAL = 0;
  public final static int QUANTITY = 1;  // how many
  public final static int REASON = 2;   // why
  public final static int CHOICE = 3;   // which of
  public final static int SUBTPYE = 4;  // which ***, *** is the type of the answer
  public final static int YES_OR_NO = 5; 
  
  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);
    for (int i = 0; i < qaSet.size(); i++) {
      Question question = qaSet.get(i).getQuestion();
      String text = question.getText();
      if (text.toLowerCase().contains("how many"))
        question.setQuestionType(QUANTITY);
      else
        question.setQuestionType(NORMAL);
    }
  }

}
