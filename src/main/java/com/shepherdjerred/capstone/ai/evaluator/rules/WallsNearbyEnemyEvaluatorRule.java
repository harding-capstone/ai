package com.shepherdjerred.capstone.ai.evaluator.rules;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import lombok.ToString;

@ToString
public class WallsNearbyEnemyEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match, QuoridorPlayer playerToOptimize) {
    double scoreValue;
    double bonuses = 0;
    PlayerCount numberOfPlayers = match.getMatchSettings().getPlayerCount();
    boolean Player1 = match.getMatchSettings().getStartingQuoridorPlayer().equals(playerToOptimize);

    if(numberOfPlayers.equals(PlayerCount.TWO)) {
      QuoridorPlayer opponent = match.getNextActivePlayerId();
      Coordinate opponentLocation = match.getBoard().getPawnLocation(opponent);
      if (match.getBoard().hasWall(opponentLocation.above(3))) {
        bonuses++;
      }

      if (match.getBoard().hasWall(opponentLocation.toLeft(3))) {
        bonuses++;
      }

      if (match.getBoard().hasWall(opponentLocation.toRight(3))) {
        bonuses++;
      }

      if (match.getBoard().hasWall(opponentLocation.below(3))) {
        bonuses++;
      }
    } else {
      // TODO
      //4 Player game, need to determine which player PTO is, then loop through the others and get
      // a score. May be able to do that without knowing which player PTO is actually.
    }

    scoreValue = 100 - bonuses * 33.3;
    return scoreValue;
  }
}
