package com.shepherdjerred.capstone.ai.evaluator.rule;

import com.shepherdjerred.capstone.ai.evaluator.EvaluationUtils;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import java.util.Set;
import lombok.ToString;

@ToString
public class OpponentBlindDistanceEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    var playerCount = match.getMatchSettings().getBoardSettings().getPlayerCount();
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
    players.remove(playerToOptimize);
    var totalDistance = players.stream()
        .map(player -> EvaluationUtils.getBlindDistanceToGoal(match.getBoard(), player))
        .mapToInt(Integer::intValue)
        .sum();
    return totalDistance;
  }
}
