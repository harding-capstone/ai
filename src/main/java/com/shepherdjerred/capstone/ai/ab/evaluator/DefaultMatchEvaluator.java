package com.shepherdjerred.capstone.ai.ab.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;

public class DefaultMatchEvaluator implements MatchEvaluator {

  private static final double MAX_DOUBLE = Double.MAX_VALUE;
  private static final double MIN_DOUBLE = Double.MIN_NORMAL;

  @Override
  public double evaluateMatch(Match match, PlayerId playerId) {
    return getScoreForVictory(match, playerId)
        + getScoreForDefeat(match, playerId)
        + getScoreForNearWalls(match, playerId)
        + getScoreForJumpPotential(match, playerId)
        + getScoreForDistanceToGoal(match, playerId);
  }

  private double getScoreForDefeat(Match match, PlayerId playerId) {
    if (match.getMatchStatus().getStatus() == Status.VICTORY && match.getMatchStatus().getVictor() != playerId) {
      return MIN_DOUBLE;
    } else {
      return 0;
    }
  }

  private double getScoreForVictory(Match match, PlayerId playerId) {
    if (match.getMatchStatus().getVictor() == playerId) {
      return MAX_DOUBLE;
    } else {
      return 0;
    }
  }

  private double getScoreForNearWalls(Match match, PlayerId playerId) {
    return 0;
  }

  private double getScoreForJumpPotential(Match match, PlayerId playerId) {
    return 0;
  }

  private double getScoreForDistanceToGoal(Match match, PlayerId playerId) {
    double maxPathDistance = 72;
    int distanceFactor = 10;
    var a = (maxPathDistance * distanceFactor - (getDistanceToGoal(match, playerId) * distanceFactor)) / 100;
    return Math.pow(a, 3);
  }

  private int getDistanceToGoal(Match match, PlayerId playerId) {
    // TODO shortest path
    return 0;
  }
}
