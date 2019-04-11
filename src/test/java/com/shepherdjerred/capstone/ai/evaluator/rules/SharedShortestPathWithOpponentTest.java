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

public class SharedShortestPathWithOpponentTest {

  @Test
  public void evaluate_SharedShortestPathWithOpponent_whenOnlyTwoPlayers() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate mid = new Coordinate(8, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, mid));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new SharedShortestPathWithOpponentEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_SharedShortestPathWithOpponent_whenFourPlayers() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate leftMid = new Coordinate(0, 8);
    Coordinate rightMid = new Coordinate(16, 8);

    Coordinate mid = new Coordinate(8, 8);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.FOUR),
        new BoardSettings(9, PlayerCount.FOUR));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, mid));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.THREE, leftMid, mid.above(2)));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.FOUR, rightMid, mid.below(2)));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new SharedShortestPathWithOpponentEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 1;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }

  @Test
  public void evaluate_SharedShortestPathWithOpponent_whenPlayersAreNotOnSamePath() {
    Coordinate topMid = new Coordinate(8, 16);
    Coordinate topRight = new Coordinate(16, 16);

    var match = Match.from(new MatchSettings(10, QuoridorPlayer.TWO, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    match = match.
        doTurnUnchecked(new NormalMovePawnTurn(QuoridorPlayer.TWO, topMid, topRight));

    System.out.println(match.getBoard().getPieceLocations());
    System.out.println(match.getMatchStatus());

    AStarBoardSearch boardSearch = new AStarBoardSearch();
    PlayerGoals playerGoals = new PlayerGoals();

    var rule = new SharedShortestPathWithOpponentEvaluatorRule(boardSearch, playerGoals);

    var actual = rule.evaluate(match, QuoridorPlayer.ONE);
    var expected = (double) 0;

    System.out.println(actual);

    assertEquals(expected, actual, 0);
  }
}
