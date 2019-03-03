package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;

public interface MatchEvaluator {

  double MAX_SCORE = 1_000_000.;
  double MIN_SCORE = -1_000_000.;

  double evaluateMatch(Match match, PlayerId playerId);
}
