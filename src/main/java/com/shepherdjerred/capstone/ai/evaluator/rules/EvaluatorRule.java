package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;

public interface EvaluatorRule {

  double evaluate(Match match);
}
