package edu.cmu.lti.deiis.hw5.candidate_sentence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import org.uimafit.util.FSCollectionFactory;

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

/**
 * 
 * @author Ran Chen
 * 
 * 
 */
public class QuestionCandSentAnswerMatcher extends JCasAnnotator_ImplBase {

	SolrWrapper solrWrapper = null;

	String serverUrl;

	// IndexSchema indexSchema;
	String coreName;

	String schemaName;

	int TOP_SEARCH_RESULTS = 3;

	ArrayList<String> stopWords = new ArrayList<String>();

	ArrayList<String> word = new ArrayList<String>();

	ArrayList<String> syno = new ArrayList<String>();

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		serverUrl = (String) context.getConfigParameterValue("SOLR_SERVER_URL");
		coreName = (String) context.getConfigParameterValue("SOLR_CORE");
		schemaName = (String) context.getConfigParameterValue("SCHEMA_NAME");
		TOP_SEARCH_RESULTS = (Integer) context
				.getConfigParameterValue("TOP_SEARCH_RESULTS");
		try {
			this.solrWrapper = new SolrWrapper(serverUrl + coreName);
			loadStopWords();
			loadSyno();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
		String testDocId = testDoc.getId();
		// testDocId = testDocId.substring(0,6)+"-"+testDocId.substring(6);
		// System.out.println(testDocId);
		ArrayList<Sentence> sentenceList = Utils
				.getSentenceListFromTestDocCAS(aJCas);
		ArrayList<QuestionAnswerSet> qaSet = Utils
				.getQuestionAnswerSetFromTestDocCAS(aJCas);

		for (int i = 0; i < qaSet.size(); i++) {

			Question question = qaSet.get(i).getQuestion();
			System.out
					.println("========================================================");
			System.out.println("Question: " + question.getText());
			for (Answer answer : FSCollectionFactory.create(qaSet.get(i)
					.getAnswerList(), Answer.class)) {
				System.out.println("Answer is " + answer.getText());
				String searchQuery = this.formSolrQuery(question, answer);
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
					SolrDocumentList results = solrWrapper.runQuery(solrQuery,
							TOP_SEARCH_RESULTS);
					for (int j = 0; j < results.size(); j++) {
						SolrDocument doc = results.get(j);
						String sentId = doc.get("id").toString();
						String docId = doc.get("docid").toString();
						if (!testDocId.equals(docId)) {
							System.out.println("No equal!");
							continue;

						}
						String sentIdx = sentId.replace(docId, "")
								.replace("_", "").trim();
						int idx = Integer.parseInt(sentIdx);
						Sentence annSentence = sentenceList.get(idx);

						String sentence = doc.get("text").toString();
						double relScore = Double.parseDouble(doc.get("score")
								.toString());
						CandidateSentence candSent = new CandidateSentence(
								aJCas);
						candSent.setSentence(annSentence);
						candSent.setRelevanceScore(relScore);
						candidateSentList.add(candSent);
						System.out.println(relScore + "\t" + sentence);
					}
					FSList fsCandidateSentList = Utils.fromCollectionToFSList(
							aJCas, candidateSentList);
					fsCandidateSentList.addToIndexes();
					qaSet.get(i).setCandidateSentenceList(fsCandidateSentList);
					qaSet.get(i).addToIndexes();

				} catch (SolrServerException e) {
					e.printStackTrace();
				}
				FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
				testDoc.setQaList(fsQASet);

			}

			System.out
					.println("=========================================================");
		}

	}

	public String formSolrQuery(Question question, Answer answer) {
		String query = "";
		query += "(" + deleteStopWords(question.getText());
		query += " " + addSyno(question.getText()) + ") OR (";
		query += deleteStopWordsInAnswer(answer.getText());
		//query += " " + addSyno(answer.getText());
		query += ")";
		// query = "nounphrases: mice";
		System.out.println("Answer-Question Query: " + query);
		return query;
	}

	private String addSyno(String text) {
		String result = "";
		String[] s = text.toLowerCase().split(" ");
		for (String ss : s) {
			if (!word.contains(ss))
				continue;
			int index = word.indexOf(ss);
			String[] tokens = syno.get(index).split(",");
			for (int i = 0; i < tokens.length; i++) {
				result += " " + tokens[i];
			}
		}
		return result;
	}

	private String deleteStopWords(String text) {
		String[] s = text.toLowerCase().split(" ");
		String result = "";
		for (String ss : s) {
			if (stopWords.contains(ss))
				continue;
			if(word.contains(ss)){
				result += " ("+ss;
				String[] tokens = syno.get(word.indexOf(ss)).split(",");
				for (int i = 0; i < tokens.length; i++) {
				 result += " " +tokens[i];
				}
				result +=")";
			}
			else{	
			 result += " " + ss;
			 
			}
		}
		return result;
	}

	private String deleteStopWordsInAnswer(String text) {
		String[] s = text.toLowerCase().split(" ");
		String result = "";
		for (String ss : s) {
			if (stopWords.contains(ss))
				continue;
			if(word.contains(ss)){
				result += " ("+ss;
				String[] tokens = syno.get(word.indexOf(ss)).split(",");
				for (int i = 0; i < tokens.length; i++) {
				 result += " " +tokens[i];
				}
				result +=") AND";
			}
			else{	
			 result += " " + ss + " AND";
			 
			}
			//result += " " + ss + " AND";
		}
		if (result.length() > 4)
			result = result.substring(0, result.length() - 4);
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

	private void loadSyno() throws Exception {
		File f = new File("data/oov_medical.txt");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] s = line.split(":");
			word.add(s[0].toLowerCase().trim());
			syno.add(s[1].toLowerCase().substring(0, s[1].indexOf('(')));
		}
		reader.close();
	}
}
