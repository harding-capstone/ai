package com.shepherdjerred.capstone.ai.ab;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@AllArgsConstructor
class AlphaBetaSearch {
  private static final double MAX_DOUBLE = Double.MAX_VALUE;
  private static final double MIN_DOUBLE = Double.MIN_NORMAL;

  private final int depth;

  public Optional<Node> getBestNode(Node current) {
    return getBestNode(current, MIN_DOUBLE, MAX_DOUBLE, depth);
  }

  private Optional<Node> getBestNode(Node current, double alpha, double beta, int levelsLeft) {
    Optional<Node> result = Optional.empty();

    if (current.isVictory() || levelsLeft == 0) {
      return Optional.of(current);
    }

    var possible = current.getChildren();
    for (Node nodeToEvaluate : possible) {
      var worstNode = getWorstNode(nodeToEvaluate, alpha, beta, levelsLeft - 1);
      if (worstNode.isPresent()) {
        var score = worstNode.get().getScore();
        if (score > alpha) {
          alpha = score;
          result = Optional.of(nodeToEvaluate);
        }
        if (alpha >= beta) {
          break;
        }
      } else {
        log.error("Worst move not found");
        throw new IllegalStateException();
      }
    }

    return result;
  }

  private Optional<Node> getWorstNode(Node current, double alpha, double beta, int levelsLeft) {
    Optional<Node> result = Optional.empty();

    if (current.isVictory() || levelsLeft == 0) {
      return Optional.of(current);
    }

    var possible = current.getChildren();
    for (Node nodeToEvaluate : possible) {
      var bestNode = getBestNode(nodeToEvaluate, alpha, beta, levelsLeft - 1);
      if (bestNode.isPresent()) {
        var score = bestNode.get().getScore();
        if (score < beta) {
          beta = score;
          result = Optional.of(nodeToEvaluate);
        }
        if (alpha >= beta) {
          break;
        }
      } else {
        log.error("Best move not found");
        throw new IllegalStateException();
      }
    }

    return result;
  }

}
