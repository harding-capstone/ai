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
public class ShortenActivePlayerPathEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  @Override
  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    double pathblocks = 0;

    QuoridorBoard gameBoard = match.getBoard();
    var playerPawnLocation = gameBoard.getPawnLocation(playerToOptimize);
    var playerPawnGoals = playerGoals.getGoalCoordinatesForPlayer(playerToOptimize,
        gameBoard.getBoardSize());
    var shortestPath = (BoardAStarSearchNode) boardSearch.getPathToAnyDestination(gameBoard,
        playerPawnLocation, playerPawnGoals);

    Coordinate endSpace = shortestPath.getLocation();
    var endSpaceParentNode = shortestPath.getParent();
    Coordinate endSpaceParent = endSpaceParentNode.getLocation();

    while (endSpaceParentNode != null) {

      Coordinate wallAbove = endSpace.above();
      Coordinate wallAbove1Left2 = wallAbove.toLeft(2);
      Coordinate wallAbove1Right2 = wallAbove.toRight(2);
      Coordinate wallBelow = endSpace.below();
      Coordinate wallBelow1Left2 = wallBelow.toLeft(2);
      Coordinate wallBelow1Right2 = wallBelow.toRight(2);
      Coordinate wallAbove2Left1 = endSpace.toLeft().above(2);
      Coordinate wallAbove2Right1 = endSpace.toRight().above(2);
      Coordinate wallBelow2Left1 = endSpace.toLeft().below(2);
      Coordinate wallBelow2Right1 = endSpace.toRight().below(2);

      if (endSpace.getX() == 0 && endSpace.getY() == 0) {
        return 0;
      } else if (endSpace.getX() == gameBoard.getGridSize() && endSpace.getY() == 0) {
        return 0;
      } else if (endSpace.getX() == gameBoard.getGridSize() && endSpace.getY() == gameBoard.getGridSize()) {
        return 0;
      } else if (endSpace.getX() == 0 && endSpace.getY() == gameBoard.getGridSize()) {
        return 0;
      } else {

        if (endSpaceParent.above(2).equals(endSpace)) {

          if (gameBoard.isEmpty(endSpace.toLeft()) && gameBoard.isEmpty(wallBelow2Left1)) {
            pathblocks++;
          }

          if (gameBoard.isEmpty(endSpace.toRight()) && gameBoard.isEmpty(wallBelow2Right1)) {
            pathblocks++;
          }

        } else if (endSpaceParent.below(2).equals(endSpace)) {

          if (gameBoard.isEmpty(endSpace.toLeft()) && gameBoard.isEmpty(wallAbove2Left1)) {
            pathblocks++;
          }

          if (gameBoard.isEmpty(endSpace.toRight()) && gameBoard.isEmpty(wallAbove2Right1)) {
            pathblocks++;
          }
        } else if (endSpaceParent.toLeft(2).equals(endSpace)) {

          if (gameBoard.isEmpty(endSpace.below()) && gameBoard.isEmpty(wallBelow1Right2)) {
            pathblocks++;
          }

          if (gameBoard.isEmpty(endSpace.above()) && gameBoard.isEmpty(wallAbove1Right2)) {
            pathblocks++;
          }
        } else if (endSpaceParent.toRight(2).equals(endSpace)) {

          if (gameBoard.isEmpty(endSpace.below()) && gameBoard.isEmpty(wallBelow1Left2)) {
            pathblocks++;
          }

          if (gameBoard.isEmpty(endSpace.above()) && gameBoard.isEmpty(wallAbove1Left2)) {
            pathblocks++;
          }
        }
      }

      shortestPath = shortestPath.getParent();
      endSpace = shortestPath.getLocation();
      endSpaceParentNode = shortestPath.getParent();
      endSpaceParent = endSpaceParentNode.getLocation();
    }


    return pathblocks;
  }
  /*
  If the activePlayer has more than one path and can force a short one by placing a wall, that's
  good. So check wall-ends and place a wall at one of them to shorten a path (have to check
  horizontal and vertical).
   */



}
