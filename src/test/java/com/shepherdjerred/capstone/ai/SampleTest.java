package com.shepherdjerred.capstone.ai;

<<<<<<< Updated upstream
public class SampleTest {

=======
import com.shepherdjerred.capstone.ai.ab.AlphaBetaQuoridorAi;
import com.shepherdjerred.capstone.ai.ab.evaluator.DefaultMatchEvaluator;
import com.shepherdjerred.capstone.ai.ab.evaluator.RandomMatchEvaluator;
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
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import org.junit.Test;

public class SampleTest {

  @Test
  public void test() {
    var enactor = new MatchTurnEnactor(new TurnEnactorFactory(), new TurnValidator());

    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, boardSettings);

    var boardCellsInitializer = new BoardCellsInitializer();
    var boardLayout = BoardLayout.fromBoardSettings(boardCellsInitializer, boardSettings);

    var pieceBoardLocationsInitializer = new BoardPiecesInitializer();
    var pieceBoardLocations = BoardPieces.initializePieceLocations(boardSettings,
        pieceBoardLocationsInitializer);

    var board = Board.createBoard(boardLayout, pieceBoardLocations);
    var initialMatchState = Match.startNewMatch(matchSettings, board);

    var alphaBetaAi = new AlphaBetaQuoridorAi(new DefaultMatchEvaluator());
    var randomAi = new AlphaBetaQuoridorAi(new RandomMatchEvaluator());
    var currentMatchState = initialMatchState;

    var matchFormatter = new MatchFormatter();
    matchFormatter.matchToString(initialMatchState);

    int i = 0;
    while (currentMatchState.getMatchStatus().getStatus() == Status.IN_PROGRESS) {
      Turn aiTurn;
      if (currentMatchState.getActivePlayerId() == PlayerId.ONE) {
         aiTurn = alphaBetaAi.calculateBestTurn(currentMatchState);
      } else {
        aiTurn = randomAi.calculateBestTurn(currentMatchState);
      }

      currentMatchState = currentMatchState.doTurn(aiTurn, enactor);
      System.out.println(aiTurn);
      System.out.println(matchFormatter.matchToString(currentMatchState));
      System.out.println(i + "\n\n\n");
      i++;
    }

  }

>>>>>>> Stashed changes
}
