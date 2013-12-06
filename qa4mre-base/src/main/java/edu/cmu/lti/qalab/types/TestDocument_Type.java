
/* First created by JCasGen Wed Feb 20 05:53:44 EST 2013 */
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


/** 
 * Updated by JCasGen Thu Dec 05 14:35:26 EST 2013
 * @generated */
public class TestDocument_Type extends SourceDocument_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TestDocument_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TestDocument_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TestDocument(addr, TestDocument_Type.this);
  			   TestDocument_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TestDocument(addr, TestDocument_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TestDocument.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.qalab.types.TestDocument");
 
  /** @generated */
  final Feature casFeat_qaList;
  /** @generated */
  final int     casFeatCode_qaList;
  /** @generated */ 
  public int getQaList(int addr) {
        if (featOkTst && casFeat_qaList == null)
      jcas.throwFeatMissing("qaList", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getRefValue(addr, casFeatCode_qaList);
  }
  /** @generated */    
  public void setQaList(int addr, int v) {
        if (featOkTst && casFeat_qaList == null)
      jcas.throwFeatMissing("qaList", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setRefValue(addr, casFeatCode_qaList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_readingTestId;
  /** @generated */
  final int     casFeatCode_readingTestId;
  /** @generated */ 
  public String getReadingTestId(int addr) {
        if (featOkTst && casFeat_readingTestId == null)
      jcas.throwFeatMissing("readingTestId", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getStringValue(addr, casFeatCode_readingTestId);
  }
  /** @generated */    
  public void setReadingTestId(int addr, String v) {
        if (featOkTst && casFeat_readingTestId == null)
      jcas.throwFeatMissing("readingTestId", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setStringValue(addr, casFeatCode_readingTestId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_topicId;
  /** @generated */
  final int     casFeatCode_topicId;
  /** @generated */ 
  public String getTopicId(int addr) {
        if (featOkTst && casFeat_topicId == null)
      jcas.throwFeatMissing("topicId", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getStringValue(addr, casFeatCode_topicId);
  }
  /** @generated */    
  public void setTopicId(int addr, String v) {
        if (featOkTst && casFeat_topicId == null)
      jcas.throwFeatMissing("topicId", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setStringValue(addr, casFeatCode_topicId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_c1score;
  /** @generated */
  final int     casFeatCode_c1score;
  /** @generated */ 
  public double getC1score(int addr) {
        if (featOkTst && casFeat_c1score == null)
      jcas.throwFeatMissing("c1score", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_c1score);
  }
  /** @generated */    
  public void setC1score(int addr, double v) {
        if (featOkTst && casFeat_c1score == null)
      jcas.throwFeatMissing("c1score", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_c1score, v);}
    
  
 
  /** @generated */
  final Feature casFeat_precision;
  /** @generated */
  final int     casFeatCode_precision;
  /** @generated */ 
  public double getPrecision(int addr) {
        if (featOkTst && casFeat_precision == null)
      jcas.throwFeatMissing("precision", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_precision);
  }
  /** @generated */    
  public void setPrecision(int addr, double v) {
        if (featOkTst && casFeat_precision == null)
      jcas.throwFeatMissing("precision", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_precision, v);}
    
  
 
  /** @generated */
  final Feature casFeat_correctAnswered;
  /** @generated */
  final int     casFeatCode_correctAnswered;
  /** @generated */ 
  public int getCorrectAnswered(int addr) {
        if (featOkTst && casFeat_correctAnswered == null)
      jcas.throwFeatMissing("correctAnswered", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getIntValue(addr, casFeatCode_correctAnswered);
  }
  /** @generated */    
  public void setCorrectAnswered(int addr, int v) {
        if (featOkTst && casFeat_correctAnswered == null)
      jcas.throwFeatMissing("correctAnswered", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setIntValue(addr, casFeatCode_correctAnswered, v);}
    
  
 
  /** @generated */
  final Feature casFeat_answered;
  /** @generated */
  final int     casFeatCode_answered;
  /** @generated */ 
  public int getAnswered(int addr) {
        if (featOkTst && casFeat_answered == null)
      jcas.throwFeatMissing("answered", "edu.cmu.lti.qalab.types.TestDocument");
    return ll_cas.ll_getIntValue(addr, casFeatCode_answered);
  }
  /** @generated */    
  public void setAnswered(int addr, int v) {
        if (featOkTst && casFeat_answered == null)
      jcas.throwFeatMissing("answered", "edu.cmu.lti.qalab.types.TestDocument");
    ll_cas.ll_setIntValue(addr, casFeatCode_answered, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public TestDocument_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_qaList = jcas.getRequiredFeatureDE(casType, "qaList", "uima.cas.FSList", featOkTst);
    casFeatCode_qaList  = (null == casFeat_qaList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_qaList).getCode();

 
    casFeat_readingTestId = jcas.getRequiredFeatureDE(casType, "readingTestId", "uima.cas.String", featOkTst);
    casFeatCode_readingTestId  = (null == casFeat_readingTestId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_readingTestId).getCode();

 
    casFeat_topicId = jcas.getRequiredFeatureDE(casType, "topicId", "uima.cas.String", featOkTst);
    casFeatCode_topicId  = (null == casFeat_topicId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_topicId).getCode();

 
    casFeat_c1score = jcas.getRequiredFeatureDE(casType, "c1score", "uima.cas.Double", featOkTst);
    casFeatCode_c1score  = (null == casFeat_c1score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_c1score).getCode();

 
    casFeat_precision = jcas.getRequiredFeatureDE(casType, "precision", "uima.cas.Double", featOkTst);
    casFeatCode_precision  = (null == casFeat_precision) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_precision).getCode();

 
    casFeat_correctAnswered = jcas.getRequiredFeatureDE(casType, "correctAnswered", "uima.cas.Integer", featOkTst);
    casFeatCode_correctAnswered  = (null == casFeat_correctAnswered) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_correctAnswered).getCode();

 
    casFeat_answered = jcas.getRequiredFeatureDE(casType, "answered", "uima.cas.Integer", featOkTst);
    casFeatCode_answered  = (null == casFeat_answered) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_answered).getCode();

  }
}



    