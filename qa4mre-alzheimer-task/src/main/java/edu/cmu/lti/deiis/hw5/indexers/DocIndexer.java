package edu.cmu.lti.deiis.hw5.indexers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class DocIndexer {

  // main method for indexing gazatteer into index.
  void indexGazatteer(BufferedReader br, IndexWriter iw) throws IOException {

    Document d = new Document();
    Field nfid = new Field("id", false, "", Field.Store.YES, Index.NOT_ANALYZED, TermVector.NO);
    Field nsid = new Field("sentid", false, "", Field.Store.YES, Index.NOT_ANALYZED, TermVector.NO);
    Field ntext = new Field("text", false, "", Field.Store.YES, Index.ANALYZED, TermVector.NO);

    d.add(nfid);
    d.add(nsid);
    d.add(ntext);

    String line;
    while ((line = br.readLine()) != null) {
      String[] column = line.trim().split("\t");
      System.out.println(column[1]);
      // get other columns except for the location words
      String id = column[0];
      String text = column[1];

      // chop the text into sentences.

      // To Do: set values to document d, and index it
      nfid.setValue(id);// 1
      // ntext.setStringValue(text.toLowerCase());

      Reader reader = new StringReader(text);
      DocumentPreprocessor dp = new DocumentPreprocessor(reader);

      List<String> sentenceList = new LinkedList<String>();
      Iterator<List<HasWord>> it = dp.iterator();
      while (it.hasNext()) {
        StringBuilder sentenceSb = new StringBuilder();
        List<HasWord> sentence = it.next();
        for (HasWord token : sentence) {
          if (sentenceSb.length() > 1) {
            sentenceSb.append(" ");
          }
          sentenceSb.append(token);
        }
        sentenceList.add(sentenceSb.toString());
      }
      int sid = 0;
      String p_2 = "", p_1 = "", p_0 = "";
      for (int i = 0; i < sentenceList.size(); i++) {
        if (i - 2 >= 0)
          p_2 = sentenceList.get(i - 2);
        if (i - 1 >= 0)
          p_1 = sentenceList.get(i - 1);
        p_0 = sentenceList.get(i);
        if (i - 1 >= 0) {
          nsid.setValue(Integer.toString(i - 1));
          ntext.setValue(p_2 + " " + p_1 + " " + p_0);
          iw.addDocument(d);
        }
      }
      // add this new document.

    }
  }

  public TopDocs getReleventSentence(String text) throws Exception {
    Query q = new TermQuery(new Term("text", text.toLowerCase()));
    BooleanQuery bq = new BooleanQuery();
    bq.add(q, Occur.SHOULD);
    IndexSearcher is = GetReader.getIndexSearcher("data/docindex");
    TopDocs docs = is.search(q, 1000);
    if (docs == null) {
      System.err.println("Not found.");
    }
    if (docs.scoreDocs.length == 0) {
      System.err.println("Not found.");
    }
    return docs;
  }

  public static void main(String argv[]) throws Exception {

    DocIndexer gi = new DocIndexer();

    argv[0] = "-read";
    String mode = argv[0];

    if (mode.equals("-write")) {
      if (argv.length != 3)
        throw new Exception("Command line argument number wrong");
      argv[1] = "data/Biomedical_about_Alzheimer_Sample_GS_Docs";
      argv[2] = "data/docindex";
      BufferedReader br = GetReader.getUTF8FileReader(argv[1]);
      IndexWriter iw = GetWriter.getIndexWriter(argv[2]);
      iw.deleteAll();
      gi.indexGazatteer(br, iw);
      iw.close();
      br.close();
    }
    if (mode.equals("-read")) {
      System.out.println("input id. Output basic information. For debugging.");
      // query first two fields.
      argv[1] = "data/docindex";
      IndexSearcher is = GetReader.getIndexSearcher(argv[1]);
      BufferedReader r = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
      String line;
      while ((line = r.readLine()) != null) {

        BooleanQuery bq = new BooleanQuery();
        Query q = new TermQuery(new Term("text", line));
        bq.add(q, Occur.SHOULD);
        long start = System.currentTimeMillis();
        TopDocs docs = is.search(q, 1000);
        if (docs == null) {
          System.err.println("Not found.");
          continue;
        }
        if (docs.scoreDocs.length == 0) {
          System.err.println("Not found.");
          continue;
        }

        for (ScoreDoc sd : docs.scoreDocs) {
          Document d = is.doc(sd.doc);
          long end = System.currentTimeMillis();
          System.out.println(sd.score);
          System.out.println(d.get("id"));
          System.out.println(d.get("sentid"));
          System.out.println(d.get("text"));
          System.out.println("lookup time: " + (end - start));
        }
      }
    }
  }

}
