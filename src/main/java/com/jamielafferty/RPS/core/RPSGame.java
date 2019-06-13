package com.jamielafferty.RPS.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import com.jamielafferty.RPS.players.Player;
import com.jamielafferty.RPS.players.PlayerFixedImpl;
import com.jamielafferty.RPS.players.PlayerRandomImpl;
import com.jamielafferty.RPS.utils.RPSUtils;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;
import com.jamielafferty.RPS.utils.RPSUtils.Results;

public class RPSGame {	
	// Counter to control unique game IDs
	protected static final AtomicInteger count = new AtomicInteger(0);
	// The game ID
	private Integer id;
	// Array to hold all rounds from this game
	private List<RPSRound> rpsRounds = new ArrayList<>();
	// Array to hold both players - used for shuffling into random order for the game
	List<Player> playerList = new ArrayList();
	
	// Player and win/tie counter declarations
	private PlayerRandomImpl playerRandomImpl = new PlayerRandomImpl();
	private PlayerFixedImpl playerFixedImpl = new PlayerFixedImpl();
	private Player playerOne;
	private Player playerTwo;
	private Integer playerOneWinCount = 0;
	private Integer playerTwoWinCount = 0;
	private Integer tieCount = 0;
	
	// Default constructor
	public RPSGame() {
		this.id = count.incrementAndGet();
		randomisePlayers();
	}
	
	// Custom constructor
	public RPSGame(Integer id) {
		this.id = id;
		randomisePlayers();
	}
	
	/**
	 * Method to handle playing a round of the game
	 */
	public RPSRound playRound() {
		// Calculate the round ID - SUM amm win/tie counts and increment 1
		Integer nextRoundNum = playerOneWinCount + playerTwoWinCount + tieCount + 1;
		// Make the moves for both players and determine winner
		Moves playerOneMove = playerOne.makeMove();
		Moves playerTwoMove = playerTwo.makeMove();
		Results result = RPSUtils.getWinner(playerOneMove, playerTwoMove);
		
		// Create round object
		RPSRound round = new RPSRound(nextRoundNum, playerOneMove, playerTwoMove, result);
		
		// Create new round object and store this round to the list
		rpsRounds.add(0, round);
		this.storeResult(result);
		return round;
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
	 * Function to randomise the order of the 2 players
	 */
	private void randomisePlayers() {
		// Clear our list to ensure it's empty
		playerList.clear();
		
		// Add both players to a list
		playerList.add(playerFixedImpl);
		playerList.add(playerRandomImpl);
		// Shuffle the list so players are not always in the same order
		Collections.shuffle(playerList);
		
		// Set players to list items 0 and 1 respectively
		playerOne = playerList.get(0);
		playerTwo = playerList.get(1);
	}
	
	public void setPlayers(PlayerFixedImpl playerFixedImpl, PlayerRandomImpl playerRandomImpl) {
		this.playerOne = playerFixedImpl;
		this.playerTwo = playerRandomImpl;
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
