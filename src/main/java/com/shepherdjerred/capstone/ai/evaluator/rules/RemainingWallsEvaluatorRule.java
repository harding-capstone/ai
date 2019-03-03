package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;

@ToString
public class RemainingWallsEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId player) {
    return match.getWallsLeft(player);
  }
}
