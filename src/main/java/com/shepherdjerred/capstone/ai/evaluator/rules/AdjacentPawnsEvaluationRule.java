package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import lombok.ToString;

@ToString
public class AdjacentPawnsEvaluationRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match) {
    var board = match.getBoard();
    var pawnLocation = board.getPawnLocation(match.getActivePlayerId());
    var adjacentPawnLocations = board.getAdjacentPawnSpaces(pawnLocation);
    return adjacentPawnLocations.stream().filter(board::hasPiece).count();
  }
}
