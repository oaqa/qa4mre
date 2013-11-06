<?xml version="1.0" encoding="UTF-8"?>

<analysisEngineDescription xmlns="http://uima.apache.org/resourceSpecifier">
  <frameworkImplementation>org.apache.uima.java</frameworkImplementation>
  <primitive>true</primitive>  <annotatorImplementationName>edu.cmu.lti.deiis.hw5.answer_ranking.AnswerChoiceCandAnsNPMIScorer</annotatorImplementationName>
  <analysisEngineMetaData>
    <name>AnswerChoiceCandAnsNPMIScorer</name>
    <description/>
    <version>1.0</version>
    <vendor/>
    <configurationParameters>
      <configurationParameter>
        <name>SOLR_SERVER_URL</name>
        <type>String</type>
        <multiValued>false</multiValued>
        <mandatory>false</mandatory>
      </configurationParameter>
      <configurationParameter>
        <name>K_CANDIDATES</name>
        <type>String</type>
        <multiValued>false</multiValued>
        <mandatory>false</mandatory>
      </configurationParameter>
    </configurationParameters>
    <configurationParameterSettings/>
    <typeSystemDescription/>
    <typePriorities/>
    <fsIndexCollection/>
    <capabilities>
      <capability>
        <inputs/>
        <outputs/>
        <languagesSupported/>
      </capability>
    </capabilities>
  <operationalProperties>
      <modifiesCas>true</modifiesCas>
      <multipleDeploymentAllowed>true</multipleDeploymentAllowed>
      <outputsNewCASes>false</outputsNewCASes>
    </operationalProperties>
  </analysisEngineMetaData>
  <resourceManagerConfiguration/>
</analysisEngineDescription>
