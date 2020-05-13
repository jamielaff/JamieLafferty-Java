package com.jamielafferty.RPS.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * 
 * @author Jamie
 *
 */
@Controller
@Scope("session")
public class RPSController {
	
	private static final Logger LOGGER = Logger.getLogger(RPSController.class.getName());
	private static final String API_VERSION = "/api/v1";

	@Autowired
	private RPSCoreEngine coreEngine;

	@Autowired
	public RPSController(RPSCoreEngine coreEngine) {
		this.coreEngine = coreEngine;
	}

	/**
	 * Default mapping to return the index jsp
	 * @param model
	 * @param session
	 * @return index jsp
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String load(Model model, HttpSession session) {
		LOGGER.info("Start - load main page");
        session.setAttribute("coreEngine", coreEngine);
		List<RPSGame> games = coreEngine.getAllGames();
		model.addAttribute("gamesList", games);
		LOGGER.info("Loaded all games");
		LOGGER.info("End - load main page");
		return "index";
	}

	/**
	 * Mapping to create a new game
	 * @return JSON of the new gameId
	 * @throws Exception
	 */
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

	/**
	 * Mapping to get a game with an ID
	 * @param id
	 * @param model
	 * @return game jsp
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = API_VERSION + "/games/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getGame(@PathVariable Integer id, Model model) throws Exception {
		LOGGER.info(String.format("Start - Get game with ID %d", id));
		// This method throws exception if game not found, which is then thrown by this method
		try {
			RPSGame rpsGame = coreEngine.getGame(id);
			model.addAttribute("selectedGameRounds", rpsGame.getPlayedRounds());
			LOGGER.info("Got the game and added to Model");
			return "game";
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			return "";
		}
	}

	/**
	 * Mapping to play a round of a game
	 * @param id
	 * @param model
	 * @return round jsp
	 * @throws Exception
	 */
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

	/**
	 * Mapping to restart a game with an ID
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = API_VERSION + "/games/{id}/restart")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void restartGame(@PathVariable Integer id) throws Exception {
		LOGGER.info(String.format("Start - restart game %d", id));
		try { 
			RPSGame game = coreEngine.getGame(id);
			game.restartGame();
			LOGGER.info("End - restarted game");
		} catch (Exception ex) {
			LOGGER.warning(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Mapping to update the top stats (total rounds, wins, ties) 
	 * @param model
	 * @return stats jsp
	 */
	@RequestMapping(method = RequestMethod.GET, value = API_VERSION + "/statistics")
	@ResponseStatus(HttpStatus.OK)
	public String refreshStatistics(Model model) {
		LOGGER.info("Start - Update top level statistics");
		model.addAttribute("totalRoundsPlayed", RPSCoreEngine.getTotalRoundsPlayed());
		model.addAttribute("totalP1Wins", RPSCoreEngine.getTotalP1Wins());
		model.addAttribute("totalP2Wins", RPSCoreEngine.getTotalP2Wins());
		model.addAttribute("totalTies", RPSCoreEngine.getTotalTies());
		LOGGER.info("End - Update stats");
		return "stats";
	}
}
