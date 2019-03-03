package com.shepherdjerred.capstone.ai.alphabeta.pruning;

import com.github.bentorfs.ai.common.TreeNode;
import com.shepherdjerred.capstone.ai.alphabeta.IQuoridorNode;
import com.shepherdjerred.capstone.ai.alphabeta.QuoridorNode;
import com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString(exclude = {"match", "matchEvaluator"})
@AllArgsConstructor
class DistancePruningQuoridorNode implements IQuoridorNode {

  private final PlayerId activePlayer;
  private final Match match;
  private final Turn turn;
  private final MatchEvaluator matchEvaluator;
  private final int distance;

  @Override
  public Collection<TreeNode> getChildNodes() {
    var turnGenerator = new TurnGenerator();
    var possibleTurns = turnGenerator.generateValidTurns(match);
    var pieces = match.getBoard().getPieceLocations();

    return possibleTurns.stream()
        .filter(turn -> {
          if (turn instanceof MovePawnTurn) {
            return true;
          } else if (turn instanceof PlaceWallTurn) {
            Set<Coordinate> turnCoordinates = new HashSet<>();
            var wallLocation = ((PlaceWallTurn) turn).getLocation();
            turnCoordinates.add(wallLocation.getFirstCoordinate());
            turnCoordinates.add(wallLocation.getVertex());
            turnCoordinates.add(wallLocation.getSecondCoordinate());
            return pieces.stream().anyMatch(piece -> turnCoordinates.stream().anyMatch(coord ->
                Coordinate.calculateManhattanDistance(piece, coord) <= distance));
          } else {
            throw new UnsupportedOperationException();
          }
        })
        .map(turn -> {
//          System.out.println(turn);
          var newMatchState = match.doTurn(turn);
          return new QuoridorNode(activePlayer, newMatchState, turn, matchEvaluator);
        })
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isSolutionNode() {
    return match.getMatchStatus().getVictor() == activePlayer;
  }

  @Override
  public double getValue() {
    return matchEvaluator.evaluateMatch(match);
  }
}
