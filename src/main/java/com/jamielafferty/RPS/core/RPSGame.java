package com.jamielafferty.RPS.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.jamielafferty.RPS.players.PlayerFixedImpl;
import com.jamielafferty.RPS.players.PlayerRandomImpl;
import com.jamielafferty.RPS.utils.RPSUtils;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;
import com.jamielafferty.RPS.utils.RPSUtils.Results;

public class RPSGame {
	// Counter to control unique game IDs
	private static final AtomicInteger count = new AtomicInteger(0);
	// The game ID
	private Integer id;
	// Array to hold all rounds from this game
	private List<RPSRound> rpsRounds = new ArrayList<>();
	
	// Player and win/tie counter declarations
	private PlayerRandomImpl playerOne = new PlayerRandomImpl();
	private Integer playerOneWinCount = 0;
	private PlayerFixedImpl playerTwo = new PlayerFixedImpl();
	private Integer playerTwoWinCount = 0;
	private Integer tieCount = 0;
	
	// Default constructor
	public RPSGame() {
		this.id = count.incrementAndGet();
	}
	
	// Custom constructor
	public RPSGame(Integer id) {
		this.id = id;
	}
	
	public void playRound() {
		// Make the moves for both players and determine winner
		Moves playerOneMove = playerOne.makeMove();
		Moves playerTwoMove = playerTwo.makeMove();
		Results result = RPSUtils.getWinner(playerOneMove, playerTwoMove);
		
		// Create new round object and store this round to the list
		rpsRounds.add(new RPSRound(playerOneMove, playerTwoMove, result));
		this.storeResult(result);
	}
	/**
	 * Function to increase the correct result counter based on the result input
	 * @param result the result of the round played
	 */
	public void storeResult(Results result) {
		if (result == Results.TIE) {
			tieCount++;
		} else if (result == Results.PLAYER_ONE) {
			playerOneWinCount++;
		} else {
			playerTwoWinCount++;
		}
	}
	
	/**
	 * Function to reset the counters and list when the user selects to restart the game
	 */
	public void restartGame() {
		playerOneWinCount = 0;
		playerTwoWinCount = 0;
		tieCount = 0;
		rpsRounds.clear();
	}
	
	/**
	 * Function to get the unique ID of this game
	 * @return the gameId
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Function to get number of wins for player1
	 * @return playerOneWinCount
	 */
	public Integer getPlayerOneWinCount() {
		return playerOneWinCount;
	}
	
	/**
	 * Function to get number of wins for player2
	 * @return playerTwoWinCount
	 */
	public Integer getPlayerTwoWinCount() {
		return playerTwoWinCount;
	}
	
	/**
	 * Function to get number of ties
	 * @return tieCount
	 */
	public Integer getTieCount() {
		return tieCount;
	}
	
	/**
	 * Function to get number of games played
	 * @return calculated number of round played (sum player1wins, player2wins and ties)
	 */
	public Integer getRoundsPlayedCount() {
		return (playerOneWinCount + playerTwoWinCount + tieCount); 
	}
	
	/**
	 * Function to get all played rounds
	 * @return all played rounds
	 */
	public List<RPSRound> getPlayedRounds() {
		return rpsRounds;
	}
	
	/**
	 * Function to return the last played round
	 * @return the last round played
	 * TODO Is there a better way to do this?
	 */
	public RPSRound getLastRound() {
		if (rpsRounds.size()>0) {
			return rpsRounds.get(rpsRounds.size()-1);
		}
		return null;
	}
}
