package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateAnswer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.Corefcluster;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Phrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class AnswerChoiceCandAnsOriginalScorer extends JCasAnnotator_ImplBase {

  class ScoreWeightPair {
    double score;
    double weight;

    ScoreWeightPair(double score, double weight) {
      this.score = score;
      this.weight = weight;
    }
  }

  private SolrWrapper solrWrapper;
  int K_CANDIDATES = 5;
  int K_CAND_ANS = 3;

  public void initialize(UimaContext context)
      throws ResourceInitializationException {
    super.initialize(context);
    String serverUrl = (String) context
        .getConfigParameterValue("SOLR_SERVER_URL");
    K_CANDIDATES = (Integer) context.getConfigParameterValue("K_CANDIDATES");

    try {
      this.solrWrapper = new SolrWrapper(serverUrl);
      // loadStopWords(stopFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    // String testDocId = testDoc.getId();
    ArrayList<QuestionAnswerSet> qaSet = Utils
        .getQuestionAnswerSetFromTestDocCAS(aJCas);

    // Hash the co-reference cluster
    HashMap<Integer, Corefcluster> clusterHashMap = new HashMap<Integer, Corefcluster>();
    FSIndex ClusterIndex = aJCas.getAnnotationIndex(Corefcluster.type);
    Iterator ClusterIter = ClusterIndex.iterator();
    while (ClusterIter.hasNext()) {
      Corefcluster corefcluster = (Corefcluster) ClusterIter.next();
      clusterHashMap.put(corefcluster.getId(), corefcluster);
    }

    for (int i = 0; i < qaSet.size(); i++) {

      Question question = qaSet.get(i).getQuestion();
      System.out.println("Question: " + question.getText());
      ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet.get(i)
          .getAnswerList(), Answer.class);
      ArrayList<CandidateSentence> candSentList = Utils.fromFSListToCollection(
          qaSet.get(i).getCandidateSentenceList(), CandidateSentence.class);

      int topK = Math.min(K_CANDIDATES, candSentList.size());
      for (int c = 0; c < topK; c++) {

        CandidateSentence candSent = candSentList.get(c);
        Sentence sent = candSent.getSentence();

        ArrayList<NounPhrase> candSentNouns = Utils.fromFSListToCollection(
            candSent.getSentence().getPhraseList(), NounPhrase.class);
        ArrayList<NER> candSentNers = Utils.fromFSListToCollection(candSent
            .getSentence().getNerList(), NER.class);
        ArrayList<Phrase> candSentCoref = null;

        // initial coref list
        if (sent.getGenPhraseList() != null) {
          FSList fsPList = sent.getGenPhraseList();
          ArrayList<Phrase> phrases = Utils.fromFSListToCollection(fsPList,
              Phrase.class);
          for (int j = 0; j < phrases.size(); j++) {
            Phrase phrase = (Phrase) phrases.get(j);
            int clusterID = phrase.getCluster();
            Corefcluster corefcluster = clusterHashMap.get(clusterID);
            if (corefcluster.getChain() == null) {
              continue;
            }
            candSentCoref = Utils.fromFSListToCollection(
                corefcluster.getChain(), Phrase.class);
          }
        }

        ArrayList<CandidateAnswer> candAnsList = new ArrayList<CandidateAnswer>();
        ArrayList<ArrayList<ScoreWeightPair>> scores = new ArrayList<ArrayList<ScoreWeightPair>>();

        for (int j = 0; j < choiceList.size(); j++) {
          ArrayList<ScoreWeightPair> scoreList = new ArrayList<ScoreWeightPair>();
          scores.add(scoreList);
          Answer answer = choiceList.get(j);
          System.out.println("Calculating the "+j+"th answer scores.");
          double partialScore = 0.0;

          // PMI Score for QC+A(NP)
          {
            partialScore = 0.0;
            for (int k = 0; k < candSentNouns.size(); k++) {
              try {
                partialScore += scoreCoOccurInSameDoc(candSentNouns.get(k)
                    .getText(), choiceList.get(j));

              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }

          // PMI Score for QC+A(NER)
          {
            partialScore = 0.0;
            for (int k = 0; k < candSentNers.size(); k++) {

              try {
                partialScore += scoreCoOccurInSameDoc(candSentNers.get(k)
                    .getText(), choiceList.get(j));
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }

          // PMI Score for QC+A(Co-ref)
          {
            partialScore = 0.0;
            if (candSentCoref != null) {
              for (int k = 0; k < candSentCoref.size(); k++) {

                try {
                  partialScore += scoreCoOccurInSameDoc(candSentCoref.get(k)
                      .getText(), choiceList.get(j));
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }

          // NP & NE Overlap
          {
            int nnMatch = 0;
            ArrayList<NounPhrase> choiceNouns = Utils.fromFSListToCollection(
                answer.getNounPhraseList(), NounPhrase.class);
            ArrayList<NER> choiceNERs = Utils.fromFSListToCollection(
                answer.getNerList(), NER.class);
            for (int k = 0; k < candSentNouns.size(); k++) {
              for (int l = 0; l < choiceNERs.size(); l++) {
                if (candSentNouns.get(k).getText()
                    .contains(choiceNERs.get(l).getText())) {
                  nnMatch++;
                }
              }
              for (int l = 0; l < choiceNouns.size(); l++) {
                if (candSentNouns.get(k).getText()
                    .contains(choiceNouns.get(l).getText())) {
                  nnMatch++;
                }
              }
            }
            for (int k = 0; k < candSentNers.size(); k++) {
              for (int l = 0; l < choiceNERs.size(); l++) {
                if (candSentNers.get(k).getText()
                    .contains(choiceNERs.get(l).getText())) {
                  nnMatch++;
                }
              }
              for (int l = 0; l < choiceNouns.size(); l++) {
                if (candSentNers.get(k).getText()
                    .contains(choiceNouns.get(l).getText())) {
                  nnMatch++;
                }
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }

          // PMI QC+QAC(NP)
          {
            partialScore = 0.0;
            for (int k = 0; k < candSentNers.size(); k++) {

              try {
                partialScore += scoreCoOccurCandCandNP(candSentNers.get(k)
                    .getText(), choiceList.get(j));
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }
          
          // PMI Score for QC+QAC(NER)
          {
            partialScore = 0.0;
            for (int k = 0; k < candSentNers.size(); k++) {

              try {
                partialScore += scoreCoOccurCandCandNER(candSentNers.get(k)
                    .getText(), choiceList.get(j));
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }

          // PMI Score for QC+QAC(Co-ref)
          {
            partialScore = 0.0;
            if (candSentCoref != null) {
              for (int k = 0; k < candSentCoref.size(); k++) {

                try {
                  partialScore += scoreCoOccurCandCandCoref(candSentCoref.get(k)
                      .getText(), choiceList.get(j), clusterHashMap);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
            scoreList.add(new ScoreWeightPair(partialScore, 1.0));
          }
        }

        // Re-scale the score to range(0,1)
        for (int j = 0; j < scores.get(0).size(); j++) {
          double max = Double.MIN_VALUE;
          double min = Double.MAX_VALUE;
          for (int k = 0; k < choiceList.size(); k++) {
            double tempScore = scores.get(k).get(j).score;
            if (tempScore > max)
              max = tempScore;
            if (tempScore < min)
              min = tempScore;
          }
          double range = max - min;
          if (range == 0)
            range = 1;
          for (int k = 0; k < choiceList.size(); k++) {
            double tempScore = scores.get(k).get(j).score;
            scores.get(k).get(j).score = (tempScore - min) / range;
          }
        }

        for (int j = 0; j < choiceList.size(); j++) {
          double score1 = calculateFinalScore(scores.get(j));
          Answer answer = choiceList.get(j);
          int cardinality = answer.getMatchesQuestionCardinality();
          boolean entityType = answer.getMatchesQuestionEntityType();
          if (cardinality == 0)
            score1 *= 0.5;
          else if (cardinality == 1)
            score1 *= 1.0;
          if (!entityType)
            score1 *= 0.5;

          System.out.println(choiceList.get(j).getText() + " " + score1);

          CandidateAnswer candAnswer = null;
          if (candSent.getCandAnswerList() == null) {
            candAnswer = new CandidateAnswer(aJCas);
          } else {
            candAnswer = Utils.fromFSListToCollection(
                candSent.getCandAnswerList(), CandidateAnswer.class).get(j);// new
                                                                            // CandidateAnswer(aJCas);;
          }
          candAnswer.setText(answer.getText());
          candAnswer.setQId(answer.getQuestionId());
          candAnswer.setChoiceIndex(j);
          candAnswer.setFinalScore(score1);
          candAnsList.add(candAnswer);
        }

        FSList fsCandAnsList = Utils.fromCollectionToFSList(aJCas, candAnsList);
        candSent.setCandAnswerList(fsCandAnsList);
        candSentList.set(c, candSent);
      }

      System.out.println("================================================");
      FSList fsCandSentList = Utils.fromCollectionToFSList(aJCas, candSentList);
      qaSet.get(i).setCandidateSentenceList(fsCandSentList);

    }
    FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
    testDoc.setQaList(fsQASet);

  }

  public double scoreCoOccurInSameDoc(String question, Answer choice)
      throws Exception {
    ArrayList<NounPhrase> choiceNounPhrases = Utils.fromFSListToCollection(
        choice.getNounPhraseList(), NounPhrase.class);
    double score = 0.0;

    for (int i = 0; i < choiceNounPhrases.size(); i++) {
      String choiceNounPhrase = choiceNounPhrases.get(i).getText();
      score += getCoOccur(question, choiceNounPhrase);
    }
    return score;
  }
  
  public double scoreCoOccurCandCandNP(String question, Answer choice)
      throws Exception {
    
    double score = 0.0;
    ArrayList<CandidateSentence> candSentList = Utils.fromFSListToCollection(
        choice.getCandidateSentenceList(), CandidateSentence.class);
    int topK = Math.min(K_CANDIDATES, candSentList.size());
    for (int j = 0; j < topK; j++)
    {
      Sentence sent = candSentList.get(j).getSentence();
      
      // NP
      ArrayList<NounPhrase> choiceNounPhrases = Utils.fromFSListToCollection(
          sent.getPhraseList(), NounPhrase.class);
      for (int i = 0; i < choiceNounPhrases.size(); i++) {
        String choiceNounPhrase = choiceNounPhrases.get(i).getText();
        score += getCoOccur(question, choiceNounPhrase);
      }
    }
    return score;
  }
  
  public double scoreCoOccurCandCandNER(String question, Answer choice)
      throws Exception {
    
    double score = 0.0;
    ArrayList<CandidateSentence> candSentList = Utils.fromFSListToCollection(
        choice.getCandidateSentenceList(), CandidateSentence.class);
    int topK = Math.min(K_CANDIDATES, candSentList.size());
    for (int j = 0; j < topK; j++)
    {
      Sentence sent = candSentList.get(j).getSentence();
      
      // NER
      ArrayList<NounPhrase> choiceNER = Utils.fromFSListToCollection(
          sent.getNerList(), NounPhrase.class);
      for (int i = 0; i < choiceNER.size(); i++) {
        String choiceNounPhrase = choiceNER.get(i).getText();
        score += getCoOccur(question, choiceNounPhrase);
      }
    }
    return score;
  }
  
  public double scoreCoOccurCandCandCoref(String question, Answer choice, HashMap<Integer, Corefcluster> clusterHashMap)
      throws Exception {
    
    double score = 0.0;
    ArrayList<CandidateSentence> candSentList = Utils.fromFSListToCollection(
        choice.getCandidateSentenceList(), CandidateSentence.class);
    int topK = Math.min(K_CAND_ANS, candSentList.size());
    for (int k = 0; k < topK; k++)
    {
      Sentence sent = candSentList.get(k).getSentence();
      ArrayList<Phrase> candSentCoref = null;
      if (sent.getGenPhraseList() != null) {
        FSList fsPList = sent.getGenPhraseList();
        ArrayList<Phrase> phrases = Utils.fromFSListToCollection(fsPList,
            Phrase.class);
        for (int j = 0; j < phrases.size(); j++) {
          Phrase phrase = (Phrase) phrases.get(j);
          int clusterID = phrase.getCluster();
          Corefcluster corefcluster = clusterHashMap.get(clusterID);
          if (corefcluster.getChain() == null) {
            continue;
          }
          candSentCoref = Utils.fromFSListToCollection(
              corefcluster.getChain(), Phrase.class);
        }
      }
      if (candSentCoref != null)
      {
        for (int i = 0; i < candSentCoref.size(); i++) {
          String choiceNounPhrase = candSentCoref.get(i).getText();
          score += getCoOccur(question, choiceNounPhrase);
        }
      }
    }
    return score;
  }
  
  double getCoOccur(String t1, String t2)
  {
    double ret = 0.0;
    if (t1.split("[ ]").length > 1) {
      t1 = "\"" + t1 + "\"";
    }
    if (t2.split("[ ]").length > 1) {
      t2 = "\"" + t2 + "\"";
    }

    String query = t1 + " AND " + t2;
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("q", query);
    params.put("rows", "1");
    SolrParams solrParams = new MapSolrParams(params);
    QueryResponse rsp = null;
    long combinedHits = 0;
    try {
      rsp = solrWrapper.getServer().query(solrParams);
      combinedHits = rsp.getResults().getNumFound();
    } catch (Exception e) {
      // System.out.println(e + "\t" + query);
    }

    query = t2;
    params = new HashMap<String, String>();
    params.put("q", query);
    params.put("rows", "1");
    solrParams = new MapSolrParams(params);

    long nHits1 = 0;
    try {
      rsp = solrWrapper.getServer().query(solrParams);
      nHits1 = rsp.getResults().getNumFound();
    } catch (Exception e) {
      // System.out.println(e+"\t"+query);
    }
    if (nHits1 != 0) {
      ret += (double) combinedHits / nHits1;
    }
    return ret;
  }

  private double calculateFinalScore(ArrayList<ScoreWeightPair> scoreList) {
    double scoreSum = 0.0;
    double weightSum = 0.0;
    for (ScoreWeightPair pair : scoreList) {
      scoreSum += pair.score * pair.weight;
      weightSum += pair.weight;
    }
    return scoreSum / weightSum;
  }

}
