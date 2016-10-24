package logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Stack;
import interfaces.IStack;

public class RPNCalculatorTest {

	RPNCalculator testRpnCalculator;
	IStack testStack;
	
	@Before
	public void setUp() throws Exception {
		testRpnCalculator = new RPNCalculator();
		testStack = new Stack();
	}

	@After
	public void tearDown() throws Exception {
		testRpnCalculator = null;
		testStack         = null;
	}

	@Test
	// example 1 from spec
	public void testExample1() {
		
		// set up input stack
		testStack.push("5");
		testStack.push("2");
		
		IStack outputStack = testRpnCalculator.process(testStack);
	
		Object[] outputStackArray = outputStack.getContents();
		
		assertEquals(2, outputStackArray.length);
		assertEquals("5", outputStackArray[0]);
		assertEquals("2", outputStackArray[1]);
	}

}
