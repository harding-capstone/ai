package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import java.util.Random;

public class RandomMatchEvaluator implements MatchEvaluator {

  private final Random random = new Random();

  @Override
  public double evaluateMatch(Match match) {
    double max = 10000;
    double min = -10000;
    var randomOffset = min + (max - min) * random.nextDouble();
    return randomOffset
        + getScoreForDefeat(match)
        + getScoreForVictory(match);
  }

  private double getScoreForDefeat(Match match) {
    if (match.getMatchStatus().getStatus() == Status.VICTORY
        && match.getMatchStatus().getVictor() != match.getActivePlayerId()) {
      return MIN_SCORE;
    } else {
      return 0;
    }
  }

  private double getScoreForVictory(Match match) {
    if (match.getMatchStatus().getVictor() == match.getActivePlayerId()) {
      return MAX_SCORE;
    } else {
      return 0;
    }
  }
}
