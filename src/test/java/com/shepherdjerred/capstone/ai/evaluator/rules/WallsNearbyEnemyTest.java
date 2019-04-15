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

public class WallsNearbyEnemyTest {
  @Test
  public void evaluate_WallsNearbyEnemyTest_whenEvaluatedPlayerIsThePlayer() {

    Coordinate playerLocation = new Coordinate(8, 16);
    WallLocation wall = new WallLocation(playerLocation.below(),
        playerLocation.below().toLeft(), playerLocation.below().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyEnemyTest_whenWallIsFarFromEnemy() {

    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wall = new WallLocation(playerLocation.below(),
        playerLocation.below().toLeft(), playerLocation.below().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyEnemyTest_whenP2Has1WallNearby() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wall = new WallLocation(playerLocation.below(),
        playerLocation.below().toLeft(), playerLocation.below().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyEnemyTest_whenP2Has2WallsNearby() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wallOne = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));
    WallLocation wallTwo = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallOne));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallTwo));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyEnemyTest_whenP2Has3WallsNearby() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wallOne = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));
    WallLocation wallTwo = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));
    WallLocation wallThree = new WallLocation(playerLocation.toRight(),
        playerLocation.toRight().above(), playerLocation.toRight().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallOne));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wallTwo));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallThree));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 3;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyEnemyTest_whenP2Has3WallsNearbyAndP3Shares1WallWithP2() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wallOne = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));
    WallLocation wallTwo = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));
    WallLocation wallThree = new WallLocation(playerLocation.toRight(),
        playerLocation.toRight().above(), playerLocation.toRight().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, wallOne));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.FOUR, wallTwo));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallThree));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation.toRight(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 4;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyEnemyTest_whenP2Has3WallsNearbyAndP3Shares2WallsWithP2() {

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wallOne = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));
    WallLocation wallTwo = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));
    WallLocation wallThree = new WallLocation(playerLocation.toRight(),
        playerLocation.toRight().above(), playerLocation.toRight().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.THREE, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.THREE, wallOne));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.FOUR, wallTwo));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallThree));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, playerLocation));
    match = match
        .doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, playerLocation.toLeft(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEnemyEvaluatorRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 5;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

}
