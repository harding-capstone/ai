package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class OpponentsShortestPathEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  @Override
  public double evaluate(Match match) {
    var playerCount = match.getMatchSettings().getPlayerCount();
    Set<PlayerId> players = new HashSet<>();

    if (playerCount == PlayerCount.TWO) {
      players.add(PlayerId.ONE);
      players.add(PlayerId.TWO);
    } else if (playerCount == PlayerCount.FOUR) {
      players.add(PlayerId.ONE);
      players.add(PlayerId.TWO);
      players.add(PlayerId.THREE);
      players.add(PlayerId.FOUR);
    } else {
      throw new UnsupportedOperationException();
    }

    players.remove(match.getActivePlayerId());

    var sumOfDistances = players.stream()
        .map(player -> {
          var gridSize = match.getBoard().getBoardSettings().getGridSize();
          var goals = playerGoals.getGoalCoordinatesForPlayer(player,
              gridSize);
          return boardSearch.getShortestPathToAnyDestination(match.getBoard(),
              match.getBoard().getPawnLocation(player),
              goals);
        })
        .mapToInt(Integer::intValue)
        .sum();

    return Math.pow(sumOfDistances, 2);
  }
}
