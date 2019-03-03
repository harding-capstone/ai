package com.shepherdjerred.capstone.ai.alphabeta.pruning;

import com.github.bentorfs.ai.search.minimax.MiniMaxAlgorithm;
import com.shepherdjerred.capstone.ai.QuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.QuoridorNode;
import com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PruningAlphaBetaQuoridorAi implements QuoridorAi {

  private final MatchEvaluator matchEvaluator;
  private final int depth;
  private final int pruningDistance;

  @Override
  public Turn calculateBestTurn(Match match) {
    var current = new DistancePruningQuoridorNode(match.getActivePlayerId(), match, null, matchEvaluator, pruningDistance);
    var bestNode = (QuoridorNode) new MiniMaxAlgorithm(depth).getBestMove(current);
    return bestNode.getTurn();
  }
}
