package com.jamielafferty.RPS.players;

import static org.junit.Assert.*;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * This test will cover the basic functions of the Player classes
 * 
 * @author Jamie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayersTest {
	@Mock
	PlayerRandomImpl playerRandom;
	PlayerFixedImpl playerFixed = new PlayerFixedImpl();
	
	// Instantiate anything before testing
	@Before
	public void setup() {
		Mockito.when(playerRandom.makeMove()).thenReturn(Moves.PAPER);
	}
	
	@Test
	public void should_getRandomMove_when_usingPlayerRandom() {
		Moves movePlayed1 = playerRandom.makeMove();
		assertEquals(Moves.PAPER, movePlayed1);
	}
	
	@Test
	public void should_alwaysGetRock_when_usingPlayerFixed() {
		Moves movePlayed = playerFixed.makeMove();
		assertEquals(Moves.ROCK, movePlayed);
		
		// Run again to ensure that rock is returned every time
		movePlayed = playerFixed.makeMove();
		assertEquals(Moves.ROCK, movePlayed);
	}
}
