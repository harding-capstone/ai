package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;

public class OptimizingPlayerJumpPotentialEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    double scoreValue;
    double bonuses = 0;
    Coordinate playerLocation = match.getBoard().getPawnLocation(playerToOptimize);
    if (match.getActivePlayerId().equals(playerToOptimize)) {
      if (match.getBoard().hasPiece(playerLocation.above(2))) {
        bonuses++;
      }

      if (match.getBoard().hasPiece(playerLocation.toLeft(2))) {
        bonuses++;
      }

      if (match.getBoard().hasPiece(playerLocation.toRight(2))) {
        bonuses++;
      }

      if (match.getBoard().hasPiece(playerLocation.below(2))) {
        bonuses++;
      }
    }

    scoreValue = 100 - bonuses * 33.3;
    return scoreValue;
  }
}
