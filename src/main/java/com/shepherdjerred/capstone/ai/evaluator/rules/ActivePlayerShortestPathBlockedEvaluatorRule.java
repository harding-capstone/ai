package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import com.shepherdjerred.capstone.logic.board.search.BoardAStarSearchNode;
import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActivePlayerShortestPathBlockedEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  /*
  If the shortest path can be blocked off by a single wall placement (legally) so that the player
  can no longer reach the goal on that path, that's very bad and we'll have to backtrack. If that's
  the case, don't follow the shortest path, get the next-best path (probably with a loop so that
  while (possible block)
    { try the next path }

  Check the end of the path to see if a wall can be placed there to completely block it
   */

  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    QuoridorBoard gameBoard = match.getBoard();

    var playerPawnLocation = gameBoard.getPawnLocation(playerToOptimize);
    var playerPawnGoals = playerGoals.getGoalCoordinatesForPlayer(playerToOptimize, gameBoard.getBoardSize());
    var shortestPath = (BoardAStarSearchNode) boardSearch.getPathToAnyDestination(gameBoard, playerPawnLocation, playerPawnGoals);

    Coordinate endSpace = shortestPath.getParent().getLocation();
    Coordinate endSpaceParent = shortestPath.getParent().getParent().getLocation();


    int canBeBlocked = 0;

    while (canBeBlocked == 0 && endSpaceParent != null) {
      Coordinate wallLeft = endSpace.toLeft();
      Coordinate wallLeft2 = wallLeft.toLeft(2);
      Coordinate wallRight = endSpace.toRight();
      Coordinate wallRight2 = wallRight.toRight(2);
      Coordinate wallAbove = endSpace.above();
      Coordinate wallAboveLeft = wallAbove.toLeft(2);
      Coordinate wallAboveRight = wallAbove.toRight(2);

      if (endSpace.getX() > 3 && endSpace.getX() < gameBoard.getGridSize() - 3
          && ((gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight)
          && gameBoard.isEmpty(wallAbove)
          && (gameBoard.isEmpty(wallAboveLeft) || gameBoard.isEmpty(wallAboveRight)))
          ||
          (gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight2)
              && gameBoard.isEmpty(wallAbove) && gameBoard.isEmpty(wallAboveRight))
          || (gameBoard.hasWall(wallLeft2) && gameBoard.hasWall(wallRight)
              && gameBoard.isEmpty(wallAbove) && gameBoard.isEmpty(wallAboveLeft)))) {
        canBeBlocked++;
      }

      //Edge cases, endpath is on the edges of the board: 0, 2, gridsize, gridsize - 2

      else if (endSpace.getX() < 3 || endSpace.getX() > gameBoard.getGridSize() - 3) {
        if (endSpace.getX() == 0
            && (gameBoard.hasPiece(wallRight) || gameBoard.hasPiece(wallRight2))
            && gameBoard.isEmpty(wallAbove)
            && gameBoard.isEmpty(wallAboveRight)) {
          canBeBlocked++;
        }

      }
    }

    return canBeBlocked;
  }

}
