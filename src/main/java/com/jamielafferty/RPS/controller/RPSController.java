package com.jamielafferty.RPS.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jamielafferty.RPS.core.RPSCoreEngine;
import com.jamielafferty.RPS.core.RPSGame;

/**
 * Class to handle all GAME operations (view all, create new, play a round, restart)
 * Check these are ALL the operations we need
 * 
 * @author Jamie
 *
 */
@RestController
public class RPSController {
	
	private static final Logger LOGGER = Logger.getLogger(RPSController.class.getName());
	private static final String API_VERSION = "/api/v1";

	@Autowired
	private RPSCoreEngine coreEngine;

	@Autowired
	public RPSController(RPSCoreEngine coreEngine) {
		this.coreEngine = coreEngine;
	}

	// Default mapping to return the index jsp
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String load(ModelMap modelMap) {
		LOGGER.info("Start - load main page");
		List<RPSGame> games = coreEngine.getAllGames();
		modelMap.put("gamesList", games);
		LOGGER.info("Loaded all games");
		LOGGER.info("End - load main page");
		return "index";
	}

	// POST method to create the game
	@RequestMapping(method = RequestMethod.POST, value = API_VERSION + "/games/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Integer createNewGame() throws Exception {
		LOGGER.info("Start - Create new game");
		// Create a new game in the game engine
		Integer gameId = coreEngine.createNewGame();
		
		LOGGER.info(String.format("End - created new game with ID %d", gameId));
		return gameId;
	}

	// TODO better exception
	@RequestMapping(method = RequestMethod.GET, value = API_VERSION + "/games/{id}")
	public HttpStatus getGame(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		LOGGER.info(String.format("Start - Get game with ID %d", id));
		// This method throws exception if game not found, which is then thrown by this method
		try {
			RPSGame rpsGame = coreEngine.getGame(id);
			modelMap.put("selectedGame", rpsGame);
			LOGGER.info("Got the game and added to ModelMap");
			return HttpStatus.OK;
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
	}

	// PUT method play a round in an existing game
	// Return the game to display on the page
	@RequestMapping(method = RequestMethod.PUT, value = API_VERSION + "/games/{id}/round")
	public HttpStatus playRoundOfTheGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - play a round of game %d", id));
		// This method throws exception if game not found, which is then thrown by this
		// method
		try {
			RPSGame game = coreEngine.getGame(id);
			game.playRound();
			LOGGER.info("End - played a round");
			return HttpStatus.CREATED;
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
	}

	// TODO void is temporary until the class has been created
	// TODO Throw a custom checked exception
	// PUT method to reset the current game. We will reuse the ID, so no need to
	// DELETE
	@RequestMapping(method = RequestMethod.PUT, value = API_VERSION + "/games/{id}/restart")
	public HttpStatus restartGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - restart game %d", id));
		try { 
			RPSGame game = coreEngine.getGame(id);
			game.restartGame();
			LOGGER.info("End - restarted game");
			return HttpStatus.OK;
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
