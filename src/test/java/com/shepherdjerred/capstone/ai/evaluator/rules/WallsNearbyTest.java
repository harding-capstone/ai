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

public class WallsNearbyTest {
  @Test
  public void evaluate_WallsNearbyTest_whenEvaluatedPlayerIsNotActivePlayer() {

    Coordinate playerLocation = new Coordinate(8, 0);
    WallLocation wall = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEvaluationRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyTest_whenWallIsFarFromPlayers() {

    Coordinate wallLocation = new Coordinate(8, 8);
    WallLocation wall = new WallLocation(wallLocation.above(),
        wallLocation.above().toLeft(), wallLocation.above().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEvaluationRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyTest_whenP1Has1WallNearby() {

    Coordinate playerLocation = new Coordinate(8, 0);
    WallLocation wall = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wall));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEvaluationRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyTest_whenP1Has2WallsNearby() {

    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wallOne = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));
    WallLocation wallTwo = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wallOne));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallTwo));


    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEvaluationRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 2;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_WallsNearbyTest_whenP1Has3WallsNearby() {

    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = new Coordinate(8, 8);
    WallLocation wallOne = new WallLocation(playerLocation.toLeft(),
        playerLocation.toLeft().below(), playerLocation.toLeft().below(2));
    WallLocation wallTwo = new WallLocation(playerLocation.above(),
        playerLocation.above().toLeft(), playerLocation.above().toLeft(2));
    WallLocation wallThree = new WallLocation(playerLocation.toRight(),
        playerLocation.toRight().above(), playerLocation.toRight().above(2));

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wallOne));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, wallTwo));
    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, wallThree));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());
    System.out.println(match.getActivePlayerId());

    var rule = new WallsNearbyEvaluationRule();

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (long) 3;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }
}
