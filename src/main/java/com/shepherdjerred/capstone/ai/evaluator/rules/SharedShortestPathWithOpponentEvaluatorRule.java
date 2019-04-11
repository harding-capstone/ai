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
public class SharedShortestPathWithOpponentEvaluatorRule implements EvaluatorRule {
  /*
  If the opponent(s) is located on the activePlayer's shortest path, and the opponent is closer to
  their goal than the activePlayer is to theirs, that's bad. They are potentially bottlenecked on
  the same path, and so activePlayer will lose (potentially).
  But, if the activePlayer is closer to their goal than the opponent(s) is, that's good.
   */
  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    double pathScore = 0;

    //call A* to find shortest path, put each move in a list
    //If any space has a piece, we know our opponent is on the path.

    QuoridorBoard gameBoard = match.getBoard();
    Coordinate playerLocation = gameBoard.getPawnLocation(playerToOptimize);

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


    var playerPawnGoals = playerGoals.getGoalCoordinatesForPlayer(playerToOptimize,
        gameBoard.getBoardSize());
    var shortestPath = (BoardAStarSearchNode) boardSearch.getPathToAnyDestination(gameBoard,
        playerLocation, playerPawnGoals);

    Coordinate endSpace = shortestPath.getLocation();
    var endSpaceParentNode = shortestPath.getParent();

    otherPlayers.iterator();

    for (QuoridorPlayer player : otherPlayers) {

      while (pathScore == 0 && endSpaceParentNode != null) {
        if (endSpace.equals(gameBoard.getPawnLocation(player))) {
          pathScore++;
        }

        shortestPath = shortestPath.getParent();
        endSpace = shortestPath.getLocation();
        endSpaceParentNode = shortestPath.getParent();
      }

    }
    return pathScore;
  }

}
