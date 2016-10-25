package io;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WhitespaceStringTokeniserTest {

	private WhitespaceStringTokeniser testTokeniser;
	private List<String> tokens;
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
		testTokeniser = null;
	}

	@Test
	// example 1 from the spec
	public void testExample1() {
		
		testTokeniser = new WhitespaceStringTokeniser("5 2");
		
		tokens = testTokeniser.getTokens();
		
		assertEquals(2, tokens.size());
		
		assertEquals("5", tokens.get(0));
		assertEquals("2", tokens.get(1));
		
		assertEquals(1, testTokeniser.getPositionForTokenAtIndex(0));
		assertEquals(3, testTokeniser.getPositionForTokenAtIndex(1));
	}
	
	@Test
	// example 8 from the spec
	public void testExample8() {
		
		testTokeniser = new WhitespaceStringTokeniser("1 2 3 * 5 + * * 6 5");
		
		tokens = testTokeniser.getTokens();
		
		assertEquals(10, tokens.size());
		
		assertEquals(15, testTokeniser.getPositionForTokenAtIndex(7));
	}

}
