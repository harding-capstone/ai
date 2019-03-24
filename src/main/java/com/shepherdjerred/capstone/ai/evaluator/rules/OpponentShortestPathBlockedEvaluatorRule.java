package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;

public class OpponentShortestPathBlockedEvaluatorRule {
  /*
  If our opponent(s)'s shortest path can be blocked with a single wall so that it no longer reaches
  the goal state, good.
   */

  public double blockOpponentPath (Match match, QuoridorPlayer playerToOptimize) {
    double blockScore = 0;
  /*
  get opponent's shortest path,
  if any a pawn space on the path can be cut off from another by placing a wall so it's adjacent
  to another or a border
    blockScore = 100
   */


  /*Not sure how to implement above, so for now, if a space is behind playerToOptimize where it can
  join two walls, we'll say it's...good? May want it to be bad because we want that space filled,
  will have to see how it plays.
   */

  QuoridorPlayer opponent = match.getNextActivePlayerId();
  PlayerGoals playerGoals = new PlayerGoals();
  QuoridorBoard gameBoard = match.getBoard();
  BoardSearch gameBoardSearch = new AStarBoardSearch();
  Coordinate playerToOptimizeLocation = gameBoard.getPawnLocation(playerToOptimize);
  Coordinate behindLeft1 = playerToOptimizeLocation.toLeft(2).below();
  Coordinate behindLeft2 = playerToOptimizeLocation.toLeft();

  int startRow;
  if (playerToOptimize == QuoridorPlayer.ONE) {
    startRow = 0;

    for ( int row = startRow + 1; row < playerToOptimizeLocation.getY(); row+=2) {

      for (int col = 0; col < gameBoard.getGridSize() - 10; col+=2) {
        //gridSize - 10 is so that I don't go out of bounds

        Coordinate wallSlot1 = new Coordinate(col, row);
        Coordinate wallSlot2 = wallSlot1.toRight(2);
        Coordinate wallSpace1 = wallSlot2.toRight(2);
        Coordinate wallSpace2 = wallSpace1.toRight(2);
        Coordinate secondWallSlot1 = wallSpace2.toRight(2);
        Coordinate secondWallSlot2 = secondWallSlot1.toRight(2);

        if(gameBoard.hasWall(wallSlot1) && gameBoard.hasWall(wallSlot2)
            && gameBoard.isEmpty(wallSpace1) && gameBoard.isEmpty(wallSpace2)
            && gameBoard.hasWall(secondWallSlot1) && gameBoard.hasWall(secondWallSlot2)) {
          blockScore++;
        }
      }
    }


  } else {
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




    return blockScore;
  }

}
