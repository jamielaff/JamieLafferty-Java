package com.jamielafferty.RPS.core;

import com.jamielafferty.RPS.utils.RPSUtils.Moves;
import com.jamielafferty.RPS.utils.RPSUtils.Results;

public class RPSRound {
	private Moves playerOneMove;
	private Moves playerTwoMove;
	private Results result;
	
	public RPSRound(Moves playerOneMove, Moves playerTwoMove, Results result) {
		this.playerOneMove = playerOneMove;
		this.playerTwoMove = playerTwoMove;
		this.result = result;
	}

	public Moves getPlayerOneMove() {
		return this.playerOneMove;
	}
	
	public Moves getPlayerTwoMove() {
		return this.playerTwoMove;
	}
	
	public Results getResult() {
		return this.result;
	}
}
