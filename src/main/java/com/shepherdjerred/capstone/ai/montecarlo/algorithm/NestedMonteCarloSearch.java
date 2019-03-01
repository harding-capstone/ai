package com.shepherdjerred.capstone.ai.montecarlo.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NestedMonteCarloSearch {

  public static <TState, TAction> Pair<Double, List<TAction>> executeSearch(
      NestedMonteCarloSearchState<TState, TAction> state,
      final int level, final Supplier<Boolean> isCanceled) {

    // terminating case
    if (level <= 0) {
      return state.simulation();
    }

    Pair<Double, List<TAction>> globalBestResult = Pair.of(state.getScore(),
        new LinkedList<>());
    final List<TAction> visitedNodes = new LinkedList<>();

    while (!state.isTerminalPosition() && !isCanceled.get()) {

      Pair<Double, List<TAction>> currentBestResult = Pair.of(0.0, new LinkedList<>());
      TAction currentBestAction = null;

      for (TAction action : state.findAllLegalActions()) {
        final NestedMonteCarloSearchState<TState, TAction> currentState = state.takeAction(action);
        // recursion
        final Pair<Double, List<TAction>> simulationResult = executeSearch(currentState,
            level - 1,
            isCanceled);

        if (simulationResult.item1 >= currentBestResult.item1) {
          currentBestAction = action;
          currentBestResult = simulationResult;
        }
      }

      if (currentBestResult.item1 >= globalBestResult.item1) {
        visitedNodes.add(currentBestAction);
        globalBestResult = currentBestResult;
        globalBestResult.item2.addAll(0, visitedNodes);
      } else {
        currentBestAction = globalBestResult.item2.get(visitedNodes.size());
        visitedNodes.add(currentBestAction);
      }

      state = state.takeAction(currentBestAction);
    }
    return globalBestResult;
  }
}
