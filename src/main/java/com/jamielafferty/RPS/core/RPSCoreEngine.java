package com.jamielafferty.RPS.core;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class RPSCoreEngine {
	
	private static final Logger LOGGER = Logger.getLogger(RPSCoreEngine.class.getName());

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
		rpsGames.add(newGame);
		return newGame.getId();
	}
	
	//TODO better exception
	//TODO do this better, use the stream method
	public RPSGame getGame(Integer id) throws Exception {
		for (RPSGame game : rpsGames) {
			if (game.getId() == id) {
				return game;
			}
		}
		LOGGER.warning(String.format("Could not get game with ID %d", id));
		throw new Exception("Game not found");
			
	
	public List<RPSGame> getAllGames() {
		return rpsGames;
	}
}
