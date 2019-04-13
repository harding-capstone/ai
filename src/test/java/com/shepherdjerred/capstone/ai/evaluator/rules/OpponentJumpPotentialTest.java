package com.shepherdjerred.capstone.ai.evaluator.rules;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallLocation;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import org.junit.Test;

public class OpponentJumpPotentialTest {
  @Test
  public void evaluate_OpponentJumpPotentialTest_whenP1IsBelowP2() {

    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = new Coordinate(8, 16);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new OpponentJumpPotentialEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_AdjacentPawnsTest_whenP1IsSurrounded() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation leftWall = new WallLocation(topMid.toLeft(),
        topMid.toLeft().below(), topMid.toLeft().below(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation.above(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation.toLeft(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation.toRight(2)));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, leftWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new OpponentJumpPotentialEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 3;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_AdjacentPawnsTest_whenP1IsSurroundedAgainstSide() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate bottomMid = new Coordinate(8, 0);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, leftMid.above(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, leftMid.toRight(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, leftMid.below(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, leftMid));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new OpponentJumpPotentialEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 3;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  //TODO fix how it evaluates potential when a wall is in the way
  @Test
  public void evaluate_AdjacentPawnsTest_whenP1IsSurroundedWithWallBlocking() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation leftWall = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation.above(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation.toLeft(2)));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, playerLocation.toRight(2)));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, leftWall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new OpponentJumpPotentialEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_OpponentJumpPotentialTest_whenP1IsTheActivePlayer() {

    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = new Coordinate(8, 16);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new OpponentJumpPotentialEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

}
