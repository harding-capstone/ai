package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;

public interface MatchEvaluator {

  double MAX_SCORE = Double.MAX_VALUE;
  double MIN_SCORE = Double.MIN_NORMAL;

  double evaluateMatch(Match match);
}
