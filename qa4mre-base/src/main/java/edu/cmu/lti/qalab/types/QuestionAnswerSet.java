

/* First created by JCasGen Fri Feb 22 06:13:28 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Nov 12 13:45:03 EST 2013
 * XML source: /Users/vvvemuri1/git/hw5-team10/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class QuestionAnswerSet extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(QuestionAnswerSet.class);
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
  protected QuestionAnswerSet() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public QuestionAnswerSet(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public QuestionAnswerSet(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public QuestionAnswerSet(JCas jcas, int begin, int end) {
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
  //* Feature: question

  /** getter for question - gets 
   * @generated */
  public Question getQuestion() {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_question == null)
      jcasType.jcas.throwFeatMissing("question", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    return (Question)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_question)));}
    
  /** setter for question - sets  
   * @generated */
  public void setQuestion(Question v) {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_question == null)
      jcasType.jcas.throwFeatMissing("question", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    jcasType.ll_cas.ll_setRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_question, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: answerList

  /** getter for answerList - gets 
   * @generated */
  public FSList getAnswerList() {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_answerList == null)
      jcasType.jcas.throwFeatMissing("answerList", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_answerList)));}
    
  /** setter for answerList - sets  
   * @generated */
  public void setAnswerList(FSList v) {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_answerList == null)
      jcasType.jcas.throwFeatMissing("answerList", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    jcasType.ll_cas.ll_setRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_answerList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: candidateSentenceList

  /** getter for candidateSentenceList - gets Deprecated
   * @generated */
  public FSList getCandidateSentenceList() {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_candidateSentenceList == null)
      jcasType.jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_candidateSentenceList)));}
    
  /** setter for candidateSentenceList - sets Deprecated 
   * @generated */
  public void setCandidateSentenceList(FSList v) {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_candidateSentenceList == null)
      jcasType.jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    jcasType.ll_cas.ll_setRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_candidateSentenceList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: candidateSets

  /** getter for candidateSets - gets The set of all candidate sentences based on each answer option.
   * @generated */
  public FSList getCandidateSets() {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_candidateSets == null)
      jcasType.jcas.throwFeatMissing("candidateSets", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_candidateSets)));}
    
  /** setter for candidateSets - sets The set of all candidate sentences based on each answer option. 
   * @generated */
  public void setCandidateSets(FSList v) {
    if (QuestionAnswerSet_Type.featOkTst && ((QuestionAnswerSet_Type)jcasType).casFeat_candidateSets == null)
      jcasType.jcas.throwFeatMissing("candidateSets", "edu.cmu.lti.qalab.types.QuestionAnswerSet");
    jcasType.ll_cas.ll_setRefValue(addr, ((QuestionAnswerSet_Type)jcasType).casFeatCode_candidateSets, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    