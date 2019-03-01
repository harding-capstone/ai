package com.shepherdjerred.capstone.ai.search;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;

public interface BoardSearch {

  int findPath(Match match, PlayerId playerId);
}
