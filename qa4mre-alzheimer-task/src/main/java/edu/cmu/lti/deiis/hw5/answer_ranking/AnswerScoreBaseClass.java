package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.ArrayList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateAnswer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet;
import edu.cmu.lti.qalab.types.Dependency;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Synonym;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

public class AnswerScoreBaseClass extends JCasAnnotator_ImplBase {

  // int K_CANDIDATES = 5;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    // K_CANDIDATES = (Integer) context.getConfigParameterValue("K_CANDIDATES");
  }

  public double computScore(Answer answer, CandidateSentence sentence) {
    ArrayList<NounPhrase> candSentNouns = Utils.fromFSListToCollection(sentence.getSentence()
            .getPhraseList(), NounPhrase.class);
    ArrayList<NER> candSentNers = Utils.fromFSListToCollection(sentence.getSentence().getNerList(),
            NER.class);
    ArrayList<NounPhrase> choiceNouns = Utils.fromFSListToCollection(answer.getNounPhraseList(),
            NounPhrase.class);
    ArrayList<NER> choiceNERs = Utils.fromFSListToCollection(answer.getNerList(), NER.class);

    int nnMatch = 0;
    for (int k = 0; k < candSentNouns.size(); k++) {
      for (int l = 0; l < choiceNERs.size(); l++) {
        if (candSentNouns.get(k).getText().contains(choiceNERs.get(l).getText())) {
          nnMatch++;
        }
      }
      for (int l = 0; l < choiceNouns.size(); l++) {
        if (candSentNouns.get(k).getText().contains(choiceNouns.get(l).getText())) {
          nnMatch++;
        }
      }
    }

    for (int k = 0; k < candSentNers.size(); k++) {
      for (int l = 0; l < choiceNERs.size(); l++) {
        if (candSentNouns.get(k).getText().contains(choiceNERs.get(l).getText())) {
          nnMatch++;
        }
      }
      for (int l = 0; l < choiceNouns.size(); l++) {
        if (candSentNouns.get(k).getText().contains(choiceNouns.get(l).getText())) {
          nnMatch++;
        }
      }

    }
    return (double) nnMatch;
  }

	public double computeJaccardSimilarityScore(Answer answer,
			CandidateSentence sentence) {
		ArrayList<NounPhrase> candSentNouns = Utils.fromFSListToCollection(
				sentence.getSentence().getPhraseList(), NounPhrase.class);
		ArrayList<NER> candSentNers = Utils.fromFSListToCollection(sentence
				.getSentence().getNerList(), NER.class);
		ArrayList<Dependency> candSentDependencies = Utils.fromFSListToCollection(sentence.getSentence().getDependencyList(), Dependency.class);
		
		ArrayList<NounPhrase> choiceNouns = Utils.fromFSListToCollection(
				answer.getNounPhraseList(), NounPhrase.class);
		ArrayList<NER> choiceNers = Utils.fromFSListToCollection(
				answer.getNerList(), NER.class);
		ArrayList<Dependency> choiceDependencies = Utils.fromFSListToCollection(sentence.getSentence().getDependencyList(), Dependency.class);
		
		
		
		double nnNounPhrase=0, nnNER=0, nnDependency=0;
		double scoreNounPhrase=0, scoreNER=0, scoreDependency=0;
		
		for (int k = 0; k < candSentNouns.size(); k++) {
			for (int l = 0; l < choiceNouns.size(); l++) {
				if (candSentNouns.get(k).getText()
						.equals(choiceNouns.get(l).getText())) {
					nnNounPhrase+=1;
				}
			}
		}
		
		if(candSentNouns.size()+choiceNouns.size()-nnNounPhrase!=0 )
			scoreNounPhrase=nnNounPhrase/((double)candSentNouns.size()+ choiceNouns.size()-nnNounPhrase);
		

		for (int k = 0; k < candSentNers.size(); k++) {
			for (int l = 0; l < choiceNers.size(); l++) {
				if (candSentNers.get(k).getText()
						.equals(choiceNers.get(l).getText())) {
					nnNER+=1;
				}
			}
		}
		if(candSentNers.size()+choiceNers.size()-nnNER!=0 )
			scoreNER = nnNER/((double)candSentNers.size() + choiceNers.size()-nnNER);
		
		
		for (int k = 0; k < candSentDependencies.size(); k++) {
			for (int l = 0; l < choiceDependencies.size(); l++) {
				if (candSentDependencies.get(k).getCoveredText()
						.equals(choiceDependencies.get(l).getCoveredText())) {
					nnDependency+=1;
				}
			}
		}
		
		if(candSentDependencies.size()+choiceDependencies.size() - nnDependency!=0 )
			scoreDependency = nnDependency/((double)candSentDependencies.size() + choiceDependencies.size()- nnDependency);
		
		double ans = (scoreDependency + scoreNER + scoreNounPhrase)/3;
//		System.out.println("***" + ans +"**");
		return ans;
	}
  
  
  /*
   * Implement this function by assigning score to some field of candidateAnswer
   */
  public void processCandidateAnswerScore(CandidateAnswer candidateAnswer,
          CandidateSentence sentence, Answer answer) {
	 
	  candidateAnswer.setSimilarityScore(computScore(answer, sentence));
	  //candidateAnswer.setJaccardSimilarityScore(computeJaccardSimilarityScore(answer, sentence));
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);

    for (int i = 0; i < qaSet.size(); i++) {
      Question question = qaSet.get(i).getQuestion();
//      System.out.println("Question: " + question.getText());
      ArrayList<CandidateSentenceAnswerSet> sentsAnswerPairs = Utils.fromFSListToCollection(qaSet
              .get(i).getCandidateSets(), CandidateSentenceAnswerSet.class);

      for (int j = 0; j < sentsAnswerPairs.size(); j++) {
        CandidateSentenceAnswerSet pair = sentsAnswerPairs.get(j);
        Answer answer = pair.getAnswer();
        ArrayList<CandidateSentence> candSentList = Utils.fromFSListToCollection(
                pair.getCandidateSentenceList(), CandidateSentence.class);
        for (CandidateSentence sent : candSentList) {
          /*
           * Create CandidateAnswer in CandidateSentence to store scores. As we may have different
           * AnswerScore components and different components may change the score in different ways,
           * we fetch the CandidateAnswer when we have that instance
           */
          CandidateAnswer candAnswer = null;
          if (sent.getCandAnswer() == null) {
            candAnswer = new CandidateAnswer(aJCas);
          } else {
            candAnswer = sent.getCandAnswer();
          }
          candAnswer.setText(answer.getText());
          candAnswer.setQId(answer.getQuestionId());
          candAnswer.setChoiceIndex(j);
          processCandidateAnswerScore(candAnswer, sent, answer);
          
          sent.setCandAnswer(candAnswer);
        }
        FSList fsCandSentList = Utils.fromCollectionToFSList(aJCas, candSentList);
        // fsCandSentList.addToIndexes();
        pair.setCandidateSentenceList(fsCandSentList);
        // pair.addToIndexes();
      }
      FSList fsPairs = Utils.fromCollectionToFSList(aJCas, sentsAnswerPairs);
      qaSet.get(i).setCandidateSets(fsPairs);
      // End of one qaset
//      System.out.println("================================================");
    }
    FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
    testDoc.setQaList(fsQASet);
  }
}