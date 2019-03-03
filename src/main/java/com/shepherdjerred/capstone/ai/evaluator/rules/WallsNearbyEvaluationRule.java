package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;

@ToString
public class WallsNearbyEvaluationRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId player) {
    var board = match.getBoard();
    var pawnLocation = board.getPawnLocation(player);
    var adjacentWallLocations = board.getAdjacentWallSpaces(pawnLocation);
    return adjacentWallLocations.stream().filter(board::hasPiece).count();
  }
}
