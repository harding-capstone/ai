package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import lombok.ToString;

@ToString
public class RemainingWallsEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match) {
    return match.getWallsLeft(match.getActivePlayerId());
  }
}
