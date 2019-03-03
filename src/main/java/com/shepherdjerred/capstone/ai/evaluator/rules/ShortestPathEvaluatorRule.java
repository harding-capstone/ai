package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@AllArgsConstructor
public class ShortestPathEvaluatorRule implements EvaluatorRule {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  @Override
  public double evaluate(Match match) {
    var player = match.getActivePlayerId();
    var maxDistance = match.getBoard().getBoardSettings().getBoardSize() * 2;
    var goals = playerGoals.getGoalCoordinatesForPlayer(player,
        match.getBoard().getBoardSettings().getGridSize());
//    log.info(new MatchFormatter().matchToString(match));
    return maxDistance - boardSearch.getShortestPathToAnyDestination(match.getBoard(),
        match.getBoard().getPawnLocation(player),
        goals);
  }
}
