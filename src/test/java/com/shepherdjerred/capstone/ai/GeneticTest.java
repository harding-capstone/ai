package com.shepherdjerred.capstone.ai;

import com.shepherdjerred.capstone.ai.evaluator.EvaluatorWeights;
import com.shepherdjerred.capstone.ai.genetic.WeightsProblem;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import org.junit.Test;

public class GeneticTest {

  @Test
  public void test() {
    var weights = new WeightsProblem();

    final Engine<DoubleGene, Integer> engine = Engine
        .builder(weights.fitness(), weights.codec())
        .build();

    final EvolutionStatistics<Integer, ?>
        statistics = EvolutionStatistics.ofNumber();

    final Genotype<DoubleGene> gt = engine.stream()
        .limit(100)
        .peek(statistics)
        .peek(r -> System.out.println(statistics))
        .collect(EvolutionResult.toBestGenotype());

    final EvaluatorWeights param = weights.decode(gt);
    System.out.println(String.format("Result: \t%s", param));
  }
}
