package com.jamielafferty.RPS.players;

import com.jamielafferty.RPS.utils.RPSUtils.Moves;

/**
 * Implementation of Player interface
 * This will ALWAYS return ROCK
 * 
 * @author Jamie
 *
 */
public class PlayerFixedImpl implements Player {

	@Override
	public Moves makeMove() {
		return Moves.ROCK;
	}
}
