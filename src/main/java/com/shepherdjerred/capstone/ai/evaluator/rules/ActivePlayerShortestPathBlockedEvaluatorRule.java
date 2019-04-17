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
  can no longer reach the goal on that path, that's very bad and we'll have to backtrack.

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
      Coordinate wallAbove = endSpaceParent.above();
      Coordinate wallAbove1Left1 = wallAbove.toLeft();
      Coordinate wallAbove1Left2 = wallAbove.toLeft(2);
      Coordinate wallAbove1Right1 = wallAbove.toRight();
      Coordinate wallAbove1Right2 = wallAbove.toRight(2);

      Coordinate wallBelow = endSpaceParent.below();
      Coordinate wallBelow1Left1 = wallBelow.toLeft();
      Coordinate wallBelow1Left2 = wallBelow.toLeft(2);
      Coordinate wallBelow1Right1 = wallBelow.toRight();
      Coordinate wallBelow1Right2 = wallBelow.toRight(2);


      Coordinate wallUpLeft = endSpaceParent.toLeft().above(2);
      Coordinate wallUpRight = endSpaceParent.toRight().above(2);
      Coordinate wallDownLeft = endSpaceParent.toLeft().below(2);
      Coordinate wallDownRight = endSpaceParent.toRight().below(2);
      Coordinate wallLeft = endSpaceParent.toLeft();
      Coordinate wallRight = endSpaceParent.toRight();
      Coordinate wallLeft2 = endSpaceParent.toLeft(3);
      Coordinate wallRight2 = endSpaceParent.toRight(3);

//TODO Fix these; Watch for Out of Bounds
      if (endSpaceParent.above(2).equals(endSpace)) {

        //middle check
        if (gameBoard.isCoordinateValid(wallLeft) && gameBoard.isCoordinateValid(wallRight)
            && gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight)) {
          if ((gameBoard.isEmpty(wallAbove1Right2) && gameBoard.isEmpty(wallAbove1Right1))
              || (gameBoard.isEmpty(wallAbove1Left2) && gameBoard.isEmpty(wallAbove1Left1))) {
            canBeBlocked = 1;
          }
        }

        //left side spaced out check
        if (gameBoard.isCoordinateValid(wallLeft2) && gameBoard.isCoordinateValid(wallRight)
            && gameBoard.hasWall(wallLeft2) && gameBoard.hasWall(wallRight)) {
          if (gameBoard.isEmpty(wallAbove1Left2) && gameBoard.isEmpty(wallAbove1Left1)) {
            canBeBlocked = 1;
          }
        }

        //right side spaced out check
        if (gameBoard.isCoordinateValid(wallLeft) && gameBoard.isCoordinateValid(wallRight2)
            && gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight2)) {
          if (gameBoard.isEmpty(wallAbove1Right2) && gameBoard.isEmpty(wallAbove1Right1)) {
            canBeBlocked = 1;
          }
        }

        //left border check
        if (endSpaceParent.getX() == 0
            && (gameBoard.hasWall(wallRight) || gameBoard.hasWall(wallRight2))
            && gameBoard.isEmpty(wallAbove1Right1) && gameBoard.isEmpty(wallAbove1Right2)) {
          canBeBlocked = 1;
        }

        //left border +1 check with 1 wall
        if (endSpaceParent.getX() == 2 && gameBoard.hasWall(wallRight)) {
          if ((gameBoard.isEmpty(wallAbove1Left1) && gameBoard.isEmpty(wallAbove1Left2))) {
            canBeBlocked = 1;
          }
        }

        //right border check
        if (endSpaceParent.getX() == gameBoard.getGridSize()
            && (gameBoard.hasWall(wallLeft) || gameBoard.hasWall(wallLeft2))
            && gameBoard.isEmpty(wallAbove1Left1) && gameBoard.isEmpty(wallAbove1Left2)) {
          canBeBlocked = 1;
        }

        //right border -1 check with 1 wall
        if (endSpaceParent.getX() == gameBoard.getGridSize() - 2 && gameBoard.hasWall(wallLeft)) {
          if ((gameBoard.isEmpty(wallAbove1Right1) && gameBoard.isEmpty(wallAbove1Right2))) {
            canBeBlocked = 1;
          }
        }
      } else if (endSpaceParent.below(2).equals(endSpace)) {

        //middle check
        if (gameBoard.isCoordinateValid(wallLeft) && gameBoard.isCoordinateValid(wallRight)
            && gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight)) {
          if ((gameBoard.isEmpty(wallBelow1Right2) && gameBoard.isEmpty(wallBelow1Right1))
              || (gameBoard.isEmpty(wallBelow1Left2) && gameBoard.isEmpty(wallBelow1Left1))) {
            canBeBlocked = 1;
          }
        }

        //left side spaced out check
        if (gameBoard.isCoordinateValid(wallLeft2) && gameBoard.isCoordinateValid(wallRight)
            && gameBoard.hasWall(wallLeft2) && gameBoard.hasWall(wallRight)) {
          if (gameBoard.isEmpty(wallBelow1Left2) && gameBoard.isEmpty(wallBelow1Left1)) {
            canBeBlocked = 1;
          }
        }

        //right side spaced out check
        if (gameBoard.isCoordinateValid(wallLeft) && gameBoard.isCoordinateValid(wallRight2)
            && gameBoard.hasWall(wallLeft) && gameBoard.hasWall(wallRight2)) {
          if (gameBoard.isEmpty(wallAbove1Right2) && gameBoard.isEmpty(wallAbove1Right1)) {
            canBeBlocked = 1;
          }
        }



      }


/*
      if (endSpaceParent.above(2).equals(endSpace)
          && ((gameBoard.isEmpty(wallBelowLeft) && gameBoard.hasWall(wallDownRight))
          || (gameBoard.isEmpty(wallBelowRight) && gameBoard.hasWall(wallDownLeft))) ) {
        canBeBlocked++;
      } else if (endSpaceParent.below(2).equals(endSpace)
          && ((gameBoard.isEmpty(wallAboveLeft) && gameBoard.hasWall(wallUpRight))
          || (gameBoard.isEmpty(wallAboveRight) && gameBoard.hasWall(wallUpLeft))) ) {
        canBeBlocked++;
      } else if (endSpaceParent.toLeft(2).equals(endSpace)
          && (gameBoard.isEmpty(wallUpRight) || gameBoard.isEmpty(wallDownRight)) ) {
        canBeBlocked++;
      } else if (endSpaceParent.toRight(2).equals(endSpace)
          && (gameBoard.isEmpty(wallUpLeft) || gameBoard.isEmpty(wallDownLeft)) ) {
        canBeBlocked++;
      }
*/
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
