package com.jamielafferty.RPS.utils;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Random;

public class RPSUtils {
	
	private static final Logger LOGGER = Logger.getLogger(RPSUtils.class.getName());
	
	/**
	 * Available moves
	 */
	public static enum Moves {
		ROCK,
		PAPER,
		SCISSORS
	};
	
	/**
	 * Winner results
	 */
	public static enum Results {
		TIE,
		PLAYER_ONE,
		PLAYER_TWO
	};
	
	/** 
	 * Final declaration of the rules of the game. HashMap is structured as:
	 * MovePlayed, MoveBeats
	 */
	public static final Map<Moves, Moves> rules;
	
	static {
		rules = new HashMap<>();
		rules.put(Moves.ROCK, Moves.SCISSORS);
		rules.put(Moves.PAPER, Moves.ROCK);
		rules.put(Moves.SCISSORS, Moves.PAPER);
	}
	
	/** 
	 * Method to select a random available move
	 * 
	 * @return a move - ROCK, PAPER or SCISSORS
	 */
	public static Moves getRandomMove() {
		LOGGER.info("Start - Get a random move to play");
		Random random = new Random();
		Moves moveToPlay = Moves.values()[random.nextInt(Moves.values().length)];
		LOGGER.info(String.format("End - Playing move %s", moveToPlay));
		return moveToPlay;
	}
	
	/**
	 * Function to determine the winner of the round.
	 * Check the moves are valid moves
	 * If the moves are the same, then it's a tie.
	 * If playerOneMove beats playerTwoMove, player 1 wins
	 * Otherwise, player 2 wins
	 * 
	 * @param playerOneMove the move player 1 made
	 * @param playerTwoMove the move player 2 made
	 * @return the result of the game - TIE, PLAYER_ONE or PLAYER_TWO only
	 * 
	 * TODO Throw a better/custom exception?
	 */
	public static Results getWinner(Moves playerOneMove, Moves playerTwoMove) throws IllegalArgumentException {
		LOGGER.info("Start - Determine who the winner is");
		// If either move is null or either move is not valid/legal, throw exception
		if (null == playerOneMove || null == playerTwoMove
				|| !(playerOneMove instanceof Moves) || !(playerTwoMove instanceof Moves)) {
			String errorMessage = String.format("Invalid move has been given - PlayerOne: %s, PlayerTwo: %s", playerOneMove, playerTwoMove);
			LOGGER.warning(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		
		// If they are the same move, then it's a tie
		if (playerOneMove == playerTwoMove) {
			LOGGER.info("End - It was a TIE");
			return Results.TIE;
		}
		// Otherwise, compare with the rules
		// If playerOneMove beats playerTwoMove, return player one is winner
		else if (rules.get(playerOneMove) == playerTwoMove) {
			LOGGER.info("End - PlayerOne has won");
			return Results.PLAYER_ONE;
		}
		// If not, then playerTwoMove has won
		else {
			LOGGER.info("End - PlayerTwo has won");
			return Results.PLAYER_TWO;
		}	
	}
}
