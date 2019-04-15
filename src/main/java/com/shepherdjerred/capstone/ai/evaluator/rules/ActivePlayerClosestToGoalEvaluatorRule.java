package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActivePlayerClosestToGoalEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  @Override
  public double evaluate(Match match, QuoridorPlayer player) {

    if (!match.getActivePlayerId().equals(player)) {
      return 0;
    }

    double playerHasShortestPath = 1;

    PlayerCount numberOfPlayers = match.getMatchSettings().getPlayerCount();
    Set<QuoridorPlayer> otherPlayers = new HashSet<>();

    if (numberOfPlayers == PlayerCount.TWO) {
      otherPlayers.add(match.getNextActivePlayerId());
    } else {
      otherPlayers.add(QuoridorPlayer.ONE);
      otherPlayers.add(QuoridorPlayer.TWO);
      otherPlayers.add(QuoridorPlayer.THREE);
      otherPlayers.add(QuoridorPlayer.FOUR);
      otherPlayers.remove(player);
    }

    var goals = playerGoals.getGoalCoordinatesForPlayer(player,
        match.getBoard().getGridSize());
    int distanceToGoal =  boardSearch.getShortestPathToAnyDestination(match.getBoard(),
        match.getBoard().getPawnLocation(player), goals);

    for (QuoridorPlayer opponent : otherPlayers) {

      var opponentGoals = playerGoals.getGoalCoordinatesForPlayer(opponent,
          match.getBoard().getGridSize());
      int opponentGoalDistance =  boardSearch.getShortestPathToAnyDestination(match.getBoard(),
          match.getBoard().getPawnLocation(opponent), opponentGoals);

      if (opponentGoalDistance <= distanceToGoal) {
        playerHasShortestPath = 0;
      }
    }

    return playerHasShortestPath;
  }

}
