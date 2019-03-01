package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.Random;

public class RandomMatchEvaluator implements MatchEvaluator {

  private static final double MAX_DOUBLE = Double.MAX_VALUE;
  private static final double MIN_DOUBLE = Double.MIN_NORMAL;
  private final Random random = new Random();

  @Override
  public double evaluateMatch(Match match, PlayerId playerId) {
    double max = 10000;
    double min = -10000;
    var randomOffset = min + (max - min) * random.nextDouble();
    return randomOffset
        + getScoreForDefeat(match, playerId)
        + getScoreForVictory(match, playerId);
  }

  private double getScoreForDefeat(Match match, PlayerId playerId) {
    if (match.getMatchStatus().getStatus() == Status.VICTORY
        && match.getMatchStatus().getVictor() != playerId) {
      return MIN_DOUBLE;
    } else {
      return 0;
    }
  }

  private double getScoreForVictory(Match match, PlayerId playerId) {
    if (match.getMatchStatus().getVictor() == playerId) {
      return MAX_DOUBLE;
    }
    else {
      return 0;
    }
  }
}
