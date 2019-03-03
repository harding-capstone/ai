module com.shepherdjerred.capstone.ai {
  requires static lombok;
  requires com.shepherdjerred.capstone.logic;
  requires org.apache.logging.log4j;
  requires ai.algorithms;
  requires io.jenetics.base;
  requires com.google.common;
  exports com.shepherdjerred.capstone.ai;
  exports com.shepherdjerred.capstone.ai.alphabeta;
  exports com.shepherdjerred.capstone.ai.evaluator;
  exports com.shepherdjerred.capstone.ai.alphabeta.pruning;
  exports com.shepherdjerred.capstone.ai.alphabeta.pruning.rules;
}
