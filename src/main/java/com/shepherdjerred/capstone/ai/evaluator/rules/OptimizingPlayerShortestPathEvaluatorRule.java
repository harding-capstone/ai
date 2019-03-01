package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class OptimizingPlayerShortestPathEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  @Override
  public double evaluate(Match match, PlayerId playerToOptimize) {
    var goals = playerGoals.getGoalCoordinatesForPlayer(playerToOptimize,
        match.getBoard().getBoardSettings().getGridSize());
    return boardSearch.getShortestPathToAnyDestination(match.getBoard(),
        match.getBoard().getPawnLocation(playerToOptimize),
        goals) * -1;
  }
}
