package com.shepherdjerred.capstone.ai.evaluator.rules;

import static org.junit.Assert.assertEquals;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallLocation;
import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
//import lombok.AllArgsConstructor;
import org.junit.Test;


public class ActivePlayerShortestPathBlockedTest {
  //private final BoardSearch boardSearch;
  //private final PlayerGoals playerGoals;

  @Test
  public void evaluate_ActivePlayerShortestPathBlocked_whenPlayerIsMidboard() {
    var middle = 8;
    Coordinate midWallLeftBegin = new Coordinate(7, 0);
    Coordinate midWallLeftVertex = new Coordinate(7, 1);
    Coordinate midWallLeftEnd = new Coordinate(7, 2);
    Coordinate midWallRightBegin = new Coordinate(9, 0);
    Coordinate midWallRightVertex = new Coordinate(9, 1);
    Coordinate midWallRightEnd = new Coordinate(9, 2);
    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

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


    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    BoardSearch boardSearch;
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ActivePlayerShortestPathBlockedEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    assertEquals(expected, actual, 0);

  }


}
