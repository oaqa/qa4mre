
/* First created by JCasGen Tue Nov 12 12:49:38 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Denotes the coreference cluster
 * Updated by JCasGen Tue Nov 12 14:20:03 EST 2013
 * @generated */
public class Corefcluster_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Corefcluster_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Corefcluster_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Corefcluster(addr, Corefcluster_Type.this);
  			   Corefcluster_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Corefcluster(addr, Corefcluster_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Corefcluster.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.qalab.types.Corefcluster");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public int getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Corefcluster");
    return ll_cas.ll_getIntValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, int v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Corefcluster");
    ll_cas.ll_setIntValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_chain;
  /** @generated */
  final int     casFeatCode_chain;
  /** @generated */ 
  public int getChain(int addr) {
        if (featOkTst && casFeat_chain == null)
      jcas.throwFeatMissing("chain", "edu.cmu.lti.qalab.types.Corefcluster");
    return ll_cas.ll_getRefValue(addr, casFeatCode_chain);
  }
  /** @generated */    
  public void setChain(int addr, int v) {
        if (featOkTst && casFeat_chain == null)
      jcas.throwFeatMissing("chain", "edu.cmu.lti.qalab.types.Corefcluster");
    ll_cas.ll_setRefValue(addr, casFeatCode_chain, v);}
    
  
 
  /** @generated */
  final Feature casFeat_head;
  /** @generated */
  final int     casFeatCode_head;
  /** @generated */ 
  public String getHead(int addr) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "edu.cmu.lti.qalab.types.Corefcluster");
    return ll_cas.ll_getStringValue(addr, casFeatCode_head);
  }
  /** @generated */    
  public void setHead(int addr, String v) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "edu.cmu.lti.qalab.types.Corefcluster");
    ll_cas.ll_setStringValue(addr, casFeatCode_head, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Corefcluster_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.Integer", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_chain = jcas.getRequiredFeatureDE(casType, "chain", "uima.cas.FSList", featOkTst);
    casFeatCode_chain  = (null == casFeat_chain) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_chain).getCode();

 
    casFeat_head = jcas.getRequiredFeatureDE(casType, "head", "uima.cas.String", featOkTst);
    casFeatCode_head  = (null == casFeat_head) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_head).getCode();

  }
}



    