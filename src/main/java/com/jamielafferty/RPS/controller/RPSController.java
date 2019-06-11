package com.jamielafferty.RPS.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jamielafferty.RPS.core.RPSCoreEngine;
import com.jamielafferty.RPS.core.RPSGame;

/**
 * Class to handle all GAME operations (view all, create new, play a round, restart)
 * Check these are ALL the operations we need
 * 
 * @author Jamie
 *
 */
@Controller
public class RPSController {
	
	private static final Logger LOGGER = Logger.getLogger(RPSController.class.getName());

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
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewGame() throws Exception {
		LOGGER.info("Start - Create new game");
		// Create a new game in the game engine
		Integer gameId = coreEngine.createNewGame();

		LOGGER.info(String.format("End - created new game with ID {}", gameId));
		//URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(gameId).toUri();

		//return ResponseEntity.created(uri).body(coreEngine.getGame(gameId));
	}

	// TODO better exception
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RPSGame getGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - Get game with ID {}", id));
		// This method throws exception if game not found, which is then thrown by this
		// method
		return coreEngine.getGame(id);
	}

	// PUT method play a round in an existing game
	// Return the game to display on the page
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/round")
	void playRoundOfTheGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - play a round of game {}", id));
		// This method throws exception if game not found, which is then thrown by this
		// method
		RPSGame game = coreEngine.getGame(id);
		game.playRound();
		LOGGER.info("End - played a round");
	}

	// TODO void is temporary until the class has been created
	// TODO Throw a custom checked exception
	// PUT method to reset the current game. We will reuse the ID, so no need to
	// DELETE
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/restart")
	void restartGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - restart game {}", id));
		RPSGame game = coreEngine.getGame(id);
		game.restartGame();
		LOGGER.info("End - restarted game");
	}
}
