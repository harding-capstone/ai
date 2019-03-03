package com.shepherdjerred.capstone.ai;

import com.google.common.collect.ImmutableSet;
import com.shepherdjerred.capstone.ai.alphabeta.AlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.PruningAlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.DeepWallPruningRule;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.PieceDistancePruningRule;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.RandomDiscardPruningRule;
import com.shepherdjerred.capstone.ai.evaluator.EvaluatorWeights;
import com.shepherdjerred.capstone.ai.evaluator.WeightedMatchEvaluator;
import com.shepherdjerred.capstone.ai.evaluator.RandomlyMultipliedMatchEvaluator;
import com.shepherdjerred.capstone.ai.montecarlo.MonteCarloQuoridorAi;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.junit.Ignore;
import org.junit.Test;

@Log4j2
public class AiTest {

  @Ignore
  @Test
  public void monteCarloVersusAlphaBeta() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var weights = new EvaluatorWeights(
        1,
        1,
        1,
        1,
        1);

    var monte = new MonteCarloQuoridorAi(1, new WeightedMatchEvaluator(weights), 6000);
    var alpha = new AlphaBetaQuoridorAi(new WeightedMatchEvaluator(weights), 3);

    simulateAi(match, monte, alpha);
  }

  @Ignore
  @Test
  public void monteCarloVersusMonteCarlo() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var weights = new EvaluatorWeights(
        1,
        1,
        1,
        1,
        1);

    var monte = new MonteCarloQuoridorAi(2, new WeightedMatchEvaluator(weights), 3000);

    simulateAi(match, monte, monte);
  }

  @Ignore
  @Test
  public void alphaBetaVersusAlphaBeta() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var weights = new EvaluatorWeights(
        1,
        1,
        1,
        1,
        1);

    var alphaBetaAi = new AlphaBetaQuoridorAi(new WeightedMatchEvaluator(weights), 2);

    simulateAi(match, alphaBetaAi, alphaBetaAi);
  }

  @Ignore
  @Test
  public void alphaBetaDefaultVsRandomlyMultipliedDefault() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var weights = new EvaluatorWeights(
        1,
        1,
        1,
        2,
        1);

    var alphaBetaAi = new AlphaBetaQuoridorAi(new WeightedMatchEvaluator(weights), 2);
    var randomAi = new AlphaBetaQuoridorAi(new RandomlyMultipliedMatchEvaluator(.5,
        -.3,
        new WeightedMatchEvaluator(weights)), 2);

    simulateAi(match, alphaBetaAi, randomAi);
  }

  @Ignore
  @Test
  public void pruningAlphaBetaDefaultVsRandomlyMultipliedDefault() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var pruningRules = ImmutableSet.of(new RandomDiscardPruningRule(100),
        new DeepWallPruningRule(100),
        new PieceDistancePruningRule(3));

    var weights = new EvaluatorWeights(
        1,
        0,
        20,
        12,
        1);

    var regularEval = new WeightedMatchEvaluator(weights);
    var randomEval = new RandomlyMultipliedMatchEvaluator(1.2, .7, regularEval);

    var alphaBetaAi = new PruningAlphaBetaQuoridorAi(regularEval, 2, pruningRules);
    var randomAi = new PruningAlphaBetaQuoridorAi(randomEval, 2, pruningRules);

    while (true) {
      simulateAi(match, alphaBetaAi, randomAi);
    }
  }

  private void simulateAi(Match match, QuoridorAi playerOne, QuoridorAi playerTwo) {
    var matchFormatter = new MatchFormatter();
    matchFormatter.matchToString(match);

    int currentTurn = 1;
    while (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {

      System.out.println("=== Turn: " + currentTurn);
      System.out.println("=== Player: " + match.getActivePlayerId());

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
      System.out.println(matchFormatter.matchToString(match));
      log.info("Turn taken: " + aiTurn);
      log.info("Time taken: " + elapsed.toMillis() / 1000.0 + " seconds");
      System.out.println("\n\n");

      currentTurn++;
    }

    log.info("WINNER: " + match.getMatchStatus());
  }
}
