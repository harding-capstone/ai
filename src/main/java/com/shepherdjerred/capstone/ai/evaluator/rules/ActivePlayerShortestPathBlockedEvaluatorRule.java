package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import java.util.Set;


public class ActivePlayerShortestPathBlockedEvaluatorRule {
  /*
  If the shortest path can be blocked off by a single wall placement (legally) so that the player
  can no longer reach the goal on that path, that's very bad and we'll have to backtrack. If that's
  the case, don't follow the shortest path, get the next-best path (probably with a loop so that
  while (possible block)
    { try the next path }

  Check the end of the path to see if a wall can be placed there to completely block it
   */

  public double evaluate (Match match, QuoridorPlayer playerToOptimize) {
    double score = 100;
    QuoridorBoard gameBoard = match.getBoard();
    BoardSearch gameBoardSearch = new AStarBoardSearch();
    Coordinate optimizingPlayerLocation = gameBoard.getPawnLocation(playerToOptimize);
    PlayerGoals goals = new PlayerGoals();

    Set<Coordinate> optimizingPlayerGoals =
        goals.getGoalCoordinatesForPlayer(playerToOptimize, gameBoard.getGridSize());
    int shortestPath = gameBoardSearch.getShortestPathToAnyDestination(gameBoard,
        optimizingPlayerLocation, optimizingPlayerGoals);

    return score;
  }

}
