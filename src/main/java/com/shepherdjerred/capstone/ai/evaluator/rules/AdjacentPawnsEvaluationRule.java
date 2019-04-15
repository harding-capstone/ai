package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import lombok.ToString;

@ToString
public class AdjacentPawnsEvaluationRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, QuoridorPlayer player) {
    if (!match.getActivePlayerId().equals(player)) {
      return 0;
    }

    double adjacentPawns = 0;
    var board = match.getBoard();
    var pawnLocation = board.getPawnLocation(player);

    if (board.isCoordinateValid(pawnLocation.toLeft(2)) && board.isEmpty(pawnLocation.toLeft())
        && board.hasPiece(pawnLocation.toLeft(2))) {
      adjacentPawns++;
    }

    if (board.isCoordinateValid(pawnLocation.toRight(2)) && board.isEmpty(pawnLocation.toRight())
        && board.hasPiece(pawnLocation.toRight(2))) {
      adjacentPawns++;
    }

    if (board.isCoordinateValid(pawnLocation.above(2)) && board.isEmpty(pawnLocation.above())
        && board.hasPiece(pawnLocation.above(2))) {
      adjacentPawns++;
    }

    if (board.isCoordinateValid(pawnLocation.below(2)) && board.isEmpty(pawnLocation.below())
        && board.hasPiece(pawnLocation.below(2))) {
      adjacentPawns++;
    }

    return adjacentPawns;
  }
}
