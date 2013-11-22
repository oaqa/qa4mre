

/* First created by JCasGen Tue Nov 12 12:49:38 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;


/** Denotes the coreference cluster
 * Updated by JCasGen Thu Nov 21 21:50:12 EST 2013
 * XML source: C:/Users/Lars/git/hw5-team08/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class Corefcluster extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Corefcluster.class);
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
  protected Corefcluster() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Corefcluster(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Corefcluster(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Corefcluster(JCas jcas, int begin, int end) {
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

  /** getter for id - gets Cluster Id of coref
   * @generated */
  public int getId() {
    if (Corefcluster_Type.featOkTst && ((Corefcluster_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Corefcluster");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Corefcluster_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets Cluster Id of coref 
   * @generated */
  public void setId(int v) {
    if (Corefcluster_Type.featOkTst && ((Corefcluster_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Corefcluster");
    jcasType.ll_cas.ll_setIntValue(addr, ((Corefcluster_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: chain

  /** getter for chain - gets 
   * @generated */
  public FSList getChain() {
    if (Corefcluster_Type.featOkTst && ((Corefcluster_Type)jcasType).casFeat_chain == null)
      jcasType.jcas.throwFeatMissing("chain", "edu.cmu.lti.qalab.types.Corefcluster");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Corefcluster_Type)jcasType).casFeatCode_chain)));}
    
  /** setter for chain - sets  
   * @generated */
  public void setChain(FSList v) {
    if (Corefcluster_Type.featOkTst && ((Corefcluster_Type)jcasType).casFeat_chain == null)
      jcasType.jcas.throwFeatMissing("chain", "edu.cmu.lti.qalab.types.Corefcluster");
    jcasType.ll_cas.ll_setRefValue(addr, ((Corefcluster_Type)jcasType).casFeatCode_chain, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: head

  /** getter for head - gets 
   * @generated */
  public String getHead() {
    if (Corefcluster_Type.featOkTst && ((Corefcluster_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "edu.cmu.lti.qalab.types.Corefcluster");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Corefcluster_Type)jcasType).casFeatCode_head);}
    
  /** setter for head - sets  
   * @generated */
  public void setHead(String v) {
    if (Corefcluster_Type.featOkTst && ((Corefcluster_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "edu.cmu.lti.qalab.types.Corefcluster");
    jcasType.ll_cas.ll_setStringValue(addr, ((Corefcluster_Type)jcasType).casFeatCode_head, v);}    
  }

    