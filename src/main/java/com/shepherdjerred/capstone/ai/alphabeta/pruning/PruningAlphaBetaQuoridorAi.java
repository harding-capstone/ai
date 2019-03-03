package com.shepherdjerred.capstone.ai.alphabeta.pruning;

import com.github.bentorfs.ai.search.minimax.MiniMaxAlgorithm;
import com.shepherdjerred.capstone.ai.QuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.PruningRule;
import com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class PruningAlphaBetaQuoridorAi implements QuoridorAi {

  private final MatchEvaluator matchEvaluator;
  private final int maxDepth;
  private final Set<PruningRule> pruningRules;

  @Override
  public Turn calculateBestTurn(Match match) {
    var current = new PruningQuoridorNode(match.getActivePlayerId(),
        match,
        null,
        matchEvaluator,
        pruningRules,
        0);
    var bestNode = (PruningQuoridorNode) new MiniMaxAlgorithm(maxDepth).getBestMove(current);
    return bestNode.getTurn();
  }
}
