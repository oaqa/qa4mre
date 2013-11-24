package edu.cmu.lti.deiis.hw5.candidate_sentence;

import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Synonym;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class QuestionCandSentSimilarityMatcher extends JCasAnnotator_ImplBase {

  SolrWrapper solrWrapper = null;

  String serverUrl;

  // IndexSchema indexSchema;
  String coreName;

  String schemaName;

  int TOP_SEARCH_RESULTS = 10;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    serverUrl = (String) context.getConfigParameterValue("SOLR_SERVER_URL");
    coreName = (String) context.getConfigParameterValue("SOLR_CORE");
    schemaName = (String) context.getConfigParameterValue("SCHEMA_NAME");
    TOP_SEARCH_RESULTS = (Integer) context.getConfigParameterValue("TOP_SEARCH_RESULTS");
    try {
      this.solrWrapper = new SolrWrapper(serverUrl + coreName);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    String testDocId = testDoc.getId();
    ArrayList<Sentence> sentenceList = Utils.getSentenceListFromTestDocCAS(aJCas);
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);

    for (int i = 0; i < qaSet.size(); i++) {

      Question question = qaSet.get(i).getQuestion();
      ArrayList<Synonym> synt=Utils.fromFSListToCollection(question.getTarget(), Synonym.class);
      //for(Synonym s:synt){
       // System.out.print("Test:"+s.getText()+",");;
      //}
      //System.out.println();
      System.out.println("========================================================");
      System.out.println("Question: " + question.getText());
      String searchQuery = this.formSolrQuery(question);
      if (searchQuery.trim().equals("")) {
        continue;
      }
      ArrayList<CandidateSentence> candidateSentList = new ArrayList<CandidateSentence>();
      SolrQuery solrQuery = new SolrQuery();
      solrQuery.add("fq", "docid:" + testDocId);
      solrQuery.add("q", searchQuery);
      solrQuery.add("rows", String.valueOf(TOP_SEARCH_RESULTS));
      solrQuery.setFields("*", "score");
      
      try {
        SolrDocumentList results = solrWrapper.runQuery(solrQuery, TOP_SEARCH_RESULTS);
        System.out.println("results size:" + results.size());
        for (int j = 0; j < results.size(); j++) {
          SolrDocument doc = results.get(j);
          String sentId = doc.get("id").toString();
          String docId = doc.get("docid").toString();
          if (!testDocId.equals(docId)) {
            continue;
          }
          String sentIdx = sentId.replace(docId, "").replace("_", "").trim();
          int idx = Integer.parseInt(sentIdx);

          // Sentence annSentence=sentenceList.get(idx);
          // System.out.println(idx);
          Sentence annSentence = null;
          if (sentenceList.size() > idx) {
            annSentence = sentenceList.get(idx);
          } else {
            continue;
          }

          String sentence = doc.get("text").toString();
          double relScore = Double.parseDouble(doc.get("score").toString());
          CandidateSentence candSent = new CandidateSentence(aJCas);
          candSent.setSentence(annSentence);
          candSent.setRelevanceScore(relScore);
          candidateSentList.add(candSent);
          System.out.println(relScore + "\t" + sentence);
        }
        FSList fsCandidateSentList = Utils.fromCollectionToFSList(aJCas, candidateSentList);
        fsCandidateSentList.addToIndexes();
        qaSet.get(i).setCandidateSentenceList(fsCandidateSentList);
        qaSet.get(i).addToIndexes();

      } catch (SolrServerException e) {
        e.printStackTrace();
      }

      // for answer
      ArrayList<Answer> answers = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      for (int answerNum = 0; answerNum < answers.size(); answerNum++) {
        Answer answer = answers.get(answerNum);
        System.out.println("--------------------------------------------------------");
        System.out.println("Answer: " + answer.getText());
        String searchQuery4Answer = this.formSolrQuery4Answer(question, answer);
        if (searchQuery4Answer.trim().equals("")) {
          continue;
        }
        ArrayList<CandidateSentence> candidateSentList4Answer = new ArrayList<CandidateSentence>();
        SolrQuery solrQuery4Answer = new SolrQuery();
        solrQuery4Answer.add("fq", "docid:" + testDocId);
        solrQuery4Answer.add("q", searchQuery4Answer);
        solrQuery4Answer.add("rows", String.valueOf(TOP_SEARCH_RESULTS));
        solrQuery4Answer.setFields("*", "score");
        try {
          SolrDocumentList results = solrWrapper.runQuery(solrQuery4Answer, TOP_SEARCH_RESULTS);
          System.out.println("results size:" + results.size());
          for (int j = 0; j < results.size(); j++) {
            SolrDocument doc = results.get(j);
            String sentId = doc.get("id").toString();
            String docId = doc.get("docid").toString();
            if (!testDocId.equals(docId)) {
              continue;
            }
            String sentIdx = sentId.replace(docId, "").replace("_", "").trim();
            int idx = Integer.parseInt(sentIdx);

            // Sentence annSentence=sentenceList.get(idx);
            // System.out.println(idx);
            Sentence annSentence = null;
            if (sentenceList.size() > idx) {
              annSentence = sentenceList.get(idx);
            } else {
              continue;
            }

            String sentence = doc.get("text").toString();
            double relScore = Double.parseDouble(doc.get("score").toString());
            CandidateSentence candSent = new CandidateSentence(aJCas);
            candSent.setSentence(annSentence);
            candSent.setRelevanceScore(relScore);
            candidateSentList4Answer.add(candSent);
            System.out.println(relScore + "\t" + sentence);
          }
          FSList fsCandidateSentList = Utils.fromCollectionToFSList(aJCas, candidateSentList4Answer);
          fsCandidateSentList.addToIndexes();
          answer.setCandidateSentenceList(fsCandidateSentList);
        } catch (SolrServerException e) {
          e.printStackTrace();
        }
      }
      FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
      testDoc.setQaList(fsQASet);

      System.out.println("=========================================================");
    }
  }

public String formSolrQuery(Question question) {
    
    String solrQuery = "";

    int weights[] = { 0, 1, 1, 0, 0 };

    ArrayList<NounPhrase> nounPhrases = Utils.fromFSListToCollection(question.getNounList(),
            NounPhrase.class);
    
    ArrayList<Token> tokens = Utils.fromFSListToCollection(question.getTokenList(),
            Token.class);

    for (int i = 0; i < tokens.size(); i++) {
      solrQuery += "text:\"" + tokens.get(i).getText() + "\"" + "^" + weights[0] + " ";
    }
    
    for (int i = 0; i < nounPhrases.size(); i++) {
      solrQuery += "nounphrases:\"" + nounPhrases.get(i).getText() + "\"" + "^" + weights[1] + " ";
    }

    ArrayList<NER> neList = Utils.fromFSListToCollection(question.getNerList(), NER.class);
    for (int i = 0; i < neList.size(); i++) {
      solrQuery += "namedentities:\"" + neList.get(i).getText() + "\"" + "^" + weights[2] + " ";
    }

    ArrayList<Token> tokenList = Utils.fromFSListToCollection(question.getTokenList(), Token.class);
    for (int i = 0; i < tokenList.size(); i++) {
      solrQuery += "correference:\"" + tokenList.get(i).getText() + "\"" + "^" + weights[3] + " ";
    }

    for (int i = 0; i < tokenList.size(); i++) {
      solrQuery += "synonyms:\"" + tokenList.get(i).getText() + "\"" + "^" + weights[4] + " ";
    }

    solrQuery = solrQuery.trim();

    return solrQuery;
  }

  public String formSolrQuery4Answer(Question question, Answer answer) {
    
    String solrQuery = "";

    int weights[] = { 0, 1, 1, 0, 0 };

    ArrayList<NounPhrase> nounPhrases = Utils.fromFSListToCollection(answer.getNounPhraseList(),
            NounPhrase.class);
    
    ArrayList<Token> tokens = Utils.fromFSListToCollection(answer.getTokenList(),
            Token.class);

    for (int i = 0; i < tokens.size(); i++) {
      solrQuery += "text:\"" + tokens.get(i).getText() + "\"" + "^" + weights[0] + " ";
    }
    
    for (int i = 0; i < nounPhrases.size(); i++) {
      solrQuery += "nounphrases:\"" + nounPhrases.get(i).getText() + "\"" + "^" + weights[1] + " ";
    }

    ArrayList<NER> neList = Utils.fromFSListToCollection(answer.getNerList(), NER.class);
    for (int i = 0; i < neList.size(); i++) {
      solrQuery += "namedentities:\"" + neList.get(i).getText() + "\"" + "^" + weights[2] + " ";
    }

    ArrayList<Token> tokenList = Utils.fromFSListToCollection(answer.getTokenList(), Token.class);
    for (int i = 0; i < tokenList.size(); i++) {
      solrQuery += "correference:\"" + tokenList.get(i).getText() + "\"" + "^" + weights[3] + " ";
    }

    for (int i = 0; i < tokenList.size(); i++) {
      solrQuery += "synonyms:\"" + tokenList.get(i).getText() + "\"" + "^" + weights[4] + " ";
    }

    solrQuery = solrQuery.trim();

    return solrQuery;
  }

}
