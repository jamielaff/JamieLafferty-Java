package com.jamielafferty.RPS.players;

import static org.junit.Assert.*;
import com.jamielafferty.RPS.utils.RPSUtils.Moves;

import org.junit.Before;
import org.junit.Test;

public class PlayersTest {
	PlayerFixedImpl playerFixed = new PlayerFixedImpl();
	PlayerRandomImpl playerRandom = new PlayerRandomImpl();
	
	// Instantiate anything before testing
	@Before
	public void setup() {
		
	}
	
	@Test
	public void should_getRandomMove_when_usingPlayerRandom() {
		// Can we test randomness? We have to trust the Java Random class...
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
