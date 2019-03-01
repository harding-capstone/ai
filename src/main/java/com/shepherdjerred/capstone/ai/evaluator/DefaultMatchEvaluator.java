package com.shepherdjerred.capstone.ai.evaluator;

import com.shepherdjerred.capstone.ai.evaluator.rule.DefeatEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.EvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.OpponentBlindDistanceEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.OpponentJumpPotentialEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.OptimizingPlayerActualDistanceEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.OptimizingPlayerJumpPotentialEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.RemainingWallsEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.VictoryEvaluatorRule;
import com.shepherdjerred.capstone.ai.evaluator.rule.WallsNearbyEnemyEvaluatorRule;
import com.shepherdjerred.capstone.ai.search.astar.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultMatchEvaluator implements MatchEvaluator {


  @Override
  public double evaluateMatch(Match match, PlayerId playerToOptimize) {
    Map<EvaluatorRule, Double> evaluators = new HashMap<>();
//    rule.put(new OptimizingPlayerBlindDistanceEvaluatorRule(), 1.0);
    evaluators.put(new OptimizingPlayerActualDistanceEvaluatorRule(new AStarBoardSearch()), 3.0);
    evaluators.put(new DefeatEvaluatorRule(), 1.0);
    evaluators.put(new OptimizingPlayerActualDistanceEvaluatorRule(new AStarBoardSearch()), 1.0);
    evaluators.put(new OpponentJumpPotentialEvaluatorRule(), 1.0);
    evaluators.put(new OpponentBlindDistanceEvaluatorRule(), 1.0);
    evaluators.put(new RemainingWallsEvaluatorRule(), 1.0);
    evaluators.put(new VictoryEvaluatorRule(), 1.0);
    evaluators.put(new WallsNearbyEnemyEvaluatorRule(), 1.0);
    evaluators.put(new OptimizingPlayerJumpPotentialEvaluatorRule(), 1.0);

    var matchScore = evaluators.entrySet().stream()
        .map(entry -> {
          var evaluator = entry.getKey();
          var weight = entry.getValue();
          var rawScore = evaluator.evaluate(match, playerToOptimize);
//          log.info(evaluator + ": " + rawScore);
          return rawScore * weight;
        })
        .mapToDouble(Double::doubleValue)
        .sum();

//    log.info("Match score: " + matchScore);
    return matchScore;
  }

}
