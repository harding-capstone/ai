package com.shepherdjerred.capstone.ai.evaluator.rules;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import org.junit.Test;

public class RemainingWallsTest {

  @Test
  public void evaluate_RemainingWalls_whenPlayerHasFullWalls() {

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new RemainingWallsEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 10;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_RemainingWalls_whenPlayerHasNoWalls() {

    var match = Match.from(new MatchSettings(0, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new RemainingWallsEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }
}
