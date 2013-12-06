package edu.cmu.lti.deiis.hw5.casconsumers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.SourceDocument;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

/** Outputs results in the specified QA4MRE format.
 */
public class QA4MREResultCasConsumer extends CasConsumer_ImplBase {

  int mDocNum;

  File mOutputDir = null;

  double THRESHOLD = 4.0;

  @Override
  public void initialize() {

    mDocNum = 0;
    try {
      mOutputDir = new File((String) getConfigParameterValue("OUTPUT_DIR"));

      THRESHOLD = Double.parseDouble((String) getConfigParameterValue("THRESHOLD"));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {

    JCas jCas;
    try {
      jCas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    TestDocument srcDoc = Utils.getTestDocumentFromCAS(jCas);

    String docId = srcDoc.getId();
    String outFileName = mOutputDir + "/" + docId + ".xmi";
    try {
      File outFile = new File(outFileName);
      this.writeXmi(aCAS, outFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeXmi(CAS aCas, File outFile) throws IOException, SAXException {
    FileOutputStream out = null;
    OutputStreamWriter ow = null;
    BufferedWriter writer = null;
    try {
      // write XMI
      out = new FileOutputStream(outFile);
      ow = new OutputStreamWriter(out);
      writer = new BufferedWriter(ow);
      TestDocument testDoc = Utils.getTestDocumentFromCAS(aCas.getJCas());
      ArrayList<QuestionAnswerSet> qaSet = Utils.fromFSListToCollection(testDoc.getQaList(),
              QuestionAnswerSet.class);
      String docID = testDoc.getId().substring(testDoc.getId().length() - 1, testDoc.getId().length());
      writer.write("<output run_id=\"team-08\">" + "\r\n");
      writer.write("\t" + "<topic t_id=\"4\">" + "\r\n");
      writer.write("\t\t" + "<reading-test r_id=\"" + docID + "\">" + "\r\n");
      for (int i = 0; i < qaSet.size(); i++) {
        Question question = qaSet.get(i).getQuestion();
        
        ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet
                .get(i).getAnswerList(), Answer.class);
        int choice = -1;
        for(int j=0;j<5;j++){
          Answer answer = choiceList.get(j);
          if(answer.getIsSelected()){
            choice = j+1;
          }
        }
        if(choice == -1){
          writer.write("\t\t\t" + "<question q_id =\"" + (Integer.parseInt(question.getId())+1) + "\" answered=\"NO\" />" + "\r\n");
        }
        else{
          writer.write("\t\t\t" + "<question q_id =\"" + (Integer.parseInt(question.getId())+1) + "\" answered=\"YES\">" + "\r\n");
          writer.write("\t\t\t\t" + "<answer a_id =\"" + choice + "\"/>" + "\r\n");
          writer.write("\t\t\t" + "</question>" + "\r\n");
        }
      }
      writer.write("\t\t" + "</reading-test>" + "\r\n");
      writer.write("\t" + "</topic>" + "\r\n");
      writer.write("</output>" + "\r\n");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        writer.close();
        ow.close();
        out.close();
      }
    }
  }

  /**
   * Closes the file and other resources initialized during the process
   * 
   */
  @Override
  public void destroy() {

  }
}
