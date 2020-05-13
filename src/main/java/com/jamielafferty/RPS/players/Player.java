package com.jamielafferty.RPS.players;

import com.jamielafferty.RPS.utils.RPSUtils.Moves;

/**
 * Interface to enforce the required function is implemented by classes which implement Player (in this case, makeMove()
 * 
 * @author Jamie
 *
 */
public interface Player {
	Moves makeMove();
}
