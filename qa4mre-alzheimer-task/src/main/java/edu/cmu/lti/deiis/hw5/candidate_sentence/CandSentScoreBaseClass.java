package edu.cmu.lti.deiis.hw5.candidate_sentence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

public class CandSentScoreBaseClass extends JCasAnnotator_ImplBase {

  int TOP_SEARCH_RESULTS = 3;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
  }

  public double getScore(Sentence sentence, Question question, Answer answer,
          Hashtable<Sentence, String> logTable) {

    double[] s1 = ScoreUtils.CalcScore(sentence, question, answer, ScoreUtils.UNIGRAM,
            ScoreUtils.MATCH);
    double[] s3 = ScoreUtils.CalcScore(sentence, question, answer, ScoreUtils.UNIGRAM,
            ScoreUtils.COSINE);
    double[] s2 = ScoreUtils.CalcScore(sentence, question, answer, ScoreUtils.NOUN_PHRASE,
            ScoreUtils.COSINE);
    // Writing log for debug
    StringBuilder sb = new StringBuilder();
    sb.append("Unigram, s - q: ");
    sb.append(s1[0] + "\n");
    sb.append("Unigram, s - a: ");
    sb.append(s1[1] + "\n");
    sb.append("NP, s - q: ");
    sb.append(s2[0] + "\n");
    sb.append("NP, s - a: ");
    sb.append(s2[1] + "\n");
    logTable.put(sentence, sb.toString());
    
    
    if (s1[1] == 0.0)
      return 0.0;
    else
      return s2[0] + s2[1];
//    double score = s2[0]; // + s2[1];
//    if (score == 0.0)
//      score = s3[0];// + s3[1];
//    return score;
  }

  public ArrayList<CandidateSentence> rankTopKSentence(List<Sentence> sentenceList,
          Question question, Answer answer, int k, JCas aJCas) {
    ArrayList<CandidateSentence> results = new ArrayList<CandidateSentence>();
    Hashtable<Sentence, String> logTable = new Hashtable<Sentence, String>();
    for (Sentence sentence : sentenceList) {
      CandidateSentence cand = new CandidateSentence(aJCas);
      cand.setSentence(sentence);
      cand.setRelevanceScore(getScore(sentence, question, answer, logTable));
      results.add(cand);
    }
    Collections.sort(results, new Comparator<CandidateSentence>() {
      @Override
      public int compare(CandidateSentence arg0, CandidateSentence arg1) {
        if (arg0.getRelevanceScore() < arg1.getRelevanceScore())
          return 1;
        else
          return -1;
      }
    });
    ArrayList<CandidateSentence> ret = new ArrayList<CandidateSentence>();
    for (int i = 0; i < k && i < results.size(); i++)
      ret.add(results.get(i));
    if (answer.getIsCorrect()) {
      try {
        PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));

        writer.println("Q: " + question.getText() + "\n");
        writer.println("A: " + answer.getIsCorrect() + " " + answer.getText() + "\n");
        for (CandidateSentence cand : ret) {
          writer.println(cand.getRelevanceScore() + "\t" + cand.getSentence().getText() + "\n");
          writer.println(logTable.get(cand.getSentence()));
        }
        writer.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return ret;
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    ArrayList<Sentence> sentenceList = Utils.getSentenceListFromTestDocCAS(aJCas);
    try {
      PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));
      for (Sentence sentence : sentenceList) {
        for (Token token : Utils.getTokenListFromSentenceList(sentence))
          writer.append(token.getText() + " ");
        writer.append("\n");
      }
      writer.close();
    } catch (Exception e) {

    }
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);

    for (int i = 0; i < qaSet.size(); i++) {
      Question question = qaSet.get(i).getQuestion();
      ArrayList<Answer> answers = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      ArrayList<CandidateSentenceAnswerSet> candForPair = new ArrayList<CandidateSentenceAnswerSet>();

      for (Answer answer : answers) {

        CandidateSentenceAnswerSet candSet = new CandidateSentenceAnswerSet(aJCas);
        candSet.setAnswer(answer);

        ArrayList<CandidateSentence> rankTopK = rankTopKSentence(sentenceList, question, answer,
                TOP_SEARCH_RESULTS, aJCas);
        FSList fsCandidateSentList = Utils.fromCollectionToFSList(aJCas, rankTopK);
        fsCandidateSentList.addToIndexes();

        candSet.setCandidateSentenceList(fsCandidateSentList);
        candSet.addToIndexes();
        candForPair.add(candSet);
      }

      FSList candSet = Utils.fromCollectionToFSList(aJCas, candForPair);
      candSet.addToIndexes();
      qaSet.get(i).setCandidateSets(candSet);

      // System.out.println("=========================================================");
    }

    FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
    testDoc.setQaList(fsQASet);

  }

}