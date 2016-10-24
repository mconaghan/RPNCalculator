package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import interfaces.IStack;

public class RPNCalculatorTest {

	RPNCalculator testRpnCalculator;
	List<String> inputList;
	
	@Before
	public void setUp() throws Exception {
		testRpnCalculator = new RPNCalculator();
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
		
		IStack outputStack = testRpnCalculator.process(inputList);
	
		Object[] outputStackArray = outputStack.getContents();
		
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
		
		IStack outputStack        = testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContents();
		
		assertEquals(1, outputStackArray.length);
		// Note extra precision from what example sshows since spec says we store to 15dps on stack and display to 10dps (or less)
		assertEquals("1.4142135623730951", outputStackArray[0]);
		
		// part 2
		inputList.add("clear");
		inputList.add("9");
		inputList.add("sqrt");
		
		outputStack      = testRpnCalculator.process(inputList);
		outputStackArray = outputStack.getContents();
		
		assertEquals(1, outputStackArray.length);
		assertEquals("3", outputStackArray[0]);
	}
	
}
