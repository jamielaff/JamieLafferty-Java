package com.jamielafferty.RPS.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.jamielafferty.RPS.core.RPSCoreEngine;
import com.jamielafferty.RPS.core.RPSGame;
import com.jamielafferty.RPS.core.RPSRound;
import com.jamielafferty.RPS.players.PlayerFixedImpl;
import com.jamielafferty.RPS.players.PlayerRandomImpl;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;
import com.jamielafferty.RPS.utils.RPSUtils.Results;

/**
 * This class will test the Controller methods only, not any core/game functionality. 
 * Therefore, most of the testing will be verifying coreEngine methods were called. 
 * These method calls are mocked, and tested in {@link com.jamielafferty.RPS.core.RPSCoreEngineTest.class}
 * 
 * @author Jamie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RPSControllerTest {

	// Mock objects
	@Mock
	private RPSCoreEngine coreEngine;
	@Mock
	private PlayerRandomImpl playerOne;
	@Mock
	private PlayerFixedImpl playerTwo;
	@Mock
	private RPSGame gameTestReturn;
	@Mock
	private HttpSession session;
	@Mock
	private Model model;
	
	// Other object declarations
	private RPSController rpsController;
	private RPSRound roundPlayed;
	private List<RPSGame> games;
	private Exception gameNotFoundException;
	
	// Static integers for a test cases
	private static Integer ROUND_ID_1 = 1;
	private static Integer GAME_ID_7 = 7;
	private static Integer GAME_ID_9 = 9;
	
	// Instantiate anything before testing
	@Before
	public void setup() throws Exception {		
		rpsController = new RPSController(coreEngine);
		
		roundPlayed = new RPSRound(ROUND_ID_1, Moves.PAPER, Moves.ROCK, Results.PLAYER_ONE);
		
		games = new ArrayList<>();
		games.add(gameTestReturn);
				
		gameNotFoundException = new Exception("Game not found");
		
		Mockito.when(coreEngine.createNewGame()).thenReturn(GAME_ID_9);
		Mockito.when(coreEngine.getGame(Mockito.eq(GAME_ID_7))).thenThrow(gameNotFoundException);
		Mockito.when(coreEngine.getGame(Mockito.eq(GAME_ID_9))).thenReturn(gameTestReturn);
		Mockito.when(coreEngine.getAllGames()).thenReturn(games);
		
		Mockito.when(gameTestReturn.playRound()).thenReturn(roundPlayed);
	}
	
	// Load the main page (root of website - /)
	@Test
	@SuppressWarnings("unchecked")
	public void should_loadRootFunction() {		
		// Call the main load function
		String returnString = rpsController.load(model, session);
		
		// Ensure we return "index" - which Spring will internally resolve to "index.jsp"
		assertEquals("index", returnString);
	}
	
	// Can we create a new game?
	@Test
	public void should_createNewGame() throws Exception {
		// Get our expected JSON
		String expectedJson = getTestJson();
		// Create a new game 
		String returnJson = rpsController.createNewGame();
		// Verify the coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).createNewGame();
		// Assert we got back the expected JSON
		assertEquals(expectedJson, returnJson);
	}
	
	// Can we get an existing game?
	@Test
	public void should_getGame_when_givenValidGameID() throws Exception {
		// First get the game (return controlled by Mockito statement in setup)
		String returnJsp = rpsController.getGame(GAME_ID_9, model);
		
		// Assert we return game - this translates to game.jsp (page to load)
		assertEquals("game", returnJsp);
		// Verify the coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(Mockito.eq(GAME_ID_9));
	}
	
	// We can't get a game that does not exist
	@Test
	public void should_notGetGame_when_givenInvalidGameID() throws Exception {
		// Create a new game 
		String returnJsp = rpsController.getGame(GAME_ID_7, model);
		
		// Assert we got back a blank JSP page (no valid game)
		assertEquals("", returnJsp);

		// Verify coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_7);
	}
	
	// Can we play a round in a game?
	@Test
	public void should_playRound_when_givenValidGameID() throws Exception {		
		// Play a round of this game - Outcomes are controlled by mocked players in setup
		String returnJsp = rpsController.playRoundOfTheGame(GAME_ID_9, model);
		
		// Assert we return the JSP page name
		assertEquals("round", returnJsp);
		
		// Verify coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_9);
	}
	
	// We should NOT play a game that does not exist
	@Test
	public void should_notPlayRound_when_givenInvalidGameID() throws Exception {
		String returnJsp = rpsController.playRoundOfTheGame(GAME_ID_7, model);
		
		// Assert we got back a blank JSP page (no valid game)
		assertEquals("", returnJsp);
		
		// Verify coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_7);
	}
	
	// We should be able to restart an existing game
	@Test
	public void should_restartGame_when_givenValidGameID() throws Exception {
		rpsController.restartGame(GAME_ID_9);
		
		// Verify coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_9);
	}
	
	// We should NOT be able to restart a game that does not exist
	@Test
	public void should_notRestartGame_when_givenInvalidGameID() throws Exception {
		try {
			rpsController.restartGame(GAME_ID_7);
			fail("If we reach here, we should not have!");
		} catch (Exception ex) {
			// Catch the exception we expect to be thrown and assert
			assertEquals("Game not found", ex.getMessage());
			assertEquals(Exception.class, ex.getClass());
			// Verify coreEngine function was called
			Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_7);
		}
	}
	
	// Check the refresh statistics function works
	@Test
	public void should_refreshStatistics() {
		String returnJsp = rpsController.refreshStatistics(model);
		
		assertEquals("stats", returnJsp);
	}
	
	private String getTestJson() {
		return "{\"gameId\" : 9}";
	}
}
