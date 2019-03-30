package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import com.shepherdjerred.capstone.logic.board.search.BoardAStarSearchNode;
import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpponentShortestPathBlockedEvaluatorRule implements EvaluatorRule {
  /*
  If our opponent(s)'s shortest path can be blocked with a single wall so that it no longer reaches
  the goal state, good.
   */

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    double canBeBlocked = 0;

    //QuoridorPlayer opponent = match.getNextActivePlayerId();
    QuoridorBoard gameBoard = match.getBoard();
    //BoardSearch gameBoardSearch = new AStarBoardSearch();
    //Coordinate playerToOptimizeLocation = gameBoard.getPawnLocation(playerToOptimize);
    //Coordinate behindLeft1 = playerToOptimizeLocation.toLeft(2).below();
    //Coordinate behindLeft2 = playerToOptimizeLocation.toLeft();

    PlayerCount numberOfPlayers = match.getMatchSettings().getPlayerCount();
    Set<QuoridorPlayer> otherPlayers = new HashSet<>();

    if (numberOfPlayers == PlayerCount.TWO) {
      otherPlayers.add(match.getNextActivePlayerId());
    } else {
      otherPlayers.add(QuoridorPlayer.ONE);
      otherPlayers.add(QuoridorPlayer.TWO);
      otherPlayers.add(QuoridorPlayer.THREE);
      otherPlayers.add(QuoridorPlayer.FOUR);
      otherPlayers.remove(playerToOptimize);
    }

    for (QuoridorPlayer player : otherPlayers) {

      var playerPawnLocation = gameBoard.getPawnLocation(player);
      var playerPawnGoals = playerGoals.getGoalCoordinatesForPlayer(player,
          gameBoard.getBoardSize());
      var shortestPath = (BoardAStarSearchNode) boardSearch.getPathToAnyDestination(gameBoard,
          playerPawnLocation, playerPawnGoals);
      //shortestPath = shortestPath.getParent();

      Coordinate endSpace = shortestPath.getLocation();
      var endSpaceParentNode = shortestPath.getParent();
      Coordinate endSpaceParent = endSpaceParentNode.getLocation();

      while (endSpaceParentNode != null) {
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
            && (gameBoard.isEmpty(wallBelowLeft) || gameBoard.isEmpty(wallBelowRight))) {
          canBeBlocked++;
        } else if (endSpaceParent.below(2).equals(endSpace)
            && (gameBoard.isEmpty(wallAboveLeft) || gameBoard.isEmpty(wallAboveRight))) {
          canBeBlocked++;
        } else if (endSpaceParent.toLeft(2).equals(endSpace)
            && (gameBoard.isEmpty(wallUpRight) || gameBoard.isEmpty(wallDownRight))) {
          canBeBlocked++;
        } else if (endSpaceParent.toRight(2).equals(endSpace)
            && (gameBoard.isEmpty(wallUpLeft) || gameBoard.isEmpty(wallDownLeft))) {
          canBeBlocked++;
        }

        shortestPath = shortestPath.getParent();
        endSpace = shortestPath.getLocation();
        endSpaceParentNode = shortestPath.getParent();
        endSpaceParent = endSpaceParentNode.getLocation();


      }


      /*
  get opponent's shortest path,
  if any a pawn space on the path can be cut off from another by placing a wall so it's adjacent
  to another or a border
   */


  /*Not sure how to implement above, so for now, if a space is behind playerToOptimize where it can
  join two walls, we'll say it's...good? May want it to be bad because we want that space filled,
  will have to see how it plays.
   */


    /*int startRow;

    if (playerToOptimize == QuoridorPlayer.ONE) {
      startRow = 0;

      for (int row = startRow + 1; row < playerToOptimizeLocation.getY(); row += 2) {

        for (int col = 0; col < gameBoard.getGridSize() - 10; col += 2) {
          //gridSize - 10 is so that I don't go out of bounds

          Coordinate wallSlot1 = new Coordinate(col, row);
          Coordinate wallSlot2 = wallSlot1.toRight(2);
          Coordinate wallSpace1 = wallSlot2.toRight(2);
          Coordinate wallSpace2 = wallSpace1.toRight(2);
          Coordinate secondWallSlot1 = wallSpace2.toRight(2);
          Coordinate secondWallSlot2 = secondWallSlot1.toRight(2);

          if (gameBoard.hasWall(wallSlot1) && gameBoard.hasWall(wallSlot2)
              && gameBoard.isEmpty(wallSpace1) && gameBoard.isEmpty(wallSpace2)
              && gameBoard.hasWall(secondWallSlot1) && gameBoard.hasWall(secondWallSlot2)) {
            blockScore++;
          }
        }
      }


    }
    else {
      startRow = gameBoard.getGridSize();

      for (int row = startRow - 1; row > playerToOptimizeLocation.getY(); row -= 2) {

        for (int col = 0; col < gameBoard.getGridSize() - 10; col += 2) {

          Coordinate wallSlot1 = new Coordinate(col, row);
          Coordinate wallSlot2 = wallSlot1.toRight(2);
          Coordinate wallSpace1 = wallSlot2.toRight(2);
          Coordinate wallSpace2 = wallSpace1.toRight(2);
          Coordinate secondWallSlot1 = wallSpace2.toRight(2);
          Coordinate secondWallSlot2 = secondWallSlot1.toRight(2);

          if (gameBoard.hasWall(wallSlot1) && gameBoard.hasWall(wallSlot2)
              && gameBoard.isEmpty(wallSpace1) && gameBoard.isEmpty(wallSpace2)
              && gameBoard.hasWall(secondWallSlot1) && gameBoard.hasWall(secondWallSlot2)) {
            blockScore++;
          }
        }
      }
    }
    */

    }
    return canBeBlocked;
  }

}


