package edu.cmu.lti.deiis.hw5.indexers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.schema.IndexSchema;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.qalab.solrutils.SolrUtils;
import edu.cmu.lti.qalab.types.Corefcluster;
import edu.cmu.lti.qalab.types.Dependency;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Phrase;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Synonym;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class SolrIndexer extends JCasAnnotator_ImplBase {

  int mDocNum;

  File mOutputFile = null;

  SolrWrapper wrapper;

  String serverUrl;

  IndexSchema indexSchema;

  String coreName;

  String schemaName;

  double THRESHOLD = 4.0;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    serverUrl = (String) context.getConfigParameterValue("SOLR_SERVER_URL");
    coreName = (String) context.getConfigParameterValue("SOLR_CORE");
    schemaName = (String) context.getConfigParameterValue("SCHEMA_NAME");

    try {
      this.wrapper = new SolrWrapper(serverUrl + coreName);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {

    TestDocument testDoc = Utils.getTestDocumentFromCAS(jCas);// (TestDocument)
    // jCas.getAnnotationIndex(TestDocument.type);

    try {
      // try to get indexschema so that you can know the fields available
      indexSchema = SolrUtils.getIndexSchema(serverUrl, coreName, schemaName);

      String id = testDoc.getId();

      ArrayList<Sentence> sentenceList = Utils.fromFSListToCollection(testDoc.getSentenceList(),
              Sentence.class);

      // Hash the co-reference cluster
      HashMap<Integer, Corefcluster> clusterHashMap = new HashMap<Integer, Corefcluster>();
      FSIndex ClusterIndex = jCas.getAnnotationIndex(Corefcluster.type);
      Iterator ClusterIter = ClusterIndex.iterator();
      while (ClusterIter.hasNext()) {
        Corefcluster corefcluster = (Corefcluster) ClusterIter.next();
        clusterHashMap.put(corefcluster.getId(), corefcluster);
      }

      // Index each sentence
      for (int i = 0; i < sentenceList.size(); i++) {
        Sentence sent = sentenceList.get(i);
        String sentText = sent.getText();
        String sentId = id + "_" + i;
        HashMap<String, Object> indexMap = new HashMap<String, Object>();
        indexMap.put("docid", id);
        indexMap.put("id", sentId);
        indexMap.put("text", sentText);

        FSList fsNounList = sent.getPhraseList();
        ArrayList<NounPhrase> nounPhrases = Utils.fromFSListToCollection(fsNounList,
                NounPhrase.class);
        ArrayList<String> nnList = new ArrayList<String>();
        for (int j = 0; j < nounPhrases.size(); j++) {
          nnList.add(nounPhrases.get(j).getText());
        }
        indexMap.put("nounphrases", nnList);

        FSList fsNEList = sent.getNerList();
        ArrayList<NER> namedEntities = Utils.fromFSListToCollection(fsNEList, NER.class);
        ArrayList<String> neList = new ArrayList<String>();
        for (int j = 0; j < namedEntities.size(); j++) {
          neList.add(namedEntities.get(j).getText());
        }
        indexMap.put("namedentities", neList);

        if (sent.getGenPhraseList() != null) {
          FSList fsPList = sent.getGenPhraseList();
          ArrayList<Phrase> phrases = Utils.fromFSListToCollection(fsPList, Phrase.class);
          ArrayList<String> pList = new ArrayList<String>();

          for (int j = 0; j < phrases.size(); j++) {
            Phrase phrase = (Phrase) phrases.get(j);
            int clusterID = phrase.getCluster();
            Corefcluster corefcluster = clusterHashMap.get(clusterID);
            if (corefcluster.getChain() == null) {
              continue;
            }
            ArrayList<Phrase> coref = Utils.fromFSListToCollection(corefcluster.getChain(),
                    Phrase.class);
            for (int k = 0; k < coref.size(); k++) {
              pList.add(coref.get(k).getText());
            }
          }
          indexMap.put("correference", pList);
        }

        FSList fsDependencies = sent.getDependencyList();
        if (fsDependencies != null) {
          ArrayList<Dependency> dependencies = Utils.fromFSListToCollection(fsDependencies,
                  Dependency.class);
          ArrayList<String> depList = new ArrayList<String>();
          for (int j = 0; j < dependencies.size(); j++) {
            String rel = dependencies.get(j).getRelation();
            String gov = dependencies.get(j).getGovernor().getText();
            String dep = dependencies.get(j).getDependent().getText();
            String depText = rel + "(" + gov + "," + dep + ")";
            depList.add(depText);
          }

          indexMap.put("dependencies", depList);
        }

        FSList tokenList = sent.getTokenList();
        ArrayList<Token> tokens = Utils.fromFSListToCollection(tokenList, Token.class);
        ArrayList<Synonym> synonymList = new ArrayList<Synonym>();
        for (int j = 0; j < tokens.size(); j++) {
          Token token = (Token) tokens.get(j);
          if (token.getSynonyms() == null) {
            continue;
          }
          ArrayList<Synonym> synonyms = Utils.fromFSListToCollection(token.getSynonyms(),
                  Synonym.class);
          synonymList.addAll(synonyms);
        }
        indexMap.put("synonyms", synonymList);

        SolrInputDocument solrInpDoc = this.wrapper.buildSolrDocument(indexMap);
        String docXML = this.wrapper.convertSolrDocInXML(solrInpDoc);

        this.wrapper.indexDocument(docXML);
        this.wrapper.getServer().commit();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}