package com.shepherdjerred.capstone.ai.evaluator.rule;

import com.shepherdjerred.capstone.ai.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class OptimizingPlayerActualDistanceEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    return boardSearch.findPath(match, playerToOptimize);
  }
}
