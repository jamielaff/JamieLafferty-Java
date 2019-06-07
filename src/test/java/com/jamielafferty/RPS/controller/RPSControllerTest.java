package com.jamielafferty.RPS.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RPSControllerTest {

	// Instantiate anything before testing
	@Before
	public void setup() {
		
	}
	
	// Can we create a new game?
	@Test
	public void should_createNewGame() {
		fail("Not implemented!");
	}
	
	// Can we play a game created by the current player?
	@Test
	public void should_playGame_when_getPlayerIDGameID() {
		fail("Not implemented!");
	}
	
	// We should NOT play a game that is created/owned by a different user
	@Test
	public void should_notPlayGame_when_getPlayerIDGameIDOfAnotherPlayer() {
		fail("Not implemented!");
	}
	
	// Can we cancel/quit/abort the game early when we decide to?
	@Test
	public void should_cancelGame_when_userChoosesToQuit() {
		fail("Not implemented!");
	}
	
	// We should be able to play one of the valid moves
	@Test
	public void should_playMove_when_givenValidMove() {
		fail("Not implemented!");
	}
	
	// We shouldn't be able to play an invalid or null move
	@Test
	public void should_notPlayMove_when_givenInvalidMove() {
		fail("Not implemented!");
	}
	
	// If we try to cancel a game that does not exist (game finished, cancelled etc) then we
	// should handle this correctly
	@Test
	public void should_notCancel_when_gameDoesNotExist() {
		fail("Not implemented!");
	}
}
