package com.jamielafferty.RPS.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.jamielafferty.RPS.core.RPSCoreEngine;
import com.jamielafferty.RPS.core.RPSGame;

/**
 * Class to handle all GAME operations (new, play, cancel, view completed) TODO
 * Check these are ALL the operations we need
 * 
 * @author Jamie
 *
 */
@Controller
public class RPSController {

	@Autowired
	private RPSCoreEngine coreEngine;

	@Autowired
	public RPSController(RPSCoreEngine coreEngine) {
		this.coreEngine = coreEngine;
	}

	// Default mapping to return the index jsp
	@RequestMapping(method = RequestMethod.GET, value = "/")
//	public String handler(ModelMap modelMap) {
	public String handler(ModelMap modelMap) {
		List<RPSGame> games = coreEngine.getAllGames();
		modelMap.put("gamesList", games);
		return "index";
	}

	// POST method to create the game
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity createNewGame(HttpServletResponse httpServletResponse) throws Exception {
		// Create a new game in the game engine
		Integer gameId = coreEngine.createNewGame();

		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(gameId).toUri();

		return ResponseEntity.created(uri).body(coreEngine.getGame(gameId));
	}

	// TODO better exception
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RPSGame getGame(@PathVariable Integer id) throws Exception {
		// This method throws exception if game not found, which is then thrown by this
		// method
		return coreEngine.getGame(id);
	}

	// PUT method play a round in an existing game
	// Return the game to display on the page
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/round")
	RPSGame playRoundOfTheGame(@PathVariable Integer id) throws Exception {
		// This method throws exception if game not found, which is then thrown by this
		// method
		RPSGame game = coreEngine.getGame(id);
		game.playRound();
		return game;
	}

	// TODO void is temporary until the class has been created
	// TODO Throw a custom checked exception
	// PUT method to reset the current game. We will reuse the ID, so no need to
	// DELETE
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/restart")
	void restartGame(@PathVariable Integer id) throws Exception {
		RPSGame game = coreEngine.getGame(id);
		game.restartGame();

	}
}
