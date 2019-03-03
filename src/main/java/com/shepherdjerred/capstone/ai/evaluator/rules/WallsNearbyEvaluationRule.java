package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import lombok.ToString;

@ToString
public class WallsNearbyEvaluationRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match) {
    var board = match.getBoard();
    var pawnLocation = board.getPawnLocation(match.getActivePlayerId());
    var adjacentWallLocations = board.getAdjacentWallSpaces(pawnLocation);
    return adjacentWallLocations.stream().filter(board::hasPiece).count();
  }
}
