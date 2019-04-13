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

public class VictoryTest {

  //TODO Check the victory corners as well
  @Test
  public void evaluate_VictoryTest_WhenP1HasReachedTheGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, topMid.below(2)));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, topMid));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP1HasReachedTheLeftCornerGoal() {
    Coordinate topLeftCorner = new Coordinate(0, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, topLeftCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP1HasReachedTheRightCornerGoal() {
    Coordinate topRightCorner = new Coordinate(16, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, topRightCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP2HasReachedTheGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, bottomMid.toLeft(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP2HasReachedTheLeftCornerGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomLeftCorner = new Coordinate(0, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, bottomLeftCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP2HasReachedTheRightCornerGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomRightCorner = new Coordinate(16, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, bottomRightCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP3HasReachedTheGoal() {
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, rightMid.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.THREE);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP3HasReachedTheTopCornerGoal() {
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightTopCorner = new Coordinate(16, 16);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, rightTopCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.THREE);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP3HasReachedTheBottomCornerGoal() {
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightBottomCorner = new Coordinate(16, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, rightBottomCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.THREE);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP4HasReachedTheGoal() {
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.FOUR, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, leftMid.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.FOUR);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP4HasReachedTheTopCornerGoal() {
    Coordinate leftTopCorner = new Coordinate(0, 16);
    Coordinate rightMid = new Coordinate(16, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.FOUR, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, leftTopCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.FOUR);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenP4HasReachedTheBottomCornerGoal() {
    Coordinate leftBottomCorner = new Coordinate(0, 0);
    Coordinate rightMid = new Coordinate(16, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.FOUR, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, leftBottomCorner));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.FOUR);
    var expected = 1_000_000.;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenNobodyHasReachedTheGoal() {

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_VictoryTest_WhenPlayerNotEvaluatedHasReachedTheGoal() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, topMid.below(2)));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, topMid));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    var rule = new VictoryEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

}
