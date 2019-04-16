package com.shepherdjerred.capstone.ai.evaluator.rules;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import org.junit.Test;

public class ActivePlayerClosestToGoalRelativeTest {

  @Test
  public void evaluate_ActivePlayerClosestToGoalRelativeTest_whenNobodyIsCloser() {

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerClosestToGoalRelativeEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_ActivePlayerClosestToGoalTest_whenP2IsCloser() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = topMid.below(2);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerClosestToGoalRelativeEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_ActivePlayerClosestToGoalTest_whenP1IsCloser() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = topMid.below(2);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerClosestToGoalRelativeEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 3;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_ActivePlayerClosestToGoalTest_whenP1IsInThird() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topMid.below(2);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation.toRight(2)));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, bottomMid.above(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerClosestToGoalRelativeEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_ActivePlayerClosestToGoalTest_whenP1IsLast() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate playerLocation = topMid.below(2);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation.below(2)));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation.toRight(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerClosestToGoalRelativeEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }
}
