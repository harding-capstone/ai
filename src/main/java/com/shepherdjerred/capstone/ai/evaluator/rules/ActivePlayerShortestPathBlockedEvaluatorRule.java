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
    var playerPawnGoals = playerGoals.getGoalCoordinatesForPlayer(playerToOptimize,
        gameBoard.getBoardSize());
    var shortestPath = (BoardAStarSearchNode) boardSearch.getPathToAnyDestination(gameBoard,
        playerPawnLocation, playerPawnGoals);
   // shortestPath = shortestPath.getParent();

    Coordinate endSpace = shortestPath.getLocation();
    var endSpaceParentNode = shortestPath.getParent();
    Coordinate endSpaceParent = endSpaceParentNode.getLocation();



    double canBeBlocked = 0;

    while (canBeBlocked == 0 && endSpaceParentNode != null) {
      Coordinate wallAbove = endSpace.above();
      Coordinate wallAboveLeft = wallAbove.toLeft(2);
      Coordinate wallAboveRight = wallAbove.toRight(2);
      Coordinate wallBelow = endSpace.below();
      Coordinate wallBelowLeft = wallBelow.toLeft(2);
      Coordinate wallBelowRight = wallBelow.toRight(2);
      Coordinate wallUpLeft = endSpace.toLeft().above(2);
      Coordinate wallUpRight = endSpace.toRight().above(2);
      Coordinate wallDownLeft = endSpace.toLeft().below(2);
      Coordinate wallDownRight = endSpace.toRight().below(2);


      if (endSpaceParent.above(2).equals(endSpace)
          && (gameBoard.isEmpty(wallBelowLeft) || gameBoard.isEmpty(wallBelowRight)) ) {
        canBeBlocked++;
      } else if (endSpaceParent.below(2).equals(endSpace)
          && (gameBoard.isEmpty(wallAboveLeft) || gameBoard.isEmpty(wallAboveRight)) ) {
        canBeBlocked++;
      } else if (endSpaceParent.toLeft(2).equals(endSpace)
          && (gameBoard.isEmpty(wallUpRight) || gameBoard.isEmpty(wallDownRight)) ) {
        canBeBlocked++;
      } else if (endSpaceParent.toRight(2).equals(endSpace)
          && (gameBoard.isEmpty(wallUpLeft) || gameBoard.isEmpty(wallDownLeft)) ) {
        canBeBlocked++;
      }

      shortestPath = shortestPath.getParent();
      endSpace = shortestPath.getLocation();
      endSpaceParentNode = shortestPath.getParent();

      if (endSpaceParentNode != null) {
        endSpaceParent = endSpaceParentNode.getLocation();
      }


/*
      if (playerToOptimize.equals(QuoridorPlayer.ONE)) {
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
              && (gameBoard.hasWall(wallRight) || gameBoard.hasWall(wallRight2))
              && gameBoard.isEmpty(wallAbove) && gameBoard.isEmpty(wallAboveRight)) {
            canBeBlocked++;
          }
          else if (endSpace.getX() == 2
              && ((gameBoard.hasWall(wallRight) && gameBoard.isEmpty(wallAbove)
              && gameBoard.isEmpty(wallAboveLeft))

              || (gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight)
              && gameBoard.isEmpty(wallAbove)
              && (gameBoard.isEmpty(wallAboveLeft) || gameBoard.isEmpty(wallAboveRight)))

              || (gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight2)
              && gameBoard.isEmpty(wallAbove) && gameBoard.isEmpty(wallAboveRight)))) {
            canBeBlocked++;
          }
          else if (endSpace.getX() == gameBoard.getGridSize()
              && (gameBoard.hasWall(wallLeft) || gameBoard.hasWall(wallLeft2))
              && gameBoard.isEmpty(wallAbove) && gameBoard.isEmpty(wallAboveLeft)) {
            canBeBlocked++;
          }
          else if (endSpace.getX() == gameBoard.getGridSize() - 2
              && ((gameBoard.hasWall(wallLeft) && gameBoard.isEmpty(wallAbove)
              && gameBoard.isEmpty(wallAboveRight))

              || (gameBoard.hasWall(wallRight) && gameBoard.hasWall(wallLeft)
              && gameBoard.isEmpty(wallAbove)
              && (gameBoard.isEmpty(wallAboveLeft) || gameBoard.isEmpty(wallAboveRight)))

              || (gameBoard.hasWall(wallRight) && gameBoard.hasWall(wallLeft2)
              && gameBoard.isEmpty(wallAbove) && gameBoard.isEmpty(wallAboveLeft)))) {
            canBeBlocked++;
          }

        } else if (endSpace.getY() > 3 && endSpace.getY() < endSpace.getY() - 3)

        //may take alot of computing time to do
        shortestPath = shortestPath.getParent();
        endSpace = shortestPath.getLocation();
        endSpaceParent = shortestPath.getParent().getLocation();

      } else if (playerToOptimize.equals(QuoridorPlayer.TWO)) {

      }
*/
    }

    return canBeBlocked;
  }

}
