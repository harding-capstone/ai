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

//import lombok.AllArgsConstructor;


public class ActivePlayerShortestPathBlockedTest {
  //private final BoardSearch boardSearch;
  //private final PlayerGoals playerGoals;

 /*
  private final Coordinate topMid = new Coordinate(8, 16);
  private final Coordinate topRight = new Coordinate(16, 16);
  private final Coordinate topLeft = new Coordinate(0, 16);
  private final Coordinate leftMid = new Coordinate(0, 8);
  private final Coordinate leftBottom = new Coordinate(0, 0);
  private final Coordinate rightMid = new Coordinate(16, 8);
  private final Coordinate rightBottom = new Coordinate(16, 0);
  private final Coordinate bottomMid = new Coordinate(8, 0);
  */



  @Test
  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsTopMid() {
    /*
    var middle = 8;
    Coordinate midWallLeftBegin = new Coordinate(7, 0);
    Coordinate midWallLeftVertex = new Coordinate(7, 1);
    Coordinate midWallLeftEnd = new Coordinate(7, 2);
    Coordinate midWallRightBegin = new Coordinate(9, 0);
    Coordinate midWallRightVertex = new Coordinate(9, 1);
    Coordinate midWallRightEnd = new Coordinate(9, 2);
    */

    Coordinate topMid = new Coordinate(8, 16);
    Coordinate topRight = new Coordinate(16, 16);
    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate playerLocation = topMid.below(2);

    WallLocation leftWall = new WallLocation(playerLocation.toLeft().below(2), playerLocation.toLeft().below(), playerLocation.toLeft());
    WallLocation rightWall = new WallLocation(playerLocation.toRight().below(2), playerLocation.toRight().below(), playerLocation.toRight());

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, playerLocation));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, topRight));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.ONE, leftWall));

    match = match.doTurnUnchecked(new PlaceWallTurn(QuoridorPlayer.TWO, rightWall));

    /*
    for (int i = 0; i < 4; i++) {

      QuoridorPlayer currentPlayer;

      if(i % 2 == 0) {
        currentPlayer = QuoridorPlayer.ONE;
      } else {
        currentPlayer = QuoridorPlayer.TWO;
      }
      match = match.doTurnUnchecked(new PlaceWallTurn(currentPlayer,
          new WallLocation(midWallLeftBegin.above(4 * i), midWallLeftVertex.above(4 * i), midWallLeftEnd.above(4 * i))));
    }

    for (int i = 0; i < 4; i++) {

      QuoridorPlayer currentPlayer;

      if(i % 2 == 0) {
        currentPlayer = QuoridorPlayer.ONE;
      } else {
        currentPlayer = QuoridorPlayer.TWO;
      }
      match = match.doTurnUnchecked(new PlaceWallTurn(currentPlayer,
          new WallLocation(midWallRightBegin.above(4 * i), midWallRightVertex.above(4 * i), midWallRightEnd.above(4 * i))));
    }
    */


    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);

    //System.out.println(actual);
  }


  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsTopLeft() {
  }

  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsTopRight() {
  }

  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsLeftMid() {
  }

  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsLeftBottom() {
  }

  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsRightMid() {
  }

  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsRightBottom() {
  }

  public void evaluate_ActivePlayerShortestPathBlocked_whenGoalIsBottomMid() {
  }

  }
