package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import java.util.Set;

//@Log4j2
public class EvaluationUtils {
  public static int getBlindDistanceToGoal(Board board, PlayerId playerId) {
    var pawn = board.getPawnLocation(playerId);
    var goals = getGoals(board.getBoardSettings().getGridSize(), playerId);
    return goals.stream()
        .map(goal -> Coordinate.calculateManhattanDistance(pawn, goal) / 2)
        .min(Integer::compareTo)
        .orElseThrow(IllegalStateException::new);
  }

  public static Set<Coordinate> getGoals(int gridSize, PlayerId playerId) {
    Set<Coordinate> goals = new HashSet<>();
    switch (playerId) {
      case ONE:
        for (int x = 0; x < gridSize; x += 2) {
          goals.add(new Coordinate(x, gridSize - 1));
        }
        break;
      case TWO:
        for (int x = 0; x < gridSize; x += 2) {
          goals.add(new Coordinate(x, 0));
        }
        break;
      case THREE:
        for (int y = 0; y < gridSize; y += 2) {
          goals.add(new Coordinate(gridSize - 1, y));
        }
        break;
      case FOUR:
        for (int y = 0; y < gridSize; y += 2) {
          goals.add(new Coordinate(0, y));
        }
        break;
      case NULL:
        throw new UnsupportedOperationException();
      default:
        throw new UnsupportedOperationException();
    }
    return goals;
  }

  public static int getDistanceToGoal(Match match, PlayerId playerId) {
    // TODO shortest path
    return 0;
  }
}
