package com.shepherdjerred.capstone.ai;

import com.shepherdjerred.capstone.ai.ab.AlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.evaluator.DefaultMatchEvaluator;
import com.shepherdjerred.capstone.ai.evaluator.RandomMatchEvaluator;
import com.shepherdjerred.capstone.ai.montecarlo.MonteCarloQuoridorAi;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardPieces;
import com.shepherdjerred.capstone.logic.board.BoardPiecesInitializer;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.layout.BoardCellsInitializer;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import org.junit.Ignore;
import org.junit.Test;

public class SampleTest {

  @Test
  public void monteCarloVersusAlphaBeta() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, boardSettings);

    var boardCellsInitializer = new BoardCellsInitializer();
    var boardLayout = BoardLayout.fromBoardSettings(boardCellsInitializer, boardSettings);

    var pieceBoardLocationsInitializer = new BoardPiecesInitializer();
    var pieceBoardLocations = BoardPieces.initializePieceLocations(boardSettings,
        pieceBoardLocationsInitializer);

    var board = Board.createBoard(boardLayout, pieceBoardLocations);
    var match = Match.startNewMatch(matchSettings, board);

    var monte = new MonteCarloQuoridorAi(1, new DefaultMatchEvaluator(), 6000);
    var alpha = new AlphaBetaQuoridorAi(new DefaultMatchEvaluator(), 3);

    simulateAi(match, monte, alpha);
  }

  @Ignore
  @Test
  public void alphaBetaVersusRandom() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, boardSettings);

    var boardCellsInitializer = new BoardCellsInitializer();
    var boardLayout = BoardLayout.fromBoardSettings(boardCellsInitializer, boardSettings);

    var pieceBoardLocationsInitializer = new BoardPiecesInitializer();
    var pieceBoardLocations = BoardPieces.initializePieceLocations(boardSettings,
        pieceBoardLocationsInitializer);

    var board = Board.createBoard(boardLayout, pieceBoardLocations);
    var initialMatchState = Match.startNewMatch(matchSettings, board);

    var alphaBetaAi = new AlphaBetaQuoridorAi(new DefaultMatchEvaluator(), 2);
    var randomAi = new AlphaBetaQuoridorAi(new RandomMatchEvaluator(), 2);

    simulateAi(initialMatchState, alphaBetaAi, randomAi);
  }

  private void simulateAi(Match match, QuoridorAi playerOne, QuoridorAi playerTwo) {
    var matchFormatter = new MatchFormatter();
    matchFormatter.matchToString(match);

    int currentTurn = 1;
    while (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {
      System.out.println("=== Turn: " + currentTurn);
      System.out.println("=== Player: " + match.getActivePlayerId());

      Turn aiTurn;
      if (match.getActivePlayerId() == PlayerId.ONE) {
        aiTurn = playerOne.calculateBestTurn(match);
      } else {
        aiTurn = playerTwo.calculateBestTurn(match);
      }

//      System.out.println(currentMatchState.getMatchStatus().getStatus());
      match = match.doTurn(aiTurn);
//      System.out.println(aiTurn);
      System.out.println(matchFormatter.matchToString(match));
      System.out.println("\n\n");

      currentTurn++;
    }
  }
}
