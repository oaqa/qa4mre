

/* First created by JCasGen Tue Nov 05 22:09:09 EST 2013 */
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
public class CandidateSentenceAnswerSet extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(CandidateSentenceAnswerSet.class);
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
  protected CandidateSentenceAnswerSet() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CandidateSentenceAnswerSet(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CandidateSentenceAnswerSet(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public CandidateSentenceAnswerSet(JCas jcas, int begin, int end) {
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
  //* Feature: candidateSentenceList

  /** getter for candidateSentenceList - gets A list of Candidate sentence based on the question and the specific answer
   * @generated */
  public FSList getCandidateSentenceList() {
    if (CandidateSentenceAnswerSet_Type.featOkTst && ((CandidateSentenceAnswerSet_Type)jcasType).casFeat_candidateSentenceList == null)
      jcasType.jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateSentenceAnswerSet_Type)jcasType).casFeatCode_candidateSentenceList)));}
    
  /** setter for candidateSentenceList - sets A list of Candidate sentence based on the question and the specific answer 
   * @generated */
  public void setCandidateSentenceList(FSList v) {
    if (CandidateSentenceAnswerSet_Type.featOkTst && ((CandidateSentenceAnswerSet_Type)jcasType).casFeat_candidateSentenceList == null)
      jcasType.jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    jcasType.ll_cas.ll_setRefValue(addr, ((CandidateSentenceAnswerSet_Type)jcasType).casFeatCode_candidateSentenceList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: answer

  /** getter for answer - gets The answer which the candidate sentences are based on
   * @generated */
  public Answer getAnswer() {
    if (CandidateSentenceAnswerSet_Type.featOkTst && ((CandidateSentenceAnswerSet_Type)jcasType).casFeat_answer == null)
      jcasType.jcas.throwFeatMissing("answer", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    return (Answer)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateSentenceAnswerSet_Type)jcasType).casFeatCode_answer)));}
    
  /** setter for answer - sets The answer which the candidate sentences are based on 
   * @generated */
  public void setAnswer(Answer v) {
    if (CandidateSentenceAnswerSet_Type.featOkTst && ((CandidateSentenceAnswerSet_Type)jcasType).casFeat_answer == null)
      jcasType.jcas.throwFeatMissing("answer", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    jcasType.ll_cas.ll_setRefValue(addr, ((CandidateSentenceAnswerSet_Type)jcasType).casFeatCode_answer, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    