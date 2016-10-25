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
		
		testTokeniser = new WhitespaceStringTokeniser();
	}

	@After
	public void tearDown() throws Exception {
		testTokeniser = null;
	}

	@Test
	// example  from the spec
	public void testExample1() {
		tokens = testTokeniser.tokenise("5 2");
		
		assertEquals(2, tokens.size());
		
		assertEquals("5", tokens.get(0));
		assertEquals("2", tokens.get(1));
		
		assertEquals(0, testTokeniser.getPositionForTokenAtIndex(0));
		assertEquals(2, testTokeniser.getPositionForTokenAtIndex(1));
	}

}
