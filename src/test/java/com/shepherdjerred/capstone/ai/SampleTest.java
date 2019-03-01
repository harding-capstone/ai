package com.shepherdjerred.capstone.ai;

import com.shepherdjerred.capstone.ai.alphabeta.AlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.evaluator.DefaultMatchEvaluator;
import com.shepherdjerred.capstone.ai.evaluator.RandomMatchEvaluator;
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
public class SampleTest {

  @Ignore
  @Test
  public void monteCarloVersusAlphaBeta() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var monte = new MonteCarloQuoridorAi(1, new DefaultMatchEvaluator(), 6000);
    var alpha = new AlphaBetaQuoridorAi(new DefaultMatchEvaluator(), 3);

    simulateAi(match, monte, alpha);
  }

  @Ignore
  @Test
  public void monteCarloVersusMonteCarlo() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var monte = new MonteCarloQuoridorAi(1, new DefaultMatchEvaluator(), 3000);

    simulateAi(match, monte, monte);
  }

  @Test
  public void alphaBetaVersusAlphaBeta() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var alphaBetaAi = new AlphaBetaQuoridorAi(new DefaultMatchEvaluator(), 2);

    simulateAi(match, alphaBetaAi, alphaBetaAi);
  }

  @Ignore
  @Test
  public void alphaBetaVersusRandom() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    var alphaBetaAi = new AlphaBetaQuoridorAi(new DefaultMatchEvaluator(), 2);
    var randomAi = new AlphaBetaQuoridorAi(new RandomMatchEvaluator(), 2);

    simulateAi(match, alphaBetaAi, randomAi);
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
      log.info("Time taken: " + elapsed.toMillis() / 1000.0 + " secondsr");
      System.out.println("\n\n");

      currentTurn++;
    }
  }
}
