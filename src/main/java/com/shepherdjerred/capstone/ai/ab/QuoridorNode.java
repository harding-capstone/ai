package com.shepherdjerred.capstone.ai.ab;

import com.github.bentorfs.ai.common.TreeNode;
import com.shepherdjerred.capstone.ai.ab.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString(exclude = {"match", "matchEvaluator"})
@AllArgsConstructor
class QuoridorNode implements Comparable<QuoridorNode>, TreeNode {

  private final PlayerId activePlayer;
  private final Match match;
  private final Turn turn;
  private final MatchEvaluator matchEvaluator;

  @Override
  public Collection<TreeNode> getChildNodes() {
    var turnGenerator = new TurnGenerator();
    var turnEnactor = new MatchTurnEnactor(new TurnEnactorFactory(), new TurnValidator());
    var possibleTurns = turnGenerator.generateValidTurns(match);

    return possibleTurns.stream()
        .map(turn -> {
          var newMatchState = match.doTurn(turn, turnEnactor);
          return new QuoridorNode(activePlayer, newMatchState, turn, matchEvaluator);
        })
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isSolutionNode() {
    return match.getMatchStatus().getVictor() == activePlayer;
  }

  @Override
  public double getValue() {
    return matchEvaluator.evaluateMatch(match, activePlayer);
  }

  @Override
  public int compareTo(QuoridorNode node) {
    return Double.compare(getValue(), node.getValue());
  }
}
