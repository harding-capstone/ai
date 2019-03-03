package com.shepherdjerred.capstone.ai.alphabeta.pruning;

import com.github.bentorfs.ai.common.TreeNode;
import com.shepherdjerred.capstone.ai.alphabeta.IQuoridorNode;
import com.shepherdjerred.capstone.ai.alphabeta.pruning.rules.PruningRule;
import com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString(exclude = {"match", "matchEvaluator"})
@AllArgsConstructor
public class PruningQuoridorNode implements IQuoridorNode {

  private final PlayerId optimizingPlayer;
  private final Match match;
  private final Turn turn;
  private final MatchEvaluator matchEvaluator;
  private final Set<PruningRule> pruningRules;
  private final int currentDepth;

  @Override
  public Collection<TreeNode> getChildNodes() {
    var turnGenerator = new TurnGenerator(new TurnValidatorFactory());
    var possibleTurns = turnGenerator.generateValidTurns(match);

    return possibleTurns.stream()
        .map(turn -> {
//          System.out.println(turn);
          var newMatchState = match.doTurn(turn);
          PlayerId op;
          if (currentDepth == 0) {
            op = optimizingPlayer;
          } else {
            op = newMatchState.getActivePlayerId();
          }
          return new PruningQuoridorNode(op,
              newMatchState,
              turn,
              matchEvaluator,
              pruningRules,
              currentDepth + 1);
        })
        .filter(node -> pruningRules.stream()
            .noneMatch(rule -> rule.shouldPrune(node)))
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isSolutionNode() {
    return match.getMatchStatus().getStatus() == Status.VICTORY;
  }

  @Override
  public double getValue() {
//    log.info("OPTIMIZING FOR " + optimizingPlayer + " AP: " + match.getActivePlayerId() + " DEPTH: " + getCurrentDepth());
    return matchEvaluator.evaluateMatch(match, optimizingPlayer);
  }
}
