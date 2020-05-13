package com.jamielafferty.RPS.core;

import com.jamielafferty.RPS.utils.RPSUtils.Moves;
import com.jamielafferty.RPS.utils.RPSUtils.Results;

/**
 * Class to hold individual round data (ID, moves played and result)
 * 
 * @author Jamie
 *
 */
public class RPSRound {	
	private Integer id;
	private Moves playerOneMove;
	private Moves playerTwoMove;
	private Results result;
	
	public RPSRound(Integer id, Moves playerOneMove, Moves playerTwoMove, Results result) {
		this.id = id;
		this.playerOneMove = playerOneMove;
		this.playerTwoMove = playerTwoMove;
		this.result = result;
	}

	public Integer getId() {
		return this.id;
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
