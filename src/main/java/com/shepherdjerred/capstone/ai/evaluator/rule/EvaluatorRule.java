package com.shepherdjerred.capstone.ai.evaluator.rule;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;

public interface EvaluatorRule {

  double evaluate(Match match, PlayerId playerToOptimize);
}
