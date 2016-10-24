package data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleArrayStackTest {

	private SimpleArrayStack testStack;
	
	@Before
	public void setUp() throws Exception {
		testStack = new SimpleArrayStack();
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
	public void testSinglePushString() {
		
		// push a single item - String
		testStack.push("Hello World");
		
		// size should now be 1
		assertEquals(1, testStack.size());
		
		// check the contents of the stack are as we expect - the one item we just pushed
		Object contents1[] = testStack.getContentsAsArray();
		
		assertEquals(1, contents1.length);
		assertEquals("Hello World", contents1[0]);
	}
	
	@Test
	public void testSinglePushDouble() {

		Double testDouble = new Double(123);
		testStack.push(testDouble.toString());
		assertEquals(1, testStack.size());
		Object contents1[] = testStack.getContentsAsArray();
		assertEquals(1, contents1.length);
		assertEquals(testDouble.toString(), contents1[0]);
	}
	
	@Test
	public void testMultiplePushString() {

		testStack.push("The");
		testStack.push("Quick");
		testStack.push("Brown");
		testStack.push("Fox");
		testStack.push("Jumped");
		testStack.push("Over");
		testStack.push("The");
		testStack.push("Lazy");
		testStack.push("Dog");
		testStack.push("Then");
		testStack.push("The");
		testStack.push("Quick");
		testStack.push("Brown");
		testStack.push("Fox");
		testStack.push("Jumped");
		testStack.push("Over");
		testStack.push("The");
		testStack.push("Lazy");
		testStack.push("Dog");
		
		// size should now be 19
		assertEquals(19, testStack.size());
		
		// check the contents of the stack are as we expect - 19 items we pushed
		Object contents1[] = testStack.getContentsAsArray();
		
		assertEquals(19, contents1.length);
		
		// do a few checks instead of checking all 18
		assertEquals("The",   contents1[0]);
		assertEquals("Dog",   contents1[18]);
		assertEquals("Quick", contents1[11]);
	}
	
	@Test
	public void testPopSingleString() {
		testStack.push("ABC123");
		
		assertEquals("ABC123", testStack.pop());
	}
	
	@Test
	public void testPopSingleDouble() {
		Double d = new Double(1.234);
		
		testStack.push(d.toString());
		
		assertEquals(d.toString(), testStack.pop());
	}
	
	@Test
	public void testPopManyString() {
		testStack.push("The");
		testStack.push("Quick");
		testStack.push("Brown");
		testStack.push("Fox");
		testStack.push("Jumped");
		testStack.push("Over");
		testStack.push("The");
		testStack.push("Lazy");
		testStack.push("Dog");
		
		assertEquals("Dog",    testStack.pop());
		assertEquals("Lazy",   testStack.pop());
		assertEquals("The",    testStack.pop());
		assertEquals("Over",   testStack.pop());
		assertEquals("Jumped", testStack.pop());
		assertEquals("Fox",    testStack.pop());
		assertEquals("Brown",  testStack.pop());
		assertEquals("Quick",  testStack.pop());
		assertEquals("The",    testStack.pop());
		assertEquals(null,     testStack.pop());
	}
	
	@Test
	public void testEmpty() {
		testStack.push("The");
		testStack.push("Quick");
		testStack.push("Brown");
		testStack.push("Fox");
		
		assertEquals(4, testStack.size());
		
		testStack.empty();
		
		assertEquals(0,    testStack.size());
		assertEquals(null, testStack.pop());
	}
	
}
