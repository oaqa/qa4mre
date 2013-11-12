package edu.cmu.lti.deiis.hw5.annotators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.EmptyFSList;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Dependency;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class StanfordCorefAnnotator extends JCasAnnotator_ImplBase {

  private StanfordCoreNLP stanfordAnnotator;

  @Override
  public void initialize(UimaContext context)
      throws ResourceInitializationException {
    super.initialize(context);
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner,dcoref");// ,
                                      // ssplit
    stanfordAnnotator = new StanfordCoreNLP(props);
  }

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {

    TestDocument testDoc = (TestDocument) Utils
        .getTestDocumentFromCAS(jCas);

    String id = testDoc.getId();
    String filteredText = testDoc.getFilteredText();
    // System.out.println("===============================================");
    // System.out.println("DocText: " + docText);
    String filteredSents[] = filteredText.split("[\\n]");
    System.out.println("Total sentences: "+filteredSents.length);
    ArrayList<Sentence> sentList = new ArrayList<Sentence>();
    int sentNo = 0;
    //for (int i = 0; i < filteredSents.length; i++) {

      //Annotation document = new Annotation(filteredSents[i]);
      Annotation document = new Annotation(filteredText);
      try {
        // System.out.println("Entering stanford annotation");
        System.out.println("Annotating...");
        stanfordAnnotator.annotate(document);
        // System.out.println("Out of stanford annotation");
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
      List<CoreMap> sentences = document.get(SentencesAnnotation.class);
      //System.out.println("No. of sentences found: "+((Integer)sentences.size()).toString());
      // SourceDocument sourcecDocument=(SourceDocument)
      // jCas.getAnnotationIndex(SourceDocument.type);
      
      // FSList sentenceList = srcDoc.getSentenceList();
      
      // This is the coreference link graph
      // Each chain stores a set of mentions that link to each other,
      // along with a method for getting the most representative mention
      // Both sentence and token offsets start at 1!
      Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
      for(Entry<Integer, CorefChain> set:graph.entrySet()){
        System.out.println("MentionHead: "+set.getValue().getRepresentativeMention().toString());
      }
      /*for (CoreMap sentence : sentences) {
        String sentText = sentence.toString();
        Sentence annSentence = new Sentence(jCas);
      }*/
      
    //}
  }
}
