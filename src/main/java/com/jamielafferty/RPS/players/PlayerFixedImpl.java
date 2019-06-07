package com.jamielafferty.RPS.players;

import com.jamielafferty.RPS.utils.RPSUtils.Moves;

public class PlayerFixedImpl implements Player {

	@Override
	public Moves makeMove() {
		return Moves.ROCK;
	}
}
