package com.jamielafferty.RPS.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;

import com.jamielafferty.RPS.core.RPSCoreEngine;
import com.jamielafferty.RPS.core.RPSGame;
import com.jamielafferty.RPS.players.PlayerFixedImpl;
import com.jamielafferty.RPS.players.PlayerRandomImpl;

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
	
	// Other object declarations
	private RPSController rpsController;
	private List<RPSGame> games;
	private ModelMap modelMap;
	private Exception gameNotFoundException;
	
	// Static integers for a test cases
	private static Integer GAME_ID_7 = 7;
	private static Integer GAME_ID_9 = 9;
	
	// Instantiate anything before testing
	@Before
	public void setup() throws Exception {		
		rpsController = new RPSController(coreEngine);
		
		games = new ArrayList<>();
		games.add(gameTestReturn);
		
		modelMap = new ModelMap();
		
		gameNotFoundException = new Exception("Game not found");
		
		Mockito.when(coreEngine.createNewGame()).thenReturn(GAME_ID_9);
		Mockito.when(coreEngine.getGame(Mockito.eq(GAME_ID_7))).thenThrow(gameNotFoundException);
		Mockito.when(coreEngine.getGame(Mockito.eq(GAME_ID_9))).thenReturn(gameTestReturn);
		Mockito.when(gameTestReturn.getId()).thenReturn(GAME_ID_9);
		Mockito.when(coreEngine.getAllGames()).thenReturn(games);
		
	}
	
	// Load the main page (root of website - /)
	@Test
	@SuppressWarnings("unchecked")
	public void should_loadRootFunction() {		
		// Call the main load function
		String returnString = rpsController.load(modelMap);
		
		// Ensure we return "index" - which Spring will internally resolve to "index.jsp"
		assertEquals("index", returnString);
		List<RPSGame> modelMapGames = (List<RPSGame>) modelMap.get("gamesList");
		assertNotNull(modelMapGames);
		assertEquals(gameTestReturn, modelMapGames.get(0));
	}
	
	// Can we create a new game?
	@Test
	public void should_createNewGame() throws Exception {
		// Create a new game
		rpsController.createNewGame();
		// Verify the coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).createNewGame();
	}
	
	// Can we get an existing game?
	@Test
	public void should_getGame_when_givenValidGameID() throws Exception {
		// First get the game (return controlled by Mockito statement in setup)
		HttpStatus returnStatus = rpsController.getGame(GAME_ID_9, modelMap);
		RPSGame game = (RPSGame) modelMap.get("selectedGame");
		// Assert the http return status and required things exist in the modelmap
		assertEquals(HttpStatus.OK, returnStatus);
		assertNotNull(game);
		// Verify the coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(Mockito.eq(GAME_ID_9));
		// Assert we have the correct game
		assertEquals( Integer.valueOf(GAME_ID_9), Integer.valueOf(game.getId()) );
	}
	
	// We can't get a game that does not exist
	@Test
	public void should_notGetGame_when_givenInvalidGameID() throws Exception {
		HttpStatus returnStatus = rpsController.getGame(GAME_ID_7, modelMap);
		RPSGame game = (RPSGame) modelMap.get("selectedGame");
		// Assert the http response status and modelMap does not contain the selectedGame
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, returnStatus);
		assertNull(game);
		// Verify coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_7);
	}
	
	// Can we play a round in a game?
	@Test
	public void should_playRound_when_givenValidGameID() throws Exception {
		// Play a round of this game - Outcomes are controlled by mocked players in setup
		rpsController.playRoundOfTheGame(GAME_ID_9);
		
		// Verify coreEngine function was called
		Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_9);
	}
	
	// We should NOT play a game that does not exist
	@Test
	public void should_notPlayRound_when_givenInvalidGameID() throws Exception {
		HttpStatus returnStatus = rpsController.playRoundOfTheGame(GAME_ID_7);
		RPSGame game = (RPSGame) modelMap.get("selectedGame");
		// Assert the http response status and modelMap does not contain the selectedGame
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, returnStatus);
		assertNull(game);
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
			rpsController.restartGame(GAME_ID_9);
		} catch (Exception ex) {
			// Catch the exception we expect to be thrown and assert
			assertEquals("Game not found", ex.getMessage());
			assertEquals(Exception.class, ex.getClass());
			// Verify coreEngine function was called
			Mockito.verify(coreEngine, Mockito.times(1)).getGame(GAME_ID_7);
		}
	}
}
