package com.shepherdjerred.capstone.ai.search.astar;

import com.github.bentorfs.ai.common.TreeNode;
import com.github.bentorfs.ai.search.asearch.AStarSearchNode;
import com.shepherdjerred.capstone.ai.evaluator.EvaluationUtils;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString(exclude = {"match", "goals", "playerId"})
@AllArgsConstructor
public class BoardAStarSearchNode extends AStarSearchNode {

  private final Match match;
  private final PlayerId playerId;
  private final Coordinate location;
  private final Set<Coordinate> goals;
  private final int cost;

  @Override
  public List<TreeNode> getChildNodes() {
    var generator = new TurnGenerator();
    var validTurns = generator.generateValidTurns(match);
//    log.info(validTurns);
    List<TreeNode> destinations = validTurns.stream()
        .filter(turn -> turn instanceof MovePawnTurn)
        .map(turn -> (MovePawnTurn) turn)
        .map(turn -> {
          log.info(turn);
          return turn.getDestination();
        })
        .map(destination -> new BoardAStarSearchNode(match, playerId, destination, goals, cost + 1))
        .collect(Collectors.toList());
    log.info("Finished");
    return destinations;
  }

  @Override
  public boolean isSolutionNode() {
    return goals.contains(location);
  }

  @Override
  public int getCostSoFar() {
    return cost;
  }

  @Override
  public int getEstimatedCostToSolution() {
    return EvaluationUtils.getBlindDistanceToGoal(match.getBoard(), playerId) / 2;
  }

  @Override
  public boolean isSamePosition(AStarSearchNode node) {
    if (node instanceof BoardAStarSearchNode) {
      var boardNode = (BoardAStarSearchNode) node;
      return location.equals(boardNode.getLocation());
    } else {
      log.error("Invalid comparison");
      return false;
    }
  }

}
