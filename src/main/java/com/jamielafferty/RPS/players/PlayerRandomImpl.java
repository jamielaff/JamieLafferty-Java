package com.jamielafferty.RPS.players;

import com.jamielafferty.RPS.utils.RPSUtils;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;

/**
 * Implementation of Player interface
 * This will return a random move
 * 
 * @author Jamie
 *
 */
public class PlayerRandomImpl implements Player {

	@Override
	public Moves makeMove() {
		return RPSUtils.getRandomMove();
	}
}