
/* First created by JCasGen Tue Nov 05 22:09:09 EST 2013 */
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

/** 
 * Updated by JCasGen Thu Nov 21 19:37:48 EST 2013
 * @generated */
public class CandidateSentenceAnswerSet_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CandidateSentenceAnswerSet_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CandidateSentenceAnswerSet_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CandidateSentenceAnswerSet(addr, CandidateSentenceAnswerSet_Type.this);
  			   CandidateSentenceAnswerSet_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CandidateSentenceAnswerSet(addr, CandidateSentenceAnswerSet_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CandidateSentenceAnswerSet.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
 
  /** @generated */
  final Feature casFeat_candidateSentenceList;
  /** @generated */
  final int     casFeatCode_candidateSentenceList;
  /** @generated */ 
  public int getCandidateSentenceList(int addr) {
        if (featOkTst && casFeat_candidateSentenceList == null)
      jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    return ll_cas.ll_getRefValue(addr, casFeatCode_candidateSentenceList);
  }
  /** @generated */    
  public void setCandidateSentenceList(int addr, int v) {
        if (featOkTst && casFeat_candidateSentenceList == null)
      jcas.throwFeatMissing("candidateSentenceList", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    ll_cas.ll_setRefValue(addr, casFeatCode_candidateSentenceList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_answer;
  /** @generated */
  final int     casFeatCode_answer;
  /** @generated */ 
  public int getAnswer(int addr) {
        if (featOkTst && casFeat_answer == null)
      jcas.throwFeatMissing("answer", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    return ll_cas.ll_getRefValue(addr, casFeatCode_answer);
  }
  /** @generated */    
  public void setAnswer(int addr, int v) {
        if (featOkTst && casFeat_answer == null)
      jcas.throwFeatMissing("answer", "edu.cmu.lti.qalab.types.CandidateSentenceAnswerSet");
    ll_cas.ll_setRefValue(addr, casFeatCode_answer, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public CandidateSentenceAnswerSet_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_candidateSentenceList = jcas.getRequiredFeatureDE(casType, "candidateSentenceList", "uima.cas.FSList", featOkTst);
    casFeatCode_candidateSentenceList  = (null == casFeat_candidateSentenceList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_candidateSentenceList).getCode();

 
    casFeat_answer = jcas.getRequiredFeatureDE(casType, "answer", "edu.cmu.lti.qalab.types.Answer", featOkTst);
    casFeatCode_answer  = (null == casFeat_answer) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_answer).getCode();

  }
}



    