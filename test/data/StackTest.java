package data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StackTest {

	private Stack testStack;
	
	@Before
	public void setUp() throws Exception {
		testStack = new Stack();
	}

	@After
	public void tearDown() throws Exception {		
		testStack = null;
	}
	
	@Test
	public void testNullHandling() {
				
		assertEquals(0, testStack.size());
		
		// pushing null does nothing
		testStack.push(null);
		assertEquals(0, testStack.size());
		
		// popping an empty stack gives null
		assertEquals(null, testStack.pop());
	}
	
	@Test
	// pushing an item should increase the size. Verify contents via Stack.GetContents()
	public void testPush() {
		
		// push a single item - String
		testStack.push("Hello World");
		
		// size should now be 1
		assertEquals(1, testStack.size());
		
		// check the contents of the stack are as we expect - the one item we just pushed
		Object contents1[] = testStack.getContents();
		
		assertEquals(1, contents1.length);
		assertEquals("Hello World", contents1[0]);
	}
	
//	@Test
//	public void testPop() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetContents() {
//		fail("Not yet implemented");
//	}

}
