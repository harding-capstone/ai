package com.shepherdjerred.capstone.ai.evaluator.rules;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallLocation;
import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import org.junit.Test;

public class OpponentShortestPathBlockedTest {

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsTopMid() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate topRight = new Coordinate(16, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topMid.below(2);

    WallLocation leftWall = new WallLocation(playerLocation.toLeft().below(2),
        playerLocation.toLeft().below(), playerLocation.toLeft());
    WallLocation rightWall = new WallLocation(playerLocation.toRight().below(2),
        playerLocation.toRight().below(), playerLocation.toRight());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, topRight));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, leftWall));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, rightWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);

    //System.out.println(actual);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsTopLeftForP1() {
    Coordinate topLeft = new Coordinate(0, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topLeft.below(2);

    WallLocation rightWall = new WallLocation(playerLocation.toRight().below(2),
        playerLocation.toRight().below(), playerLocation.toRight());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, rightWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsTopLeftPlus1ForP1() {
    Coordinate topLeft = new Coordinate(0, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topLeft.below(2).toRight(2);

    WallLocation rightWall = new WallLocation(playerLocation.toRight().below(2),
        playerLocation.toRight().below(), playerLocation.toRight());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, rightWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsTopRightForP1() {
    Coordinate topRight = new Coordinate(16, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topRight.below(2);

    WallLocation leftWall = new WallLocation(playerLocation.toLeft().below(2),
        playerLocation.toLeft().below(), playerLocation.toLeft());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, leftWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsTopRightMinus1ForP1() {
    Coordinate topRight = new Coordinate(16, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topRight.below(2).toLeft(2);

    WallLocation leftWall = new WallLocation(playerLocation.toLeft().below(2),
        playerLocation.toLeft().below(), playerLocation.toLeft());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, leftWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.TWO);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftBottomForP2() {
    Coordinate leftBottom = new Coordinate(0, 0);
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = leftBottom.above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.toRight(),
        playerLocation.toRight().above(), playerLocation.toRight().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftBottomAndWallIsSpacedOutForP2() {
    Coordinate leftBottom = new Coordinate(0, 0);
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = leftBottom.above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.toRight(3),
        playerLocation.toRight(3).above(), playerLocation.toRight(3).above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftBottomPlus1ForP2() {
    Coordinate leftBottom = new Coordinate(0, 0);
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = leftBottom.toRight(2).above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.toRight(),
        playerLocation.toRight().above(), playerLocation.toRight().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightBottomForP2() {
    Coordinate rightBottom = new Coordinate(16, 0);
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = rightBottom.above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().above(), playerLocation.toLeft().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightBottomAndWallIsSpacedOutForP2() {
    Coordinate rightBottom = new Coordinate(16, 0);
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = rightBottom.above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.toLeft(3),
        playerLocation.toLeft(3).above(), playerLocation.toLeft(3).above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightBottomPlus1ForP2() {
    Coordinate rightBottom = new Coordinate(16, 0);
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = rightBottom.toLeft(2).above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().above(), playerLocation.toLeft().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsBottomMid() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate topRight = new Coordinate(16, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = bottomMid.above(2);

    WallLocation leftWall = new WallLocation(playerLocation.toLeft().above(2),
        playerLocation.toLeft().above(), playerLocation.toLeft());
    WallLocation rightWall = new WallLocation(playerLocation.toRight().above(2),
        playerLocation.toRight().above(), playerLocation.toRight());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, leftWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, rightWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  //4P tests

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightMid() {
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = rightMid.toLeft(2);

    WallLocation belowWall = new WallLocation(playerLocation.below().toLeft(2),
        playerLocation.below().toLeft(), playerLocation.below());
    WallLocation aboveWall = new WallLocation(playerLocation.above().toLeft(2),
        playerLocation.above().toLeft(), playerLocation.above());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, belowWall));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightTopForP3() {
    Coordinate rightTop = new Coordinate(16, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = rightTop.toLeft(2);

    WallLocation belowWall = new WallLocation(playerLocation.below().toLeft(2),
        playerLocation.below().toLeft(), playerLocation.below());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, belowWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightTopMinus1ForP3() {
    Coordinate rightTop = new Coordinate(16, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = rightTop.toLeft(2).below(2);

    WallLocation belowWall = new WallLocation(playerLocation.below().toLeft(2),
        playerLocation.below().toLeft(), playerLocation.below());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, belowWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightBottomForP3() {
    Coordinate rightBottom = new Coordinate(16, 0);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = rightBottom.toLeft(2);

    WallLocation aboveWall = new WallLocation(playerLocation.above().toLeft(2),
        playerLocation.above().toLeft(), playerLocation.above());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsRightBottomPlus1ForP3() {
    Coordinate rightBottom = new Coordinate(16, 0);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = rightBottom.toLeft(2).above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.above().toLeft(2),
        playerLocation.above().toLeft(), playerLocation.above());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftMid() {
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = leftMid.toRight(2);

    WallLocation belowWall = new WallLocation(playerLocation.below().toRight(2),
        playerLocation.below().toRight(), playerLocation.below());
    WallLocation aboveWall = new WallLocation(playerLocation.above().toRight(2),
        playerLocation.above().toRight(), playerLocation.above());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, belowWall));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftTopForP4() {
    Coordinate leftTop = new Coordinate(0, 16);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate playerLocation = leftTop.toRight(2);

    WallLocation belowWall = new WallLocation(playerLocation.below().toRight(2),
        playerLocation.below().toRight(), playerLocation.below());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, belowWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftTopMinus1ForP4() {
    Coordinate leftTop = new Coordinate(0, 16);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate playerLocation = leftTop.toRight(2).below(2);

    WallLocation belowWall = new WallLocation(playerLocation.below().toRight(2),
        playerLocation.below().toRight(), playerLocation.below());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, belowWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftBottomForP4() {
    Coordinate leftBottom = new Coordinate(0, 0);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate playerLocation = leftBottom.toRight(2);

    WallLocation aboveWall = new WallLocation(playerLocation.above().toRight(2),
        playerLocation.above().toRight(), playerLocation.above());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenGoalIsLeftBottomPlus1ForP4() {
    Coordinate leftBottom = new Coordinate(0, 0);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate playerLocation = leftBottom.toRight(2).above(2);

    WallLocation aboveWall = new WallLocation(playerLocation.above().toRight(2),
        playerLocation.above().toRight(), playerLocation.above());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, aboveWall));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentShortestPathBlocked_whenNoWallsArePlaced() {

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new OpponentShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }
}
