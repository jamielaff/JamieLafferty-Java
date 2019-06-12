package com.jamielafferty.RPS.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.jamielafferty.RPS.players.PlayerFixedImpl;
import com.jamielafferty.RPS.players.PlayerRandomImpl;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;
import com.jamielafferty.RPS.utils.RPSUtils.Results;

/**
 * This class will cover functionality of the classes: 
 * - {@link com.jamielafferty.RPS.core.RPSCoreEngine.class}
 * - {@link com.jamielafferty.RPS.core.RPSGame.class}
 * - {@link com.jamielafferty.RPS.core.RPSRound.class}
 * 
 * @author Jamie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RPSCoreEngineTest {
	
	private RPSCoreEngine coreEngine;
	
	@Mock
	private PlayerRandomImpl playerRandomImpl;
	
	@Mock
	private PlayerFixedImpl playerFixedImpl;

	// Instantiate anything before testing
	@Before
	public void setup() {
		coreEngine = new RPSCoreEngine();
		Mockito.when(playerFixedImpl.makeMove()).thenCallRealMethod();
	}
	
	// Check we can create new games. We should also check the IDs auto increment correctly
	@Test
	public void should_create2NewGames_when_callingCreateFunctionTwice() {
		// Setup, call function to clear all existing games
		clearAllGames();
		
		// Create a new game, ensure it has ID of 1 and ensure the games list is of size 1
		Integer game1ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(1), game1ID);
		assertEquals(Integer.valueOf(1), Integer.valueOf(coreEngine.getAllGames().size()));
		
		// Create another new game, ensure it has ID of 2 and ensure the games list is now of size 2
		Integer game2ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(2), game2ID);
		assertEquals(Integer.valueOf(2), Integer.valueOf(coreEngine.getAllGames().size()));
	}
	
	// Can we get a game that exists?
	@Test
	public void should_returnExistingGame_when_gameIdExists() throws Exception {
		// Setup, call function to clear all existing games
		clearAllGames();
		// Setup, call function to create 5 games
		create5Games();
		// Ensure we have 5 games available
		assertEquals(Integer.valueOf(5), Integer.valueOf(coreEngine.getAllGames().size()));
		
		// Call the core function to get a game that exists (3)
		RPSGame game = coreEngine.getGame(3);
		// Check the correct game has been returned to us
		assertEquals(Integer.valueOf(3), game.getId());
	}
	
	// Check that if we try to get a game that does not exist, this is handled correctly
	@Test
	public void should_throwException_when_tryingToGetAGameThatDoesNotExist() {
		// Setup, call function to clear all existing games
		clearAllGames();
		// Setup, call function to create 5 games
		create5Games();
		// Ensure we have 5 games available
		assertEquals(Integer.valueOf(5), Integer.valueOf(coreEngine.getAllGames().size()));
		
		try {
			RPSGame game = coreEngine.getGame(6);
			fail("If we reach here, we should not have!");
		} catch (Exception ex) {
			assertEquals("Game not found", ex.getMessage());
			assertEquals(Exception.class, ex.getClass());
		}
	}
	
	// Check if we can play a round of a game where player 1 wins
	@Test
	public void should_playRoundPlayer1Wins() throws Exception {
		Mockito.when(playerRandomImpl.makeMove()).thenReturn(Moves.SCISSORS);
		
		// Setup, call function to clear all existing games
		clearAllGames();
		
		// Create a new game, ensure it has ID of 1 and ensure the games list is of size 1
		Integer game1ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(1), game1ID);
		assertEquals(Integer.valueOf(1), Integer.valueOf(coreEngine.getAllGames().size()));
		
		// Get the game from coreEngine
		RPSGame game = coreEngine.getGame(1);
		// We need to set the players to mocked objects, to control the outcome of the moves (we cannot rely on random)
		game.setPlayers(playerFixedImpl, playerRandomImpl);
		// Play a round
		game.playRound();
		// Get the last played round (in this case, only 1 round)
		RPSRound round = game.getLastRound();
		
		assertEquals(Results.PLAYER_ONE, round.getResult());
		assertEquals(Integer.valueOf(1), game.getRoundsPlayedCount());
		assertEquals(Integer.valueOf(1), game.getPlayerOneWinCount());
		assertEquals(Integer.valueOf(0), game.getPlayerTwoWinCount());
		assertEquals(Integer.valueOf(0), game.getTieCount());
	}
	
	// Check if we can play a round of a game where player 2 wins
	@Test
	public void should_playRoundPlayer2Wins() throws Exception {
		Mockito.when(playerRandomImpl.makeMove()).thenReturn(Moves.PAPER);
		
		// Setup, call function to clear all existing games
		clearAllGames();
		
		// Create a new game, ensure it has ID of 1 and ensure the games list is of size 1
		Integer game1ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(1), game1ID);
		assertEquals(Integer.valueOf(1), Integer.valueOf(coreEngine.getAllGames().size()));
		
		// Get the game from coreEngine
		RPSGame game = coreEngine.getGame(1);
		// We need to set the players to mocked objects, to control the outcome of the moves (we cannot rely on random)
		game.setPlayers(playerFixedImpl, playerRandomImpl);
		// Play a round
		game.playRound();
		// Get the last played round (in this case, only 1 round)
		RPSRound round = game.getLastRound();
		
		assertEquals(Results.PLAYER_TWO, round.getResult());
		assertEquals(Integer.valueOf(1), game.getRoundsPlayedCount());
		assertEquals(Integer.valueOf(0), game.getPlayerOneWinCount());
		assertEquals(Integer.valueOf(1), game.getPlayerTwoWinCount());
		assertEquals(Integer.valueOf(0), game.getTieCount());
	}
	
	// Check if we can play a round of a game where it is a tie
	@Test
	public void should_playRoundTie() throws Exception {
		Mockito.when(playerRandomImpl.makeMove()).thenReturn(Moves.ROCK);
		
		// Setup, call function to clear all existing games
		clearAllGames();
		
		// Create a new game, ensure it has ID of 1 and ensure the games list is of size 1
		Integer game1ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(1), game1ID);
		assertEquals(Integer.valueOf(1), Integer.valueOf(coreEngine.getAllGames().size()));
		
		// Get the game from coreEngine
		RPSGame game = coreEngine.getGame(1);
		// We need to set the players to mocked objects, to control the outcome of the moves (we cannot rely on random)
		game.setPlayers(playerFixedImpl, playerRandomImpl);
		// Play a round
		game.playRound();
		// Get the last played round (in this case, only 1 round)
		RPSRound round = game.getLastRound();
		
		assertEquals(Results.TIE, round.getResult());
		assertEquals(Integer.valueOf(1), game.getRoundsPlayedCount());
		assertEquals(Integer.valueOf(0), game.getPlayerOneWinCount());
		assertEquals(Integer.valueOf(0), game.getPlayerTwoWinCount());
		assertEquals(Integer.valueOf(1), game.getTieCount());
	}
	
	// Check if we try to play a round of a game that doesn't exist, throw an exception
	@Test
	public void should_playRoundThrowException_when_givenInvalidMoveByPlayerOne() {
		// Setup, call function to clear all existing games
		clearAllGames();
		
		// Create a new game, ensure it has ID of 1 and ensure the games list is of size 1
		Integer game1ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(1), game1ID);
		assertEquals(Integer.valueOf(1), Integer.valueOf(coreEngine.getAllGames().size()));
		try {
			// Get the game from coreEngine
			RPSGame game = coreEngine.getGame(1);
			// We need to set the players to mocked objects, to control the outcome of the moves (we cannot rely on random)
			game.setPlayers(playerFixedImpl, playerRandomImpl);
			// Play a round
			game.playRound();
		} catch (Exception ex) {
			assertEquals(IllegalArgumentException.class, ex.getClass());
			assertEquals("Invalid move has been given - PlayerOne: ROCK, PlayerTwo: null", ex.getMessage());
		}
	}
	
	// Check if we can play some rounds, then restart the game properly
	@Test
	public void should_playSomeRoundsAndRestartGame() throws Exception {
		// Setup, call function to clear all existing games
		clearAllGames();
		
		// Create a new game, ensure it has ID of 1 and ensure the games list is of size 1
		Integer game1ID = coreEngine.createNewGame();
		assertEquals(Integer.valueOf(1), game1ID);
		assertEquals(Integer.valueOf(1), Integer.valueOf(coreEngine.getAllGames().size()));
		
		// Get the game from coreEngine
		RPSGame game = coreEngine.getGame(1);
		// Round 1
		Mockito.when(playerRandomImpl.makeMove()).thenReturn(Moves.ROCK);
		game.setPlayers(playerFixedImpl, playerRandomImpl);
		game.playRound();
		
		// Round 2
		Mockito.when(playerRandomImpl.makeMove()).thenReturn(Moves.PAPER);
		game.setPlayers(playerFixedImpl, playerRandomImpl);
		game.playRound();
		
		// Round 3
		Mockito.when(playerRandomImpl.makeMove()).thenReturn(Moves.SCISSORS);
		game.setPlayers(playerFixedImpl, playerRandomImpl);
		game.playRound();
		
		assertEquals(Integer.valueOf(1), game.getPlayerOneWinCount());
		assertEquals(Integer.valueOf(1), game.getPlayerTwoWinCount());
		assertEquals(Integer.valueOf(1), game.getTieCount());
		assertEquals(Integer.valueOf(3), Integer.valueOf(game.getPlayedRounds().size()));
		
		// Restart the game
		game.restartGame();
		// Check the variables have been reset
		assertEquals(Integer.valueOf(0), game.getPlayerOneWinCount());
		assertEquals(Integer.valueOf(0), game.getPlayerTwoWinCount());
		assertEquals(Integer.valueOf(0), game.getTieCount());
		assertEquals(Integer.valueOf(0), Integer.valueOf(game.getPlayedRounds().size()));
	}
	
	// Function to quickly clear all existing games and reset the ID counter to 0
	private void clearAllGames() {
		// Clear any existing games
		coreEngine.clearAllGames();
		// Set the counter to 0
		RPSGame.count.set(0);
	}
	
	// Function to quickly create 5 games
	private void create5Games() {
		clearAllGames();
		// Create 5 games
		coreEngine.createNewGame();
		coreEngine.createNewGame();
		coreEngine.createNewGame();
		coreEngine.createNewGame();
		coreEngine.createNewGame();
	}
}