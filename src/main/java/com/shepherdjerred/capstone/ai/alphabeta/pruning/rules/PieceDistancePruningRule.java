package com.shepherdjerred.capstone.ai.alphabeta.pruning.rules;

import com.shepherdjerred.capstone.ai.alphabeta.pruning.PruningQuoridorNode;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class PieceDistancePruningRule implements PruningRule {

  private final int distance;

  @Override
  public boolean shouldPrune(PruningQuoridorNode node) {
    var match = node.getMatch();
    var turn = node.getTurn();
    var pieces = match.getBoard().getPieceLocations();

    if (turn instanceof MovePawnTurn) {
      return false;
    } else if (turn instanceof PlaceWallTurn) {
      Set<Coordinate> turnCoordinates = new HashSet<>();
      var wallLocation = ((PlaceWallTurn) turn).getLocation();
      turnCoordinates.add(wallLocation.getFirstCoordinate());
      turnCoordinates.add(wallLocation.getVertex());
      turnCoordinates.add(wallLocation.getSecondCoordinate());
      return pieces.stream()
          .noneMatch(piece -> turnCoordinates.stream()
              .anyMatch(coord -> Coordinate.calculateManhattanDistance(piece, coord) <= distance));
    } else {
      throw new UnsupportedOperationException();
    }

  }
}
