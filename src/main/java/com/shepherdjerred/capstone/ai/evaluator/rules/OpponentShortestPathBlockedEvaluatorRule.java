package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;

public class OpponentShortestPathBlockedEvaluatorRule {
  /*
  If our opponent(s)'s shortest path can be blocked with a single wall so that it no longer reaches
  the goal state, good.
   */

  public double blockOpponentPath (Match match, QuoridorPlayer playerToOptimize) {
    double blockScore = 0;
  /*
  get opponent's shortest path,
  if any a pawn space on the path can be cut off from another by placing a wall so it's adjacent
  to another or a border
    blockScore = 100
   */
    return blockScore;
  }

}
