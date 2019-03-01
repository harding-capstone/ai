package com.shepherdjerred.capstone.ai.montecarlo.algorithm;

import java.util.List;

/**
 * This interface represents the state of a problem, game or puzzle that can be searched with the
 * Nested Monte Carlo Search.
 *
 * <p>
 * Any implementation of this interface has to be immutable.
 *
 * @param <TState> type of the state.
 * @param <TAction> type of action that can be applied to the state.
 */
public interface NestedMonteCarloSearchState<TState, TAction> {

  /**
   * Get the current score.
   */
  double getScore();

  /**
   * Specify if the state is terminal. Terminal means that no more actions can be applied.
   *
   * @return <code>true</code> if state is terminal.
   */
  boolean isTerminalPosition();

  /**
   * Get all legal actions that can be applied to this state.
   *
   * @return all legal actions.
   */
  List<TAction> findAllLegalActions();

  /**
   * Apply an action to the state. The result of the application is returned from this function. The
   * state of the object that <code>TakeAction</code> was called on does not change.
   *
   * @param action action to be applied to the state.
   * @return new state after action was applied.
   */
  NestedMonteCarloSearchState<TState, TAction> takeAction(TAction action);

  /**
   * Perform a (level-0) play-out until a terminal state is created.
   *
   * <p>
   * The play-out policy can be random or guided.
   *
   * <p>
   * Calling this method does not change the state of the object.
   *
   * @return a tuple that contains the result (score) of the play-out and a list of actions that are
   * applied during the play-out.
     */
    Pair<Double, List<TAction>> simulation();

}
