package data;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {

	@Test
	public void testNullHandling() {
		
		Stack testStack = new Stack();
		
		assertEquals(0, testStack.size());
		
		// pushing null does nothing
		testStack.push(null);
		assertEquals(0, testStack.size());
		
		// popping an empty stack gives null
		assertEquals(null, testStack.pop());
	}
	
	@Test
	public void testPush() {
		fail("Not yet implemented");
	}
	@Test
	public void testPop() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContents() {
		fail("Not yet implemented");
	}

}
