package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;

public class ShortenActivePlayerPathEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, QuoridorPlayer optimizingPlayer) {
    return 0;
  }
  /*
  If the activePlayer has more than one path and can force a short one by placing a wall, that's
  good. So check wall-ends and place a wall at one of them to shorten a path (have to check
  horizontal and vertical).
   */

}
