package com.jamielafferty.RPS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class to handle all GAME operations (new, play, cancel, view completed)
 * TODO Check these are ALL the operations we need
 * 
 * @author Jamie
 *
 */
@RestController
@RequestMapping(value = "/games")
public class RPSController {

	public RPSController() {
		
	}
	
	//TODO void is temporary until the class has been created
	// POST method to create the game
	@RequestMapping(method = RequestMethod.POST, value = "")
	void createNewGame() {
		
	}
	
	//TODO void is temporary until the class has been created
	//TODO Throw a custom checked exception
	// PUT method play the already created game
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	void playGame() throws Exception {
		
	}
	
	//TODO void is temporary until the class has been created
	//TODO Throw a custom checked exception
	// PUT method play a round in an existing game
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	void playRoundOfTheGame() throws Exception {
		
	}
		
	
	//TODO void is temporary until the class has been created
	//TODO Throw a custom checked exception
	// PUT method to reset the current game. We will reuse the ID, so no need to DELETE
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	void restartGame() throws Exception {
		
	}
}
