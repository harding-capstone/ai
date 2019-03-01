package com.shepherdjerred.capstone.ai.evaluator.rules;

import static com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator.MAX_SCORE;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;

@ToString
public class VictoryEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    if (match.getMatchStatus().getVictor() == playerToOptimize) {
      return MAX_SCORE;
    } else {
      return 0.0;
    }
  }
}
