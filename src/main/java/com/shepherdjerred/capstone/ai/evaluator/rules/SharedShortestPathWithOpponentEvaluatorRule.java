package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;

public class SharedShortestPathWithOpponentEvaluatorRule implements EvaluatorRule {
  /*
  If the opponent(s) is located on the activePlayer's shortest path, and the opponent is closer to
  their goal than the activePlayer is to theirs, that's bad. They are potentially bottlenecked on
  the same path, and so activePlayer will lose (potentially).
  But, if the activePlayer is closer to their goal than the opponent(s) is, that's good.
   */

  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    double pathScore;

    //call A* to find shortest path, put each move in a list
    //If any space has a piece, we know our opponent is on the path.
    //check to see who's closer to their goal (new function?)



    /*
    if (any other piece is on PTO's path) {
      if (playerToOptimize is closer) {
        return 100;
      } else {
        return 0;
      }
    }
    */

    pathScore = 50;
    return pathScore;
  }

}
