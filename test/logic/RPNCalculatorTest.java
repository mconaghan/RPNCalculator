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

	@Test
	// example 2 from spec
	public void testExample2() {
		
		// part 1
		testStack.push("2");
		testStack.push("sqrt");
		
		IStack outputStack        = testRpnCalculator.process(testStack);	
		Object[] outputStackArray = outputStack.getContents();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("1.4142135623", outputStackArray[0]);
		
		// part 2
		testStack.push("clear");
		testStack.push("9");
		testStack.push("sqrt");
		
		outputStack      = testRpnCalculator.process(testStack);
		outputStackArray = outputStack.getContents();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("3", outputStackArray[0]);
	}
	
}
