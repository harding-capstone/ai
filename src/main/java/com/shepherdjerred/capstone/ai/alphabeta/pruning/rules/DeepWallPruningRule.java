package com.shepherdjerred.capstone.ai.alphabeta.pruning.rules;

import com.shepherdjerred.capstone.ai.alphabeta.pruning.PruningQuoridorNode;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class DeepWallPruningRule implements PruningRule {

  private final int maxWallDepth;

  @Override
  public boolean shouldPrune(PruningQuoridorNode node) {
    if (node.getTurn() instanceof PlaceWallTurn) {
      return node.getCurrentDepth() > maxWallDepth;
    }
    return false;
  }
}
