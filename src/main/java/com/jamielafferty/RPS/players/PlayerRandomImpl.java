package com.jamielafferty.RPS.players;

import com.jamielafferty.RPS.utils.RPSUtils;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;

public class PlayerRandomImpl implements Player {

	@Override
	public Moves makeMove() {
		return RPSUtils.getRandomMove();
	}
}