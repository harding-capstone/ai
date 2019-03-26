package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import lombok.ToString;

@ToString
public class OpponentJumpPotentialEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, QuoridorPlayer player) {
    if (match.getActivePlayerId().equals(player)) {
      return 0;
    }

    var board = match.getBoard();
    var pawnLocation = board.getPawnLocation(player);
    var adjacentPawnLocations = board.getPawnSpacesAdjacentToPawnSpace(pawnLocation);
    return adjacentPawnLocations.stream().filter(board::hasPiece).count();
  }
}
