package com.shepherdjerred.capstone.ai.montecarlo;

import com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.ai.montecarlo.algorithm.NestedMonteCarloSearchState;
import com.shepherdjerred.capstone.ai.montecarlo.algorithm.Pair;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuoridorNestedMonteCarloSearchState implements
    NestedMonteCarloSearchState<Match, Turn> {

  private final Match match;
  private final PlayerId playerToOptimize;
  private final Turn turn;
  private final MatchEvaluator matchEvaluator;
  private final Random random = new Random();

  @Override
  public double getScore() {
    return matchEvaluator.evaluateMatch(match);
  }

  @Override
  public boolean isTerminalPosition() {
    return match.getMatchStatus().getStatus() != Status.IN_PROGRESS;
  }

  @Override
  public List<Turn> findAllLegalActions() {
    var turnGenerator = new TurnGenerator();
    var possibleTurns = turnGenerator.generateValidTurns(match);
    return new ArrayList<>(possibleTurns);
  }

  @Override
  public NestedMonteCarloSearchState<Match, Turn> takeAction(Turn turn) {
    var appliedMatchState = match.doTurn(turn);

    return new QuoridorNestedMonteCarloSearchState(appliedMatchState,
        playerToOptimize,
        turn,
        matchEvaluator);
  }

  @Override
  public Pair<Double, List<Turn>> simulation() {
    var turnGenerator = new TurnGenerator();
    List<Turn> possibleTurns = findAllLegalActions();
    List<Turn> simulatedTurns = new ArrayList<>();
    var tempMatch = match;

    while (possibleTurns.size() > 0) {
      Turn randomTurn = possibleTurns.get(random.nextInt(possibleTurns.size()));
      tempMatch = tempMatch.doTurn(randomTurn);
      simulatedTurns.add(randomTurn);
      possibleTurns = new ArrayList<>(turnGenerator.generateValidTurns(tempMatch));
    }
    return Pair.of(getScore(), simulatedTurns);
  }
}
