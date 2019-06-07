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
	public void should_notPlayGame_when_givenInvalidGameID() {
		fail("Not implemented!");
		try {
			rpsController.playGame();
			fail("If we reach here, we should not have!");
		} catch (Exception ex) {
			// Catch the exception we expect to be thrown and assert
			assertEquals("", ex.getMessage());
			assertEquals("", ex.getClass());
		}
	}
	
	// Can we play a round in a game?
	@Test
	public void should_playRound_when_givenValidGameID() throws Exception {
		rpsController.playRoundOfTheGame();
		fail("Not implemented!");
	}
	
	// We should NOT play a game that does not exist
	@Test
	public void should_notPlayRound_when_givenInvalidGameID() {
		fail("Not implemented!");
		try{
			rpsController.playRoundOfTheGame();
			fail("If we reach here, we should not have!");
		} catch (Exception ex) {
			// Catch the exception we expect to be thrown and assert
			assertEquals("", ex.getMessage());
			assertEquals("", ex.getClass());
		}
	}
	
	// We should be able to restart an existing game
	@Test
	public void should_restartGame_when_givenValidGameID() throws Exception {
		rpsController.restartGame();
		fail("Not implemented!");
	}
	
	// We should NOT be able to restart a game that does not exist
	@Test
	public void should_notRestartGame_when_givenInvalidGameID() {
		fail("Not implemented!");
		try {
			rpsController.restartGame();
		} catch (Exception ex) {
			// Catch the exception we expect to be thrown and assert
			assertEquals("", ex.getMessage());
			assertEquals("", ex.getClass());
		}
	}
}
