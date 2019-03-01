package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.ai.evaluator.EvaluationUtils;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class OptimizingPlayerBlindDistanceEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    var blindDistance = EvaluationUtils.getBlindDistanceToGoal(match.getBoard(), playerToOptimize);
//    log.info("Distance: " + blindDistance);
    return Math.pow(10 - (blindDistance), 3);
  }
}
