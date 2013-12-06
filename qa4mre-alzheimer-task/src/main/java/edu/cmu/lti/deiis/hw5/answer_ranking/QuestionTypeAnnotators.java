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

  public final static int QUANTITY = 1; // how many

  public final static int REASON = 2; // why

  public final static int CHOICE = 3; // which of

  public final static int SUBTYPE = 4; // which ***, *** is the type of the answer

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
      String text = question.getText().toLowerCase();
      if (text.contains("how many") || text.contains("how old") || text.contains("how long")) {
        question.setQuestionType(QUANTITY);
        continue;
      }
      if (text.startsWith("is") || text.startsWith("are")) {
        question.setQuestionType(YES_OR_NO);
        continue;
      }
      if (text.startsWith("why")){
        question.setQuestionType(REASON);
        continue;
      }
      if (text.startsWith("what experimental")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("experimental"); // ChIP, promoter deletion experiments, electrophoretic mobility shift assay,immunostaining
        continue;
      }
      if (text.startsWith("what software")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("software"); // MatInspector
        continue;
      }
      if (text.startsWith("what organism")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("organism"); // mouse
        continue;
      }
      if (text.startsWith("which is the part of the human body")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("human body"); // head
        continue;
      }
      if (text.startsWith("what target gene")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("target gene"); // Seladin-1,APP
        continue;
      }
      if (text.startsWith("what is the gene symbol")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("gene symbol"); // BACE, microtubule-associated protein tau, APOE
        continue;
      }
      if (text.startsWith("what is the long name")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("long name"); // selective AD indicator 1
        continue;
      }
      if (text.startsWith("what method")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("method"); // ChIP
        continue;
      }
      if (text.startsWith("what is the target gene")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("target gene"); // Seladin-1,APP
        continue;
      }
      
      if (text.startsWith("what program")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("program"); // WebPrank
        continue;
      }
      
      if (text.startsWith("what specific protein forms")){
        question.setQuestionType(SUBTYPE);
        question.setAnswerClass("specific protein forms"); // APC
        continue;
      }
      
      
      
      question.setQuestionType(NORMAL);
    }
  }

}
