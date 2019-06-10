package com.jamielafferty.RPS.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RPSUtils {
	
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
	public static final Map<Moves, Moves> rules = new HashMap<>();
	
	public static void initRules() {
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
		Random random = new Random();
		return Moves.values()[random.nextInt(Moves.values().length)];
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
		// If either move is null or either move is not valid/legal, throw exception
		if (null == playerOneMove || null == playerTwoMove
				|| !(playerOneMove instanceof Moves) || !(playerTwoMove instanceof Moves)) {
			throw new IllegalArgumentException("Invalid move given: playerOneMove == " + playerOneMove + ", playerTwoMove == " + playerTwoMove);
		}
		
		// If rules is empty, init them
		//FIXME this needs to be auto done
		if (rules.isEmpty()) initRules();
		
		// If they are the same move, then it's a tie
		if (playerOneMove == playerTwoMove) {
			return Results.TIE;
		}
		// Otherwise, compare with the rules
		// If playerOneMove beats playerTwoMove, return player one is winner
		else if (rules.get(playerOneMove) == playerTwoMove) {
			return Results.PLAYER_ONE;
		}
		// If not, then playerTwoMove has won
		else {
			return Results.PLAYER_TWO;
		}	
	}
}
