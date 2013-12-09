

/* First created by JCasGen Wed Feb 20 05:53:44 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


/** 
 * Updated by JCasGen Thu Dec 05 14:35:26 EST 2013
 * XML source: /Users/troy/git/hw5-team10/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class TestDocument extends SourceDocument {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TestDocument.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TestDocument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TestDocument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TestDocument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TestDocument(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: qaList

  /** getter for qaList - gets 
   * @generated */
  public FSList getQaList() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_qaList == null)
      jcasType.jcas.throwFeatMissing("qaList", "edu.cmu.lti.qalab.types.TestDocument");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TestDocument_Type)jcasType).casFeatCode_qaList)));}
    
  /** setter for qaList - sets  
   * @generated */
  public void setQaList(FSList v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_qaList == null)
      jcasType.jcas.throwFeatMissing("qaList", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setRefValue(addr, ((TestDocument_Type)jcasType).casFeatCode_qaList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: readingTestId

  /** getter for readingTestId - gets 
   * @generated */
  public String getReadingTestId() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_readingTestId == null)
      jcasType.jcas.throwFeatMissing("readingTestId", "edu.cmu.lti.qalab.types.TestDocument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TestDocument_Type)jcasType).casFeatCode_readingTestId);}
    
  /** setter for readingTestId - sets  
   * @generated */
  public void setReadingTestId(String v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_readingTestId == null)
      jcasType.jcas.throwFeatMissing("readingTestId", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setStringValue(addr, ((TestDocument_Type)jcasType).casFeatCode_readingTestId, v);}    
   
    
  //*--------------*
  //* Feature: topicId

  /** getter for topicId - gets 
   * @generated */
  public String getTopicId() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_topicId == null)
      jcasType.jcas.throwFeatMissing("topicId", "edu.cmu.lti.qalab.types.TestDocument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TestDocument_Type)jcasType).casFeatCode_topicId);}
    
  /** setter for topicId - sets  
   * @generated */
  public void setTopicId(String v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_topicId == null)
      jcasType.jcas.throwFeatMissing("topicId", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setStringValue(addr, ((TestDocument_Type)jcasType).casFeatCode_topicId, v);}    
   
    
  //*--------------*
  //* Feature: c1score

  /** getter for c1score - gets c1 score for this document
   * @generated */
  public double getC1score() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_c1score == null)
      jcasType.jcas.throwFeatMissing("c1score", "edu.cmu.lti.qalab.types.TestDocument");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((TestDocument_Type)jcasType).casFeatCode_c1score);}
    
  /** setter for c1score - sets c1 score for this document 
   * @generated */
  public void setC1score(double v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_c1score == null)
      jcasType.jcas.throwFeatMissing("c1score", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((TestDocument_Type)jcasType).casFeatCode_c1score, v);}    
   
    
  //*--------------*
  //* Feature: precision

  /** getter for precision - gets precision for all questions, must answer each question
   * @generated */
  public double getPrecision() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.lti.qalab.types.TestDocument");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((TestDocument_Type)jcasType).casFeatCode_precision);}
    
  /** setter for precision - sets precision for all questions, must answer each question 
   * @generated */
  public void setPrecision(double v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((TestDocument_Type)jcasType).casFeatCode_precision, v);}    
   
    
  //*--------------*
  //* Feature: correctAnswered

  /** getter for correctAnswered - gets 
   * @generated */
  public int getCorrectAnswered() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_correctAnswered == null)
      jcasType.jcas.throwFeatMissing("correctAnswered", "edu.cmu.lti.qalab.types.TestDocument");
    return jcasType.ll_cas.ll_getIntValue(addr, ((TestDocument_Type)jcasType).casFeatCode_correctAnswered);}
    
  /** setter for correctAnswered - sets  
   * @generated */
  public void setCorrectAnswered(int v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_correctAnswered == null)
      jcasType.jcas.throwFeatMissing("correctAnswered", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setIntValue(addr, ((TestDocument_Type)jcasType).casFeatCode_correctAnswered, v);}    
   
    
  //*--------------*
  //* Feature: answered

  /** getter for answered - gets 
   * @generated */
  public int getAnswered() {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_answered == null)
      jcasType.jcas.throwFeatMissing("answered", "edu.cmu.lti.qalab.types.TestDocument");
    return jcasType.ll_cas.ll_getIntValue(addr, ((TestDocument_Type)jcasType).casFeatCode_answered);}
    
  /** setter for answered - sets  
   * @generated */
  public void setAnswered(int v) {
    if (TestDocument_Type.featOkTst && ((TestDocument_Type)jcasType).casFeat_answered == null)
      jcasType.jcas.throwFeatMissing("answered", "edu.cmu.lti.qalab.types.TestDocument");
    jcasType.ll_cas.ll_setIntValue(addr, ((TestDocument_Type)jcasType).casFeatCode_answered, v);}    
  }

    