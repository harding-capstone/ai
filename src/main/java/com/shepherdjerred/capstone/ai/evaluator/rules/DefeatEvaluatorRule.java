package com.shepherdjerred.capstone.ai.evaluator.rules;

import static com.shepherdjerred.capstone.ai.evaluator.MatchEvaluator.MIN_SCORE;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import lombok.ToString;

@ToString
public class DefeatEvaluatorRule implements EvaluatorRule {

  @Override
  public double evaluate(Match match) {
    if (match.getMatchStatus().getStatus() == Status.VICTORY
        && match.getMatchStatus().getVictor() != match.getActivePlayerId()) {
      return MIN_SCORE;
    } else {
      return 0.0;
    }
  }
}
