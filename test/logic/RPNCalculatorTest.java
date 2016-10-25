package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.SimpleArrayStack;
import interfaces.IStack;

public class RPNCalculatorTest {

	private RPNCalculator testRpnCalculator;
	private List<String> inputList;
	private IStack<String> outputStack;
	
	@Before
	public void setUp() throws Exception {
		outputStack       = new SimpleArrayStack<String>();
		testRpnCalculator = new RPNCalculator(outputStack);
		inputList         = new ArrayList<String>(10);
	}

	@After
	public void tearDown() throws Exception {
		testRpnCalculator = null;
		inputList         = null;
	}
	
	@Test
	// example 1 from spec
	public void testExample1() {
		
		// set up input stack
		inputList.add("5");
		inputList.add("2");
		
		testRpnCalculator.process(inputList);
	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(2, outputStackArray.length);
		assertEquals("5", outputStackArray[0]);
		assertEquals("2", outputStackArray[1]);
	}

	@Test
	// example 2 from spec
	public void testExample2() {
		
		// part 1
		inputList.add("2");
		inputList.add("sqrt");
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		// Note extra precision from what example sshows since spec says we store to 15dps on stack and display to 10dps (or less)
		assertEquals("1.4142135623730951", outputStackArray[0]);
		
		// part 2
		inputList.add("clear");
		inputList.add("9");
		inputList.add("sqrt");
		
		testRpnCalculator.process(inputList);
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		
		// sample output says it should be '3' instead of '3.0', but data presentation is handled elsewhere
		//TODO remember to test this, that output removes the erroneous '.0'
		
		assertEquals("3.0", outputStackArray[0]);
	}
	
	@Test
	public void testExample3() {
		
		// part 1
		inputList.add("5");
		inputList.add("2");
		inputList.add("-");
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		// Note extra precision from what example shows since spec says we store to 15dps on stack and display to 10dps (or less)
		assertEquals("3.0", outputStackArray[0]);
		
		// part 2
		inputList.clear();
		inputList.add("3");
		inputList.add("-");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("0.0", outputStackArray[0]);
		
		// part 3 
		inputList.add("clear");		
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(0, outputStackArray.length);
	}
	
	@Test
	public void testExample4() {
		
		// part 1
		inputList.add("5");
		inputList.add("4");
		inputList.add("3");
		inputList.add("2");
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(4, outputStackArray.length);
				
		// part 2
		inputList.clear();
		inputList.add("undo");
		inputList.add("undo");
		inputList.add("*");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("20.0", outputStackArray[0]);
		
		// part 3 
		inputList.clear();
		inputList.add("5");
		inputList.add("*");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("100.0", outputStackArray[0]);
		
		// part 4
		inputList.clear();
		inputList.add("undo");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();

		// The spec says that we should expect a single item on the stack - 20, however I think this is wrong...
		// The undo will undo the '*', which poped the 20 and the 5 and pushed 100. Undogin just the '*'will leave
		// the 20 and the 5, where as the spec suggests only the 20 should be on the stack - that would require 2x 'undo'
		// The alternative is hat undo should undo the entire list of input strings, but in the first part of the example
		// 2 undos are done and it only undoes the last two operations, not the last two lines of input
		
		assertEquals(2,      outputStackArray.length);
		assertEquals("20.0", outputStackArray[0]);
		assertEquals("5",    outputStackArray[1]);
	}
	
	@Test
	public void testExample5() {
		
		// part 1
		inputList.add("7");
		inputList.add("12");
		inputList.add("2");
		inputList.add("/");
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(2,     outputStackArray.length);
		assertEquals("7",   outputStackArray[0]);
		assertEquals("6.0", outputStackArray[1]);
				
		// part 2
		inputList.clear();
		inputList.add("*");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("42.0", outputStackArray[0]);
		
		// part 3 
		inputList.clear();
		inputList.add("4");
		inputList.add("/");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("10.5", outputStackArray[0]);
	}
		
	@Test
	public void testExample6() {
		
		// part 1
		inputList.add("1");
		inputList.add("2");
		inputList.add("3");
		inputList.add("4");
		inputList.add("5");
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(5, outputStackArray.length);
		
		// part 2
		inputList.clear();
		inputList.add("*");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(4,      outputStackArray.length);
		assertEquals("20.0", outputStackArray[3]);
		assertEquals("3",    outputStackArray[2]);
		
		// part 3 
		inputList.clear();
		inputList.add("clear");
		inputList.add("3");
		inputList.add("4");
		inputList.add("-");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("-1.0", outputStackArray[0]);
	}
	
	@Test
	public void testExample7() {
		
		// part 1
		inputList.add("1");
		inputList.add("2");
		inputList.add("3");
		inputList.add("4");
		inputList.add("5");
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(5, outputStackArray.length);
		
		// part 2
		inputList.clear();
		inputList.add("*");
		inputList.add("*");
		inputList.add("*");
		inputList.add("*");
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		// note that spec shows the state of the stack after the first multiply, 
		// looks like a mistake in the spec since 4x multiplies are in a single command line so only the end result should be shown?
		
		assertEquals(1,       outputStackArray.length);
		assertEquals("120.0", outputStackArray[0]);
		
	}
	
}