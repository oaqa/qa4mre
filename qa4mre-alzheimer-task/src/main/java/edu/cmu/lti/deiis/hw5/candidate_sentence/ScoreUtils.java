package edu.cmu.lti.deiis.hw5.candidate_sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;

public class ScoreUtils {
  public static final int UNIGRAM = 0;

  public static final int BIGRAM = 1;

  public static final int NOUN_PHRASE = 2;

  public static final int POSTAG = 3;

  public static final int DEPENDENCY_LEAF = 4;

  public static final int COSINE = 5;

  public static final int LANGUAGE_MODEL = 6;

  public static final int DICE = 7;

  public static final int JACCARD = 8;
  
  public static final int MATCH = 9;
  
  public static final int MATCH_COUNT = 10;

  protected static Map<String, Double> FormExpression(Answer RawString, int sExpType) {
    Map<String, Double> hFeature = new HashMap<String, Double>();
    ArrayList<String> vTerm = new ArrayList<String>();
    if (sExpType == UNIGRAM) {
      vTerm = GetUnigram(RawString);
    }
    if (sExpType == BIGRAM) {
      vTerm = GetBigram(RawString);
    }
    if (sExpType == NOUN_PHRASE) {
      vTerm = GetNounPhrase(RawString);
    }
    if (sExpType == POSTAG) {
      vTerm = GetPostag(RawString);
    }
    if (sExpType == DEPENDENCY_LEAF) {
      vTerm = GetDependencyLeaf(RawString);
    }

    hFeature = TfCounter(vTerm);
    return hFeature;
  }

  protected static Map<String, Double> FormExpression(Question RawString, int sExpType) {
    Map<String, Double> hFeature = new HashMap<String, Double>();
    ArrayList<String> vTerm = new ArrayList<String>();
    if (sExpType == UNIGRAM) {
      vTerm = GetUnigram(RawString);
    }
    if (sExpType == BIGRAM) {
      vTerm = GetBigram(RawString);
    }
    if (sExpType == NOUN_PHRASE) {
      vTerm = GetNounPhrase(RawString);
    }
    if (sExpType == POSTAG) {
      vTerm = GetPostag(RawString);
    }
    if (sExpType == DEPENDENCY_LEAF) {
      vTerm = GetDependencyLeaf(RawString);
    }

    hFeature = TfCounter(vTerm);
    return hFeature;
  }

  protected static Map<String, Double> FormExpression(Sentence sentence, int sExpType) {
    Map<String, Double> hFeature = new HashMap<String, Double>();
    ArrayList<String> vTerm = new ArrayList<String>();
    if (sExpType == UNIGRAM) {
      vTerm = GetUnigram(sentence);
    }
    if (sExpType == BIGRAM) {
      vTerm = GetBigram(sentence);
    }
    if (sExpType == NOUN_PHRASE) {
      vTerm = GetNounPhrase(sentence);
    }
    if (sExpType == POSTAG) {
      vTerm = GetPostag(sentence);
    }
    if (sExpType == DEPENDENCY_LEAF) {
      vTerm = GetDependencyLeaf(sentence);
    }

    hFeature = TfCounter(vTerm);
    return hFeature;
  }

  protected static Map<String, Double> TfCounter(ArrayList<String> vTerm) {
    Map<String, Double> hTerm = new HashMap<String, Double>();
    for (int i = 0; i < vTerm.size(); i++) {
      if (!hTerm.containsKey(vTerm.get(i))) {
        hTerm.put(vTerm.get(i), 0.0);
      }
      hTerm.put(vTerm.get(i), hTerm.get(vTerm.get(i)) + 1.0);
    }
    return hTerm;
  }

  protected static ArrayList<String> GetUnigram(Answer answer) {
    ArrayList<Token> tokenList = Utils.getTokenListFromAnswer(answer);
    ArrayList<String> results = new ArrayList<String>();
    for (Token token : tokenList)
      results.add(token.getText());
    return results;
  }

  protected static ArrayList<String> GetUnigram(Sentence sentence) {
    ArrayList<Token> tokenList = Utils.fromFSListToCollection(sentence.getTokenList(), Token.class);
    ArrayList<String> results = new ArrayList<String>();
    for (Token token : tokenList)
      results.add(token.getText());
    return results;
  }

  protected static ArrayList<String> GetUnigram(Question RawString) {
    ArrayList<Token> tokenList = Utils
            .fromFSListToCollection(RawString.getTokenList(), Token.class);
    ArrayList<String> results = new ArrayList<String>();
    for (Token token : tokenList)
      results.add(token.getText());
    return results;
  }

  // TODO
  protected static ArrayList<String> GetBigram(Answer RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();
    return vTerm;
  }

  // TODO
  protected static ArrayList<String> GetBigram(Sentence RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();
    return vTerm;
  }

  // TODO
  protected static ArrayList<String> GetBigram(Question RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();
    return vTerm;
  }

  protected static ArrayList<String> GetNounPhrase(Answer answer) {
    ArrayList<NounPhrase> nplist = Utils.fromFSListToCollection(answer.getNounPhraseList(),
            NounPhrase.class);
    ArrayList<String> vTerm = new ArrayList<String>();
    for (NounPhrase np : nplist) {
      vTerm.add(np.getText());
    }
    return vTerm;
  }

  protected static ArrayList<String> GetNounPhrase(Sentence sentence) {
    ArrayList<NounPhrase> nplist = Utils.fromFSListToCollection(sentence.getPhraseList(),
            NounPhrase.class);
    ArrayList<String> vTerm = new ArrayList<String>();
    for (NounPhrase np : nplist) {
      vTerm.add(np.getText());
    }
    return vTerm;
  }

  protected static ArrayList<String> GetNounPhrase(Question question) {
    ArrayList<NounPhrase> nplist = Utils.fromFSListToCollection(question.getNounList(),
            NounPhrase.class);
    ArrayList<String> vTerm = new ArrayList<String>();
    for (NounPhrase np : nplist) {
      vTerm.add(np.getText());
    }
    return vTerm;
  }

  // TODO
  protected static ArrayList<String> GetPostag(Answer RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();

    return vTerm;
  }
  
  // TODO
  protected static ArrayList<String> GetPostag(Sentence RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();

    return vTerm;
  }
  
  // TODO
  protected static ArrayList<String> GetPostag(Question RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();

    return vTerm;
  }
  
  // TODO
  protected static ArrayList<String> GetDependencyLeaf(Answer answer) {
    ArrayList<String> vTerm = new ArrayList<String>();
    return vTerm;
  }

  // TODO
  protected static ArrayList<String> GetDependencyLeaf(Sentence RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();
    return vTerm;
  }

  // TODO
  protected static ArrayList<String> GetDependencyLeaf(Question RawString) {
    ArrayList<String> vTerm = new ArrayList<String>();

    return vTerm;
  }

  public static double[] CalcScore(Sentence sentence, Question question, Answer answer, int sExpType,
          int sScoreFuncType) {
    Map<String, Double> sentenceFeature = FormExpression(sentence, sExpType);
    Map<String, Double> questionFeature = FormExpression(question, sExpType);
    Map<String, Double> answerFeature = FormExpression(answer, sExpType);
    double score_s_q = CalcScore(sentenceFeature, questionFeature, sScoreFuncType);
    double score_s_a = CalcScore(sentenceFeature, answerFeature, sScoreFuncType);
    double[] ret = new double[2];
    ret[0] = score_s_q;
    ret[1] = score_s_a;
    return ret;

  }

  /**
   * calculate score of two feature vector. based on set sScoreFuncType.
   * 
   * @param hFeatureA
   * @param hFeatureB
   * @return
   */
  protected static Double CalcScore(Map<String, Double> hFeatureA, Map<String, Double> hFeatureB,
          int sScoreFuncType) {
    Double score = 0.0;

    if (sScoreFuncType == COSINE) {
      score = Cosine(hFeatureA, hFeatureB);
    }
    if (sScoreFuncType == LANGUAGE_MODEL) {
      score = LanguageModel(hFeatureA, hFeatureB);
    }
    if (sScoreFuncType == DICE) {
      score = DiceCoefficient(hFeatureA, hFeatureB);
    }
    if (sScoreFuncType == JACCARD) {
      score = JaccardCoefficient(hFeatureA, hFeatureB);
    }
    if (sScoreFuncType == MATCH) {
      score = Match(hFeatureA, hFeatureB);
    }
    if (sScoreFuncType == MATCH_COUNT) {
      score = Match_Count(hFeatureA, hFeatureB);
    }
    
    return score;
  }
  
  protected static Double Match_Count(Map<String, Double> f1, Map<String, Double> f2){
    Map<String, Double> shorter = f1.size() > f2.size() ? f2 : f1;
    Map<String, Double> longer = f1.size() > f2.size() ? f1 : f2;
    double score = 0.0;
    for (String key : shorter.keySet())
      if (longer.containsKey(key))
        score += 1.0;
    return score;
  }
  
  protected static Double Match(Map<String, Double> f1, Map<String, Double> f2){
    Map<String, Double> shorter = f1.size() > f2.size() ? f2 : f1;
    Map<String, Double> longer = f1.size() > f2.size() ? f1 : f2;
    for (String key : shorter.keySet())
      if (longer.containsKey(key))
        return 1.0;
    return 0.0;
  }

  protected static Double Cosine(Map<String, Double> hFeatureA, Map<String, Double> hFeatureB) {
    Double score = 0.0;
    score = DotProduct(hFeatureA, hFeatureB) / (SetNorm(hFeatureA, 2) * SetNorm(hFeatureB, 2));
    return score;
  }

  /**
   * calculate the probability of hFeatureA|hFeatureB
   * 
   * @param hFeatureA
   * @param hFeatureB
   * @return
   */
  protected static Double LanguageModel(Map<String, Double> hFeatureA, Map<String, Double> hFeatureB) {
    Double score = 0.0;
    Double n = SetNorm(hFeatureB, 1);
    // no smoothing now, only count the intersect terms
    for (Entry<String, Double> entry : hFeatureA.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      if (hFeatureB.containsKey(key)) {
        Double p = hFeatureB.get(key);
        p = p / n;
        score += value * Math.log(p);
      }
    }
    return score;
  }

  protected static Double DiceCoefficient(Map<String, Double> hFeatureA,
          Map<String, Double> hFeatureB) {
    Double score = 0.0;
    score = 2 * SetNorm(SetIntersect(hFeatureA, hFeatureB), 1)
            / (SetNorm(hFeatureA, 1) + SetNorm(hFeatureB, 1));
    return score;
  }

  protected static Double JaccardCoefficient(Map<String, Double> hFeatureA,
          Map<String, Double> hFeatureB) {
    Double score = 0.0;
    score = SetNorm(SetIntersect(hFeatureA, hFeatureB), 1)
            / SetNorm(SetUnion(hFeatureA, hFeatureB), 1);
    return score;
  }

  protected static Map<String, Double> SetUnion(Map<String, Double> hFeatureA,
          Map<String, Double> hFeatureB) {
    Map<String, Double> hMerge = new HashMap<String, Double>();

    for (Entry<String, Double> entry : hFeatureA.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      if (hFeatureB.containsKey(key)) {
        value = Math.max(hFeatureB.get(key), value);
      }
      hMerge.put(key, value);
    }
    return hMerge;
  }

  protected static Map<String, Double> SetIntersect(Map<String, Double> hFeatureA,
          Map<String, Double> hFeatureB) {
    Map<String, Double> hMerge = new HashMap<String, Double>();

    for (Entry<String, Double> entry : hFeatureA.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      if (hFeatureB.containsKey(key)) {
        value = Math.min(hFeatureB.get(key), value);
        hMerge.put(key, value);
      }
    }
    return hMerge;
  }

  protected static Double SetNorm(Map<String, Double> hFeature, Integer NormNum) {
    Double res = 0.0;
    for (Entry<String, Double> entry : hFeature.entrySet()) {
      res += Math.pow(Math.abs(entry.getValue()), NormNum);
    }
    res = Math.pow(res, 1.0 / NormNum);
    return res;
  }

  protected static Double DotProduct(Map<String, Double> hFeatureA, Map<String, Double> hFeatureB) {
    Double res = 0.0;
    for (Entry<String, Double> entry : hFeatureA.entrySet()) {
      if (hFeatureB.containsKey(entry.getKey())) {
        res += entry.getValue() * hFeatureB.get(entry.getKey());
      }
    }
    return res;
  }
}
