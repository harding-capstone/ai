package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import java.util.HashSet;
import java.util.Set;
import lombok.ToString;

// TODO add configurable radius
@ToString
public class WallsNearbyEnemyEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    if (match.getActivePlayerId().equals(playerToOptimize)) {
      return 0;
    }

    PlayerCount numberOfPlayers = match.getMatchSettings().getPlayerCount();
    Set<QuoridorPlayer> otherPlayers = new HashSet<>();

    if (numberOfPlayers == PlayerCount.TWO) {
      otherPlayers.add(match.getNextActivePlayerId());
    } else {
      otherPlayers.add(QuoridorPlayer.ONE);
      otherPlayers.add(QuoridorPlayer.TWO);
      otherPlayers.add(QuoridorPlayer.THREE);
      otherPlayers.add(QuoridorPlayer.FOUR);
      otherPlayers.remove(match.getActivePlayerId());
    }

    var board = match.getBoard();
    return otherPlayers.stream()
        .map(player -> board.getPawnLocation(player))
        .map(pawnLocation -> {
          var adjacentWallLocations = board.getWallCellsAdjacentToPawnSpace(pawnLocation);
          return adjacentWallLocations.stream().filter(board::hasPiece).count();
        })
        .mapToInt(Number::intValue)
        .sum();
  }
}
