package com.shepherdjerred.capstone.ai.search.astar;

import com.github.bentorfs.ai.search.asearch.ASearchAlgorithm;
import com.shepherdjerred.capstone.ai.evaluator.EvaluationUtils;
import com.shepherdjerred.capstone.ai.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AStarBoardSearch implements BoardSearch {

  private final ASearchAlgorithm algorithm = new ASearchAlgorithm();

  @Override
  public int findPath(Match match, PlayerId playerId) {
    var goals = EvaluationUtils.getGoals(match.getBoard().getBoardSettings().getGridSize(),
        playerId);

    var root = new BoardAStarSearchNode(match,
        playerId,
        match.getBoard().getPawnLocation(playerId),
        goals,
        0);
    var solution = algorithm.searchSolution(root);
    
    if (solution != null) {
      return solution.getCostSoFar();
    } else {
      throw new IllegalStateException("No path from pawn to goal");
    }
  }
}
