package com.shepherdjerred.capstone.ai.evaluator.rule;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;

@ToString
public class RemainingWallsEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    var remainingWalls = match.getWallsLeft(playerToOptimize);
    return remainingWalls;
  }
}
