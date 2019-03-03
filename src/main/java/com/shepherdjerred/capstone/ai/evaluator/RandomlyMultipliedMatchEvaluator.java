package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RandomlyMultipliedMatchEvaluator implements MatchEvaluator {

  private final double multiplierMaximum;
  private final double multiplierMinimum;
  private final MatchEvaluator matchEvaluator;

  private final Random random = new Random();

  @Override
  public double evaluateMatch(Match match, PlayerId playerId) {
    var score = matchEvaluator.evaluateMatch(match, playerId);
    var multiplier = getRandomMultiplier();
    return score * multiplier;
  }

  private double getRandomMultiplier() {
    return multiplierMinimum + (multiplierMaximum - multiplierMinimum) * random.nextDouble();
  }
}
