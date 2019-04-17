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
        gameBoard.getGridSize());
    var shortestPath = (BoardAStarSearchNode) boardSearch.getPathToAnyDestination(gameBoard,
        playerPawnLocation, playerPawnGoals);

    Coordinate endSpace = shortestPath.getLocation();
    var endSpaceParentNode = shortestPath.getParent();
    Coordinate endSpaceParent = endSpaceParentNode.getLocation();



    double canBeBlocked = 0;

    while (canBeBlocked == 0 && endSpaceParentNode != null) {
      Coordinate wallAbove = endSpaceParent.above();
      Coordinate wallAbove2 = endSpaceParent.above(3);
      Coordinate wallAbove1Left1 = wallAbove.toLeft();
      Coordinate wallAbove1Left2 = wallAbove.toLeft(2);
      Coordinate wallAbove2Left1 = wallAbove.above().toLeft();

      Coordinate wallAbove1Right1 = wallAbove.toRight();
      Coordinate wallAbove1Right2 = wallAbove.toRight(2);
      Coordinate wallAbove2Right1 = wallAbove.above().toRight();


      Coordinate wallBelow = endSpaceParent.below();
      Coordinate wallBelow2 = endSpaceParent.below(3);
      Coordinate wallBelow1Left1 = wallBelow.toLeft();
      Coordinate wallBelow1Left2 = wallBelow.toLeft(2);
      Coordinate wallBelow2Left1 = wallBelow.below().toLeft();

      Coordinate wallBelow1Right1 = wallBelow.toRight();
      Coordinate wallBelow1Right2 = wallBelow.toRight(2);
      Coordinate wallBelow2Right1 = wallBelow.below().toRight();

      Coordinate wallLeft = endSpaceParent.toLeft();
      Coordinate wallRight = endSpaceParent.toRight();
      Coordinate wallLeft2 = endSpaceParent.toLeft(3);
      Coordinate wallRight2 = endSpaceParent.toRight(3);

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
        if (endSpaceParent.getX() == gameBoard.getGridSize() - 1
            && (gameBoard.hasWall(wallLeft) || gameBoard.hasWall(wallLeft2))
            && gameBoard.isEmpty(wallAbove1Left1) && gameBoard.isEmpty(wallAbove1Left2)) {
          canBeBlocked = 1;
        }

        //right border -1 check with 1 wall
        if (endSpaceParent.getX() == gameBoard.getGridSize() - 3 && gameBoard.hasWall(wallLeft)) {
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
          if (gameBoard.isEmpty(wallBelow1Right2) && gameBoard.isEmpty(wallBelow1Right1)) {
            canBeBlocked = 1;
          }
        }

        //left border check
        if (endSpaceParent.getX() == 0
            && (gameBoard.hasWall(wallRight) || gameBoard.hasWall(wallRight2))
            && gameBoard.isEmpty(wallBelow1Right1) && gameBoard.isEmpty(wallBelow1Right2)) {
          canBeBlocked = 1;
        }

        //left border +1 check with 1 wall
        if (endSpaceParent.getX() == 2 && gameBoard.hasWall(wallRight)) {
          if ((gameBoard.isEmpty(wallBelow1Left1) && gameBoard.isEmpty(wallBelow1Left2))) {
            canBeBlocked = 1;
          }
        }

        //right border check
        if (endSpaceParent.getX() == gameBoard.getGridSize() - 1
            && (gameBoard.hasWall(wallLeft) || gameBoard.hasWall(wallLeft2))
            && gameBoard.isEmpty(wallBelow1Left1) && gameBoard.isEmpty(wallBelow1Left2)) {
          canBeBlocked = 1;
        }

        //right border -1 check with 1 wall
        if (endSpaceParent.getX() == gameBoard.getGridSize() - 3 && gameBoard.hasWall(wallLeft)) {
          if ((gameBoard.isEmpty(wallBelow1Right1) && gameBoard.isEmpty(wallBelow1Right2))) {
            canBeBlocked = 1;
          }
        }
      } else if (endSpaceParent.toRight(2).equals(endSpace)) {

        //middle check
        if (gameBoard.isCoordinateValid(wallAbove) && gameBoard.isCoordinateValid(wallBelow)
            && gameBoard.hasWall(wallAbove) && gameBoard.hasWall(wallBelow)) {
          if ((gameBoard.isEmpty(wallAbove2Right1) && gameBoard.isEmpty(wallAbove1Right1))
              || (gameBoard.isEmpty(wallBelow2Right1) && gameBoard.isEmpty(wallBelow1Right1))) {
            canBeBlocked = 1;
          }
        }

        //above side spaced out check
        if (gameBoard.isCoordinateValid(wallAbove2) && gameBoard.isCoordinateValid(wallBelow)
            && gameBoard.hasWall(wallAbove2) && gameBoard.hasWall(wallBelow)) {
          if (gameBoard.isEmpty(wallAbove1Right1) && gameBoard.isEmpty(wallAbove2Right1)) {
            canBeBlocked = 1;
          }
        }

        //below side spaced out check
        if (gameBoard.isCoordinateValid(wallAbove) && gameBoard.isCoordinateValid(wallBelow2)
            && gameBoard.hasWall(wallAbove) && gameBoard.hasWall(wallBelow2)) {
          if (gameBoard.isEmpty(wallBelow2Right1) && gameBoard.isEmpty(wallBelow1Right1)) {
            canBeBlocked = 1;
          }
        }

        //above border check
        if (endSpaceParent.getY() == gameBoard.getGridSize() - 1
            && (gameBoard.hasWall(wallBelow) || gameBoard.hasWall(wallBelow2))
            && gameBoard.isEmpty(wallBelow1Right1) && gameBoard.isEmpty(wallBelow2Right1)) {
          canBeBlocked = 1;
        }

        //above border -1 check with 1 wall
        if (endSpaceParent.getY() == gameBoard.getGridSize() - 3 && gameBoard.hasWall(wallBelow)) {
          if ((gameBoard.isEmpty(wallAbove1Right1) && gameBoard.isEmpty(wallAbove2Right1))) {
            canBeBlocked = 1;
          }
        }

        //below border check
        if (endSpaceParent.getY() == 0
            && (gameBoard.hasWall(wallAbove) || gameBoard.hasWall(wallAbove2))
            && gameBoard.isEmpty(wallAbove1Right1) && gameBoard.isEmpty(wallAbove2Right1)) {
          canBeBlocked = 1;
        }

        //below border + 1 check with wall
        if (endSpaceParent.getY() == 2 && gameBoard.hasWall(wallAbove)) {
          if (gameBoard.isEmpty(wallBelow1Right1) && gameBoard.isEmpty(wallBelow2Right1)) {
            canBeBlocked = 1;
          }
        }
      } else if (endSpaceParent.toLeft(2).equals(endSpace)) {

        //middle check
        if (gameBoard.isCoordinateValid(wallAbove) && gameBoard.isCoordinateValid(wallBelow)
            && gameBoard.hasWall(wallAbove) && gameBoard.hasWall(wallBelow)) {
          if ((gameBoard.isEmpty(wallAbove2Left1) && gameBoard.isEmpty(wallAbove1Left1))
              || (gameBoard.isEmpty(wallBelow2Left1) && gameBoard.isEmpty(wallBelow1Left1))) {
            canBeBlocked = 1;
          }
        }

        //above side spaced out check
        if (gameBoard.isCoordinateValid(wallAbove2) && gameBoard.isCoordinateValid(wallBelow)
            && gameBoard.hasWall(wallAbove2) && gameBoard.hasWall(wallBelow)) {
          if (gameBoard.isEmpty(wallAbove1Left1) && gameBoard.isEmpty(wallAbove2Left1)) {
            canBeBlocked = 1;
          }
        }

        //below side spaced out check
        if (gameBoard.isCoordinateValid(wallAbove) && gameBoard.isCoordinateValid(wallBelow2)
            && gameBoard.hasWall(wallAbove) && gameBoard.hasWall(wallBelow2)) {
          if (gameBoard.isEmpty(wallBelow2Left1) && gameBoard.isEmpty(wallBelow1Left1)) {
            canBeBlocked = 1;
          }
        }

        //above border check
        if (endSpaceParent.getY() == gameBoard.getGridSize() - 1
            && (gameBoard.hasWall(wallBelow) || gameBoard.hasWall(wallBelow2))
            && gameBoard.isEmpty(wallBelow1Left1) && gameBoard.isEmpty(wallBelow2Left1)) {
          canBeBlocked = 1;
        }

        //above border -1 check with 1 wall
        if (endSpaceParent.getY() == gameBoard.getGridSize() - 3 && gameBoard.hasWall(wallBelow)) {
          if ((gameBoard.isEmpty(wallAbove1Left1) && gameBoard.isEmpty(wallAbove2Left1))) {
            canBeBlocked = 1;
          }
        }

        //below border check
        if (endSpaceParent.getY() == 0
            && (gameBoard.hasWall(wallAbove) || gameBoard.hasWall(wallAbove2))
            && gameBoard.isEmpty(wallAbove1Left1) && gameBoard.isEmpty(wallAbove2Left1)) {
          canBeBlocked = 1;
        }

        //below border + 1 check with wall
        if (endSpaceParent.getY() == 2 && gameBoard.hasWall(wallAbove)) {
          if (gameBoard.isEmpty(wallBelow1Left1) && gameBoard.isEmpty(wallBelow2Left1)) {
            canBeBlocked = 1;
          }
        }
      }
      shortestPath = shortestPath.getParent();
      endSpace = shortestPath.getLocation();
      endSpaceParentNode = shortestPath.getParent();

      if (endSpaceParentNode != null) {
        endSpaceParent = endSpaceParentNode.getLocation();
      }
    }

    return canBeBlocked;
  }
}
