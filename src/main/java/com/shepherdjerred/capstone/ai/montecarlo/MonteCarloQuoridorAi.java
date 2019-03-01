package com.shepherdjerred.capstone.ai.montecarlo;

import com.shepherdjerred.capstone.ai.QuoridorAi;
import com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.ai.montecarlo.algorithm.NestedMonteCarloSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonteCarloQuoridorAi implements QuoridorAi {

  private final int depth;
  private final MatchEvaluator matchEvaluator;
  private final long runningTimeInMilliseconds;

  @Override
  public Turn calculateBestTurn(Match match) {
    var player = match.getActivePlayerId();
    var initialState = new QuoridorNestedMonteCarloSearchState(match, player, null, matchEvaluator);

    var endTime = System.currentTimeMillis() + runningTimeInMilliseconds;

    var result = NestedMonteCarloSearch.executeSearch(initialState,
        depth,
        () -> System.currentTimeMillis() > endTime);

    // TODO?
    return result.item2.get(0);
  }
}
