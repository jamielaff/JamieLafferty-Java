package com.jamielafferty.RPS.utils;

import java.util.Random;

public class RPSUtils {
	
	// Available moves
	public static enum Moves {
		ROCK,
		PAPER,
		SCISSORS
	};
	
	// Method to select a random available move
	public static Moves getRandomMove() {
		Random random = new Random();
		return Moves.values()[random.nextInt(Moves.values().length)];
	}
}
