module com.shepherdjerred.capstone.ai {
  requires static lombok;
  requires com.shepherdjerred.capstone.logic;
  requires org.apache.logging.log4j;
  requires ai.algorithms;
  exports com.shepherdjerred.capstone.ai;
  exports com.shepherdjerred.capstone.ai.ab;
  exports com.shepherdjerred.capstone.ai.evaluator;
}
