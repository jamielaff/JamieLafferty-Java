package com.jamielafferty.RPS.core;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class RPSCoreEngine {
	
	private static final Logger LOGGER = Logger.getLogger(RPSCoreEngine.class.getName());
	
	// Static variables to keep an overall tally
	private static Integer TOTAL_ROUNDS_PLAYED = 0;
	private static Integer TOTAL_WINS_P1 = 0;
	private static Integer TOTAL_WINS_P2 = 0;
	private static Integer TOTAL_TIES = 0;

	private List<RPSGame> rpsGames;
	
	public RPSCoreEngine() {
		rpsGames = new ArrayList<>();
	}
	
	/**
	 * This function creates a new instance of RPSGame and stores to the List<> variable declared abov
	 * @return the newly created gameID
	 */
	public Integer createNewGame() {
		RPSGame newGame = new RPSGame();
		rpsGames.add(0, newGame);
		return newGame.getId();
	}
	
	/**
	 * This function will get an instance of a game given an ID
	 * @param id of the game to get
	 * @return the game with given ID
	 * @throws Exception when game ID does not exist
	 */
	public RPSGame getGame(Integer id) throws Exception {
		for (RPSGame game : rpsGames) {
			if (game.getId() == id) {
				return game;
			}
		}
		
		LOGGER.warning(String.format("Could not get game with ID %d", id));
		throw new Exception("Game not found");
	}
	
	/**
	 * Function to get all the games that exist
	 * @return rpsGames list which holds all active games
	 */
	public List<RPSGame> getAllGames() {
		return rpsGames;
	}
	
	/**
	 * Function to clear all existing games, resetting to 0
	 */
	public void clearAllGames() {
		this.rpsGames.clear();
	}
	
	/**
	 * Function to get total rounds played
	 * @return total rounds played
	 */
	public static Integer getTotalRoundsPlayed() {
		return TOTAL_ROUNDS_PLAYED;
	}
	/**
	 * Function to increment total rounds played
	 */
	public static void incrementTotalRoundsPlayed() {
		TOTAL_ROUNDS_PLAYED++;
	}
	
	/**
	 * Function to get total Player1 wins
	 * @return total Player1 wins
	 */
	public static Integer getTotalP1Wins() {
		return TOTAL_WINS_P1;
	}
	/**
	 * Function to increment total Player1 wins
	 */
	public static void incrementTotalP1Wins() {
		TOTAL_WINS_P1++;
	}
	
	/**
	 * Function to get total Player2 wins
	 * @return total Player2 wins
	 */
	public static Integer getTotalP2Wins() {
		return TOTAL_WINS_P2;
	}
	/**
	 * Function to increment total Player2 wins
	 */
	public static void incrementTotalP2Wins() {
		TOTAL_WINS_P2++;
	}
	
	/**
	 * Function to get total Ties
	 * @return total Ties
	 */
	public static Integer getTotalTies() {
		return TOTAL_TIES;
	}
	/**
	 * Function to increment total Ties
	 */
	public static void incrementTotalTies() {
		TOTAL_TIES++;
	}
}
