package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.CalculatorOperator;
import data.RPNCalculatorParameter;
import data.SimpleArrayStack;
import exceptions.InsufficientParametersException;
import interfaces.IStack;

public class RPNCalculatorTest {

	private RPNCalculator  testRpnCalculator;
	private List<RPNCalculatorParameter> inputList;
	private IStack<Double> outputStack;
	
	@Before
	public void setUp() throws Exception {
		outputStack       = new SimpleArrayStack<Double>();
		testRpnCalculator = new RPNCalculator(outputStack);
		inputList         = new ArrayList<RPNCalculatorParameter>(10);
	}

	@After
	public void tearDown() throws Exception {
		testRpnCalculator = null;
		inputList         = null;
	}
	
	@Test
	// example 1 from spec
	public void testExample1() throws Exception {
		
		// set up input stack
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		
		testRpnCalculator.process(inputList);
	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(2, outputStackArray.length);
		assertEquals(Double.valueOf(5), outputStackArray[0]);
		assertEquals(Double.valueOf(2), outputStackArray[1]);
	}

	@Test
	// example 2 from spec
	public void testExample2() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.SQRT));
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		// Note extra precision from what example shows since spec says we store to 15dps on stack and display to 10dps (or less)
		assertEquals(Double.valueOf("1.4142135623730951"), outputStackArray[0]);
		
		// part 2
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.CLEAR));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(9)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.SQRT));
		
		testRpnCalculator.process(inputList);
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		
		// sample output says it should be '3' instead of '3.0', but data presentation is handled elsewhere (IStackStringOutputFormatter)
		assertEquals(Double.valueOf(3), outputStackArray[0]);
	}
	
	@Test
	public void testExample3() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MINUS));
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		// Note extra precision from what example shows since spec says we store to 15dps on stack and display to 10dps (or less)
		assertEquals(Double.valueOf(3), outputStackArray[0]);
		
		// part 2
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(Double.valueOf(3)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MINUS));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf(0), outputStackArray[0]);
		
		// part 3 
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.CLEAR));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(0, outputStackArray.length);
	}
	
	@Test
	public void testExample4() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(4)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(3)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(4, outputStackArray.length);
				
		// part 2
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.UNDO));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.UNDO));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf(20), outputStackArray[0]);
		
		// part 3 
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf(100), outputStackArray[0]);
		
		// part 4
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.UNDO));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();

		// The spec says that we should expect a single item on the stack - 20, however I think this is wrong...
		// The undo will undo the '*', which poped the 20 and the 5 and pushed 100. Undogin just the '*'will leave
		// the 20 and the 5, where as the spec suggests only the 20 should be on the stack - that would require 2x 'undo'
		// The alternative is hat undo should undo the entire list of input strings, but in the first part of the example
		// 2 undos are done and it only undoes the last two operations, not the last two lines of input
		
		assertEquals(2,                  outputStackArray.length);
		assertEquals(Double.valueOf(20), outputStackArray[0]);
		assertEquals(Double.valueOf(5),  outputStackArray[1]);
	}
	
	@Test
	public void testExample5() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(7)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(12)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.DIVIDE));
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(2,                 outputStackArray.length);
		assertEquals(Double.valueOf(7), outputStackArray[0]);
		assertEquals(Double.valueOf(6), outputStackArray[1]);
				
		// part 2
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf(42), outputStackArray[0]);
		
		// part 3 
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(Double.valueOf(4)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.DIVIDE));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf("10.5"), outputStackArray[0]);
	}
		
	@Test
	public void testExample6() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(1)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(3)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(4)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(5, outputStackArray.length);
		
		// part 2
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(4,      outputStackArray.length);
		assertEquals(Double.valueOf(20), outputStackArray[3]);
		assertEquals(Double.valueOf(3),  outputStackArray[2]);
		
		// part 3 
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.CLEAR));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(3)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(4)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MINUS));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf(-1), outputStackArray[0]);
	}
	
	@Test
	public void testExample7() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(1)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(3)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(4)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		
		testRpnCalculator.process(inputList);	
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(5, outputStackArray.length);
		
		// part 2
		inputList.clear();
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		
		testRpnCalculator.process(inputList);	
		outputStackArray = outputStack.getContentsAsArray();
		
		// note that spec shows the state of the stack after the first multiply, 
		// looks like a mistake in the spec since 4x multiplies are in a single command line so only the end result should be shown?
		
		assertEquals(1,                   outputStackArray.length);
		assertEquals(Double.valueOf(120), outputStackArray[0]);		
	}

	@Test
	public void testExample8() throws Exception {
		
		// part 1
		inputList.add(new RPNCalculatorParameter(Double.valueOf(1)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(2)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(3)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.PLUS));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.MULTIPLY));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(6)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(5)));
		
		try {
			testRpnCalculator.process(inputList);	
			fail("Should have thrown an InsufficientParametersException");
		} catch (InsufficientParametersException e) {
			// expected
			assertEquals(CalculatorOperator.MULTIPLY, e.getOperator());
			assertEquals(7, e.getOperatorPosition());
		}
		
		Object[] outputStackArray = outputStack.getContentsAsArray();
		
		assertEquals(1, outputStackArray.length);
		assertEquals(Double.valueOf(11), outputStackArray[0]);	
				
	}
	
	@Test 
	public void testDivideByZero() throws Exception {
		
		// set up input stack
		inputList.add(new RPNCalculatorParameter(Double.valueOf(0)));
		inputList.add(new RPNCalculatorParameter(Double.valueOf(0)));
		inputList.add(new RPNCalculatorParameter(CalculatorOperator.DIVIDE));
				
		try {
		
			testRpnCalculator.process(inputList);
			fail("Should have thrown an ArithmeticException");
		} catch (ArithmeticException e) {
			// expected
		}
		
			
		Object[] outputStackArray = outputStack.getContentsAsArray();
				
		assertEquals(0, outputStackArray.length);
				
	}
	
}