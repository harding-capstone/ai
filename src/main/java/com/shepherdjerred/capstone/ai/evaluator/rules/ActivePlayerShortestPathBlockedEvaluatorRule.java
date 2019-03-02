package com.shepherdjerred.capstone.ai.evaluator.rules;

public class ActivePlayerShortestPathBlockedEvaluatorRule {
  /*
  If the shortest path can be blocked off by a single wall placement (legally) so that the player
  can no longer reach the goal on that path, that's very bad and we'll have to backtrack. If that's
  the case, don't follow the shortest path, get the next-best path (probably with a loop so that
  while (possible block)
    { try the next path }
   */


}
