package com.shepherdjerred.capstone.ai.ab;

import com.github.bentorfs.ai.search.minimax.MiniMaxAlgorithm;
import com.shepherdjerred.capstone.ai.QuoridorAi;
import com.shepherdjerred.capstone.ai.ab.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlphaBetaQuoridorAi implements QuoridorAi {

  private final MatchEvaluator matchEvaluator;
  private final int depth;

  @Override
  public Turn calculateBestTurn(Match match) {
    var current = new QuoridorNode(match.getActivePlayerId(), match, null, matchEvaluator);
    var bestNode = (QuoridorNode) new MiniMaxAlgorithm(2).getBestMove(current);
    return bestNode.getTurn();
  }
}
