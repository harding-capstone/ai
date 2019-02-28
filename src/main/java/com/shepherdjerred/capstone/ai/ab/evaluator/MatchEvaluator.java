package com.shepherdjerred.capstone.ai.ab.evaluator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;

public interface MatchEvaluator {
  double evaluateMatch(Match match, PlayerId playerId);
}
