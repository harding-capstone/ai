package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.ai.evaluator.rules.DefeatEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.EvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.OpponentsShortestPathEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.OpponentJumpPotentialEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.OptimizingPlayerShortestPathEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.OptimizingPlayerJumpPotentialEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.RemainingWallsEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.VictoryEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rules.WallsNearbyEnemyEvaluatorRule;
import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashMap;
import java.util.Map;

//@Log4j2
public class DefaultMatchEvaluator implements MatchEvaluator {


  @Override
  public double evaluateMatch(Match match, PlayerId playerToOptimize) {
    Map<EvaluatorRule, Double> evaluators = new HashMap<>();
    evaluators.put(new OptimizingPlayerShortestPathEvaluatorRule(new AStarBoardSearch(), new PlayerGoals()), 3.0);
    evaluators.put(new DefeatEvaluatorRule(), 1.0);
    evaluators.put(new OpponentJumpPotentialEvaluatorRule(), 1.0);
    evaluators.put(new OpponentsShortestPathEvaluatorRule(new AStarBoardSearch(), new PlayerGoals()), 1.0);
    evaluators.put(new RemainingWallsEvaluatorRule(), 1.0);
    evaluators.put(new VictoryEvaluatorRule(), 1.0);
    evaluators.put(new WallsNearbyEnemyEvaluatorRule(), 1.0);
    evaluators.put(new OptimizingPlayerJumpPotentialEvaluatorRule(), 1.0);

    var matchScore = evaluators.entrySet().stream()
        .map(entry -> {
          var evaluator = entry.getKey();
          var weight = entry.getValue();
          var rawScore = evaluator.evaluate(match, playerToOptimize);
          log.debug(evaluator + ": " + rawScore);
          return rawScore * weight;
        })
        .mapToDouble(Double::doubleValue)
        .sum();

    log.debug("Match score: " + matchScore);
    return matchScore;
  }

}
