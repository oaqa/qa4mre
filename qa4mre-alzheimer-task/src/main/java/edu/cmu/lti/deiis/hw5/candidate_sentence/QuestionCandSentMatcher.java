package edu.cmu.lti.deiis.hw5.candidate_sentence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
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
import org.uimafit.util.FSCollectionFactory;

import edu.cmu.lti.deiis.hw5.indexers.DocIndexer;
import edu.cmu.lti.deiis.hw5.indexers.GetReader;
import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

public class QuestionCandSentMatcher extends JCasAnnotator_ImplBase {
  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    try{
      loadStopWords();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  ArrayList<String> stopWords = new ArrayList<String>();

  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    String testDocId = testDoc.getId();
    ArrayList<Sentence> sentenceList = Utils.getSentenceListFromTestDocCAS(aJCas);
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);
    try {
      IndexSearcher is = GetReader.getIndexSearcher("data/docindex");
      DocIndexer docIndexer = new DocIndexer();
      ArrayList<CandidateSentence> candidateSentList = new ArrayList<CandidateSentence>();

      for (int i = 0; i < qaSet.size(); i++) {

        Question question = qaSet.get(i).getQuestion();
        System.out.println("========================================================");
        System.out.println("Question: " + question.getText());
        for (Answer answer : FSCollectionFactory.create(qaSet.get(i).getAnswerList(), Answer.class)) {
          String searchQuery = this.formQuery(question, answer);
          TopDocs docList = docIndexer.getReleventSentence(searchQuery);
          for (ScoreDoc doc : docList.scoreDocs) {
            CandidateSentence candSent = new CandidateSentence(aJCas);
            Sentence annSentence = new Sentence(aJCas);
            String sentence = is.doc(doc.doc).get("text");
            annSentence.setText(sentence);
            candSent.setSentence(annSentence);
            Float relScore = doc.score;
            candSent.setRelevanceScore(relScore);
            candidateSentList.add(candSent);
            System.out.println(relScore + "\t" + sentence);
          }
        }
        FSList fsCandidateSentList = Utils.fromCollectionToFSList(aJCas, candidateSentList);
        fsCandidateSentList.addToIndexes();
        qaSet.get(i).setCandidateSentenceList(fsCandidateSentList);
        qaSet.get(i).addToIndexes();
        FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
        testDoc.setQaList(fsQASet);

        System.out.println("=========================================================");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Change this to form better query
   * 
   * @param question
   * @return
   */
  private String formQuery(Question question, Answer answer) {
    String query = "";
    query += deleteStopWords(question.getText());
    query += " ";
    query += deleteStopWords(answer.getText());
    System.out.println("Answer-Question Query: " + query);
    return query;
  }

  /**
   * Need to be done.
   * 
   * @param text
   * @return
   */
  private String deleteStopWords(String text) {
    String[] s = text.toLowerCase().split(" ");
    String result = "";
    for (String ss : s) {
      if (stopWords.contains(ss))
        continue;
      result += " " + ss;
    }
    return result;
  }

  private void loadStopWords() throws Exception {
    File f = new File("data/stopwords.txt");
    BufferedReader reader = new BufferedReader(new FileReader(f));
    String line;
    while ((line = reader.readLine()) != null) {
      stopWords.add(line);
    }
    reader.close();
  }

}
