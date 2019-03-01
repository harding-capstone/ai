package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;

@ToString
public class OpponentJumpPotentialEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    Coordinate playerLocation = match.getBoard().getPawnLocation(playerToOptimize);
    double scoreValue;
    double penalties = 0;
    if (!match.getActivePlayerId().equals(playerToOptimize)) {

      if (match.getBoard().hasPiece(playerLocation.toLeft(2))) {
        penalties++;
      }

      if (match.getBoard().hasPiece(playerLocation.above(2))) {
        penalties++;
      }

      if (match.getBoard().hasPiece(playerLocation.toRight(2))) {
        penalties++;
      }

      if (match.getBoard().hasPiece(playerLocation.below(2))) {
        penalties++;
      }
    }

    scoreValue = 33.3 * penalties;

    return scoreValue;
  }
}
