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

public class ShortestPathTest {

  @Test
  public void evaluate_ShortestPathTest_whenAtStartingPosition() {
    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ShortestPathEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 8;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_ShortestPathTest_whenAboveStartingPosition() {

    Coordinate bottomMid = new Coordinate(8, 0);
    Coordinate up1 = bottomMid.above(2);
    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.ONE, bottomMid, up1));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new ShortestPathEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = 7;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

}
