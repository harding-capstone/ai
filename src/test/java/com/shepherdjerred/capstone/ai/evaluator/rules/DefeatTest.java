package com.shepherdjerred.capstone.ai.evaluator.rules;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import org.junit.Test;

public class DefeatTest {

  //TODO Check the victory corners as well
  @Test
  public void evaluate_DefeatTest_WhenP2hasReachedTheGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, bottomMid.toRight(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new DefeatEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = -1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_DefeatTest_WhenP3hasReachedTheGoal() {
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate leftMid = new Coordinate(0, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, rightMid.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new DefeatEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = -1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_DefeatTest_WhenP4hasReachedTheGoal() {
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate leftMid = new Coordinate(0, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.FOUR, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, leftMid.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new DefeatEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = -1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_DefeatTest_WhenP1hasReachedTheGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, topMid.toRight(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new DefeatEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_DefeatTest_WhenNobodyHasReachedTheGoal() {

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new DefeatEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

}
