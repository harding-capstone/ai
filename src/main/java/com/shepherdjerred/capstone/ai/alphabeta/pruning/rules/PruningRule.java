package com.shepherdjerred.capstone.ai.alphabeta.pruning.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface PruningRule {

  boolean shouldPrune(Match match, Turn turn);
}
