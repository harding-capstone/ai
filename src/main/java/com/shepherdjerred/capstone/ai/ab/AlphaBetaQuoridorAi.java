package com.shepherdjerred.capstone.ai.ab;

import com.shepherdjerred.capstone.ai.QuoridorAi;
import com.shepherdjerred.capstone.ai.ab.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlphaBetaQuoridorAi implements QuoridorAi {

  private final MatchEvaluator matchEvaluator;

  @Override
  public Turn calculateBestTurn(Match match) {
    var current = new QuoridorNode(match.getActivePlayerId(), match, null, matchEvaluator);
    var search = new AlphaBetaSearch(2).getBestNode(current);
    // terrible
    var node = (QuoridorNode) search.get();
    return node.getTurn();
  }
}
