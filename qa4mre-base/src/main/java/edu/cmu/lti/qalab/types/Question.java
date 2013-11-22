

/* First created by JCasGen Wed Feb 20 04:59:42 EST 2013 */
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
public class Question extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Question.class);
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
  protected Question() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Question(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Question(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Question(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: dependencies

  /** getter for dependencies - gets 
   * @generated */
  public FSList getDependencies() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies)));}
    
  /** setter for dependencies - sets  
   * @generated */
  public void setDependencies(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: nerList

  /** getter for nerList - gets 
   * @generated */
  public FSList getNerList() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_nerList == null)
      jcasType.jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_nerList)));}
    
  /** setter for nerList - sets  
   * @generated */
  public void setNerList(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_nerList == null)
      jcasType.jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_nerList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: nounList

  /** getter for nounList - gets 
   * @generated */
  public FSList getNounList() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_nounList == null)
      jcasType.jcas.throwFeatMissing("nounList", "edu.cmu.lti.qalab.types.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_nounList)));}
    
  /** setter for nounList - sets  
   * @generated */
  public void setNounList(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_nounList == null)
      jcasType.jcas.throwFeatMissing("nounList", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_nounList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tokenList

  /** getter for tokenList - gets 
   * @generated */
  public FSList getTokenList() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_tokenList)));}
    
  /** setter for tokenList - sets  
   * @generated */
  public void setTokenList(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_tokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: cardinality

  /** getter for cardinality - gets 1 - Singular (question target entity is singular)
2 - Plural (question target entity is plural)
   * @generated */
  public int getCardinality() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_cardinality == null)
      jcasType.jcas.throwFeatMissing("cardinality", "edu.cmu.lti.qalab.types.Question");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Question_Type)jcasType).casFeatCode_cardinality);}
    
  /** setter for cardinality - sets 1 - Singular (question target entity is singular)
2 - Plural (question target entity is plural) 
   * @generated */
  public void setCardinality(int v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_cardinality == null)
      jcasType.jcas.throwFeatMissing("cardinality", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setIntValue(addr, ((Question_Type)jcasType).casFeatCode_cardinality, v);}    
   
    
  //*--------------*
  //* Feature: entityType

  /** getter for entityType - gets "Integer" - question is looking for target entity that is an integer value (or NP containing integer value)
"Double" - question is looking for target entity that is a double value (or NP containing double value)
"DateTime" - question is looking for target entity that contains date or time information (date, event, relative time, etc)
"Entity" - question is looking for target entity that is a "thing" - not a quantity or date
   * @generated */
  public String getEntityType() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_entityType == null)
      jcasType.jcas.throwFeatMissing("entityType", "edu.cmu.lti.qalab.types.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_entityType);}
    
  /** setter for entityType - sets "Integer" - question is looking for target entity that is an integer value (or NP containing integer value)
"Double" - question is looking for target entity that is a double value (or NP containing double value)
"DateTime" - question is looking for target entity that contains date or time information (date, event, relative time, etc)
"Entity" - question is looking for target entity that is a "thing" - not a quantity or date 
   * @generated */
  public void setEntityType(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_entityType == null)
      jcasType.jcas.throwFeatMissing("entityType", "edu.cmu.lti.qalab.types.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_entityType, v);}    
  }

    