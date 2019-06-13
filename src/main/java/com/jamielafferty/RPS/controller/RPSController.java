package com.jamielafferty.RPS.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jamielafferty.RPS.core.RPSCoreEngine;
import com.jamielafferty.RPS.core.RPSGame;
import com.jamielafferty.RPS.core.RPSRound;

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

	@RequestMapping(method = RequestMethod.POST, value = API_VERSION + "/games/create", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public String createNewGame() throws Exception {
		LOGGER.info("Start - Create new game");
		// Create a new game in the game engine
		Integer gameId = coreEngine.createNewGame();
		
		LOGGER.info(String.format("End - created new game with ID %d", gameId));
		return "{\"gameId\" : " + gameId.toString() + "}";
	}

	@RequestMapping(method = RequestMethod.GET, value = API_VERSION + "/games/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getGame(@PathVariable Integer id, Model model) throws Exception {
		LOGGER.info(String.format("Start - Get game with ID %d", id));
		// This method throws exception if game not found, which is then thrown by this method
		try {
			RPSGame rpsGame = coreEngine.getGame(id);
			model.addAttribute("selectedGameRounds", rpsGame.getPlayedRounds());
			LOGGER.info("Got the game and added to ModelMap");
			return "game";
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			return "";
		}
	}

	// PUT method play a round in an existing game
	@RequestMapping(method = RequestMethod.POST, value = API_VERSION + "/games/{id}/rounds")
	@ResponseStatus(HttpStatus.OK)
	public String playRoundOfTheGame(@PathVariable Integer id, Model model) throws Exception {
		LOGGER.info(String.format("Start - play a round of game %d", id));
		// This method throws exception if game not found, which is then thrown by this
		// method
		try {
			RPSGame game = coreEngine.getGame(id);
			RPSRound playedRound = game.playRound();
			model.addAttribute("roundId", playedRound.getId());
			model.addAttribute("playerOneMove", playedRound.getPlayerOneMove());
			model.addAttribute("playerTwoMove", playedRound.getPlayerTwoMove());
			model.addAttribute("result", playedRound.getResult());
			LOGGER.info("End - played a round");
			return "round";
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			return "";
		}
		
	}

	// PUT method to reset the current game. We will reuse the ID, so no need to DELETE
	@RequestMapping(method = RequestMethod.PUT, value = API_VERSION + "/games/{id}/restart")
	@ResponseStatus(HttpStatus.OK)
	public void restartGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - restart game %d", id));
		try { 
			RPSGame game = coreEngine.getGame(id);
			game.restartGame();
			LOGGER.info("End - restarted game");
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
		}
	}
}
