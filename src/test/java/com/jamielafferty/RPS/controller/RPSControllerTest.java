package com.jamielafferty.RPS.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RPSControllerTest {

	RPSController rpsController;
	// Instantiate anything before testing
	@Before
	public void setup() {
		rpsController = new RPSController();
	}
	
	// Can we create a new game?
	@Test
	public void should_createNewGame() {
		rpsController.createNewGame();
		fail("Not implemented!");
	}
	
	// Can we play an existing game?
	@Test
	public void should_playGame_when_givenValidGameID() throws Exception {
		rpsController.playGame();
		fail("Not implemented!");
	}
	
	// We can't play a game that does not exist
	@Test
	public void should_notPlayGame_when_givenInvalidGameID() throws Exception {
		rpsController.playGame();
		fail("Not implemented!");
	}
	
	// Can we play a round in a game?
	@Test
	public void should_playRound_when_givenValidGameID() throws Exception {
		rpsController.playRoundOfTheGame();
		fail("Not implemented!");
	}
	
	// We should NOT play a game that is created/owned by a different user
	@Test
	public void should_notPlayRound_when_givenInvalidGameID() throws Exception {
		rpsController.playRoundOfTheGame();
		fail("Not implemented!");
	}
	
	// We should be able to restart an existing game
	@Test
	public void should_restartGame_when_givenValidGameID() throws Exception {
		rpsController.restartGame();
		fail("Not implemented!");
	}
	
	// We should NOT be able to restart a game that does not exist
	@Test
	public void should_notRestartGame_when_givenInvalidGameID() throws Exception {
		rpsController.restartGame();
		fail("Not implemented!");
	}
}
