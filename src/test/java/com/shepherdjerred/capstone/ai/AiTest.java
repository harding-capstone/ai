package com.shepherdjerred.capstone.ai;

import com.google.common.collect.ImmutableSet;
import com.shepherdjerred.capstone.ai.alphabeta.AlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.PruningAlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.DeepWallPruningRule;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.PieceDistancePruningRule;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.RandomDiscardPruningRule;
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
import java.time.Duration;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.junit.Ignore;
import org.junit.Test;

@Log4j2
public class AiTest {

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
        new DeepWallPruningRule(5),
        new PieceDistancePruningRule(3));

    var weights = new EvaluatorWeights(
        1,
        0,
        10,
        12,
        1);

    var regularEval = new WeightedMatchEvaluator(weights);
    var randomEval = new RandomlyMultipliedMatchEvaluator(1.1, .9, regularEval);

    var alphaBetaAi = new PruningAlphaBetaQuoridorAi(regularEval, 3, pruningRules);
    var randomAi = new PruningAlphaBetaQuoridorAi(randomEval, 3, pruningRules);

//    while (true) {
      simulateAi(match, alphaBetaAi, randomAi);
//    }
  }

  private void simulateAi(Match match, QuoridorAi playerOne, QuoridorAi playerTwo) {
    var matchFormatter = new MatchFormatter();
    matchFormatter.matchToString(match);

    int currentTurn = 1;
    while (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {

      log.info("=== Turn: " + currentTurn);
      log.info("=== Player: " + match.getActivePlayerId());

      var player = match.getActivePlayerId();

      var startTime = Instant.now();
      Turn aiTurn;
      if (match.getActivePlayerId() == PlayerId.ONE) {
        aiTurn = playerOne.calculateBestTurn(match);
      } else {
        aiTurn = playerTwo.calculateBestTurn(match);
      }
      var endTime = Instant.now();
      var elapsed = Duration.between(startTime, endTime);

      log.info(new WeightedMatchEvaluator(new EvaluatorWeights(1,
          1,
          10,
          12,
          1)).evaluateMatch(match, player));

//      log.info(currentMatchState.getMatchStatus().getStatus());
      match = match.doTurn(aiTurn);
//      log.info(aiTurn);
      log.info(matchFormatter.matchToString(match));
      log.info("Turn taken: " + aiTurn);
      log.info("Time taken: " + elapsed.toMillis() / 1000.0 + " seconds");

      log.info("\n\n");

      currentTurn++;
    }

    log.info("WINNER: " + match.getMatchStatus());
  }
}
