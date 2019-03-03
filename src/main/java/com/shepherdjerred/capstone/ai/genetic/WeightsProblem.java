package com.shepherdjerred.capstone.ai.genetic;

import com.google.common.collect.ImmutableSet;
import com.shepherdjerred.capstone.ai.QuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.PruningAlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.DeepWallPruningRule;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.PieceDistancePruningRule;
import com.shepherdjerred.capstone.ai.evaluator.EvaluatorWeights;
import com.shepherdjerred.capstone.ai.evaluator.RandomlyMultipliedMatchEvaluator;
import com.shepherdjerred.capstone.ai.evaluator.WeightedMatchEvaluator;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import io.jenetics.DoubleChromosome;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.DoubleRange;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class WeightsProblem implements Problem<EvaluatorWeights, DoubleGene, Integer> {

  @Override
  public Function<EvaluatorWeights, Integer> fitness() {
    return weights -> {
      var boardSettings = new BoardSettings(9, PlayerCount.TWO);
      var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
      var match = Match.from(matchSettings, boardSettings);

      var alphaBetaAi = new PruningAlphaBetaQuoridorAi(new WeightedMatchEvaluator(weights), 2,
          ImmutableSet.of(new DeepWallPruningRule(2), new PieceDistancePruningRule(5)));
      var randomAi = new PruningAlphaBetaQuoridorAi(new RandomlyMultipliedMatchEvaluator(1.2, .7,
          new WeightedMatchEvaluator(weights)), 2, ImmutableSet.of(new DeepWallPruningRule(2), new PieceDistancePruningRule(5)));

      return simulateAi(match, alphaBetaAi, randomAi);
    };
  }

  @Override
  public Codec<EvaluatorWeights, DoubleGene> codec() {
    return Codec.of(
        Genotype.of(DoubleChromosome.of(DoubleRange.of(-10000, 10000), 5)),
        gt -> new EvaluatorWeights(gt.get(0, 0).doubleValue(),
            gt.get(0, 1).doubleValue(),
            gt.get(0, 2).doubleValue(),
            gt.get(0, 3).doubleValue(),
            gt.get(0, 4).doubleValue())
    );
  }

  private int simulateAi(Match match, QuoridorAi playerOne, QuoridorAi playerTwo) {
    var matchFormatter = new MatchFormatter();
    matchFormatter.matchToString(match);

    int currentTurn = 1;
    while (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {

//      System.out.println("=== Turn: " + currentTurn);
//      System.out.println("=== Player: " + match.getActivePlayerId());

      var startTime = Instant.now();
      Turn aiTurn;
      if (match.getActivePlayerId() == PlayerId.ONE) {
        aiTurn = playerOne.calculateBestTurn(match);
      } else {
        aiTurn = playerTwo.calculateBestTurn(match);
      }
      var endTime = Instant.now();
      var elapsed = Duration.between(startTime, endTime);

//      System.out.println(currentMatchState.getMatchStatus().getStatus());
      match = match.doTurn(aiTurn);
//      System.out.println(aiTurn);
//      System.out.println(matchFormatter.matchToString(match));
//      log.info("Turn taken: " + aiTurn);
//      log.info("Time taken: " + elapsed.toMillis() / 1000.0 + " seconds");
//      System.out.println("\n\n");

      currentTurn++;
    }

    if (match.getMatchStatus().getVictor() == PlayerId.ONE) {
      return currentTurn * -1;
    } else {
      return Integer.MIN_VALUE;
    }
  }
}
