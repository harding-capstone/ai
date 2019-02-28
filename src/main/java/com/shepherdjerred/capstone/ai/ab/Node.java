package com.shepherdjerred.capstone.ai.ab;

import java.util.Set;

interface Node {

  Set<Node> getChildren();

  boolean isVictory();

  double getScore();
}
