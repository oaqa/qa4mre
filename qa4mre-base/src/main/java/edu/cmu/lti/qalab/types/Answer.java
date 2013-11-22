

/* First created by JCasGen Sun Feb 17 07:02:44 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Nov 22 12:51:38 EST 2013
 * XML source: /Users/wanghaoyu/git/hw5-team08/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class Answer extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Answer.class);
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
  protected Answer() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Answer(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Answer(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Answer(JCas jcas, int begin, int end) {
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
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: questionId

  /** getter for questionId - gets 
   * @generated */
  public String getQuestionId() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_questionId == null)
      jcasType.jcas.throwFeatMissing("questionId", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_questionId);}
    
  /** setter for questionId - sets  
   * @generated */
  public void setQuestionId(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_questionId == null)
      jcasType.jcas.throwFeatMissing("questionId", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_questionId, v);}    
   
    
  //*--------------*
  //* Feature: docId

  /** getter for docId - gets 
   * @generated */
  public String getDocId() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_docId);}
    
  /** setter for docId - sets  
   * @generated */
  public void setDocId(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_docId, v);}    
   
    
  //*--------------*
  //* Feature: synonyms

  /** getter for synonyms - gets 
   * @generated */
  public FSList getSynonyms() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_synonyms)));}
    
  /** setter for synonyms - sets  
   * @generated */
  public void setSynonyms(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_synonyms, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: isCorrect

  /** getter for isCorrect - gets 
   * @generated */
  public boolean getIsCorrect() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isCorrect == null)
      jcasType.jcas.throwFeatMissing("isCorrect", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isCorrect);}
    
  /** setter for isCorrect - sets  
   * @generated */
  public void setIsCorrect(boolean v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isCorrect == null)
      jcasType.jcas.throwFeatMissing("isCorrect", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isCorrect, v);}    
   
    
  //*--------------*
  //* Feature: isSelected

  /** getter for isSelected - gets 
   * @generated */
  public boolean getIsSelected() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isSelected == null)
      jcasType.jcas.throwFeatMissing("isSelected", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isSelected);}
    
  /** setter for isSelected - sets  
   * @generated */
  public void setIsSelected(boolean v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isSelected == null)
      jcasType.jcas.throwFeatMissing("isSelected", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isSelected, v);}    
   
    
  //*--------------*
  //* Feature: nounPhraseList

  /** getter for nounPhraseList - gets 
   * @generated */
  public FSList getNounPhraseList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nounPhraseList == null)
      jcasType.jcas.throwFeatMissing("nounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nounPhraseList)));}
    
  /** setter for nounPhraseList - sets  
   * @generated */
  public void setNounPhraseList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nounPhraseList == null)
      jcasType.jcas.throwFeatMissing("nounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nounPhraseList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: nerList

  /** getter for nerList - gets 
   * @generated */
  public FSList getNerList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nerList == null)
      jcasType.jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nerList)));}
    
  /** setter for nerList - sets  
   * @generated */
  public void setNerList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nerList == null)
      jcasType.jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nerList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tokenList

  /** getter for tokenList - gets 
   * @generated */
  public FSList getTokenList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_tokenList)));}
    
  /** setter for tokenList - sets  
   * @generated */
  public void setTokenList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_tokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: dependencies

  /** getter for dependencies - gets 
   * @generated */
  public FSList getDependencies() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies)));}
    
  /** setter for dependencies - sets  
   * @generated */
  public void setDependencies(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: matchesQuestionCardinality

  /** getter for matchesQuestionCardinality - gets 
   * @generated */
  public int getMatchesQuestionCardinality() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_matchesQuestionCardinality == null)
      jcasType.jcas.throwFeatMissing("matchesQuestionCardinality", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Answer_Type)jcasType).casFeatCode_matchesQuestionCardinality);}
    
  /** setter for matchesQuestionCardinality - sets  
   * @generated */
  public void setMatchesQuestionCardinality(int v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_matchesQuestionCardinality == null)
      jcasType.jcas.throwFeatMissing("matchesQuestionCardinality", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setIntValue(addr, ((Answer_Type)jcasType).casFeatCode_matchesQuestionCardinality, v);}    
   
    
  //*--------------*
  //* Feature: matchesQuestionEntityType

  /** getter for matchesQuestionEntityType - gets 
   * @generated */
  public boolean getMatchesQuestionEntityType() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_matchesQuestionEntityType == null)
      jcasType.jcas.throwFeatMissing("matchesQuestionEntityType", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_matchesQuestionEntityType);}
    
  /** setter for matchesQuestionEntityType - sets  
   * @generated */
  public void setMatchesQuestionEntityType(boolean v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_matchesQuestionEntityType == null)
      jcasType.jcas.throwFeatMissing("matchesQuestionEntityType", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_matchesQuestionEntityType, v);}    
   
    
  //*--------------*
  //* Feature: candidateSentenceList

  /** getter for candidateSentenceList - gets 
   * @generated */
  public FSList getCandidateSentenceList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_candidateSentenceList == null)
      jcasType.jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_candidateSentenceList)));}
    
  /** setter for candidateSentenceList - sets  
   * @generated */
  public void setCandidateSentenceList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_candidateSentenceList == null)
      jcasType.jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_candidateSentenceList, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    