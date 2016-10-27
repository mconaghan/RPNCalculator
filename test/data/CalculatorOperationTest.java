package data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorOperationTest {

	private CalculatorOperation testCalOp;
	
	@Before
	public void setUp() throws Exception {
		testCalOp = new CalculatorOperation();
	}

	@After
	public void tearDown() throws Exception {
		testCalOp = null;
	}

	@Test
	public void test() {
		
		// nothing has been added, should be no more operations
		assertFalse(testCalOp.areMoreStackOperations());
		
		// add a couple of operations,  simulate a 3 * 2 operation
		testCalOp.addStackOperation(StackOperationType.POP,  Double.valueOf(3));
		testCalOp.addStackOperation(StackOperationType.POP,  Double.valueOf(2));
		testCalOp.addStackOperation(StackOperationType.PUSH, Double.valueOf(6));
		
		// check they come back in the right order
		StackOperation<Double> nextOp;
		
		assertTrue(testCalOp.areMoreStackOperations());
		nextOp = testCalOp.getLastStackOperation();
		assertEquals(StackOperationType.PUSH, nextOp.getOperationType());
		assertEquals(Double.valueOf(6), nextOp.getOperationValue());
		
		assertTrue(testCalOp.areMoreStackOperations());
		nextOp = testCalOp.getLastStackOperation();
		assertEquals(StackOperationType.POP, nextOp.getOperationType());
		assertEquals(Double.valueOf(2), nextOp.getOperationValue());
		
		assertTrue(testCalOp.areMoreStackOperations());
		nextOp = testCalOp.getLastStackOperation();
		assertEquals(StackOperationType.POP, nextOp.getOperationType());
		assertEquals(Double.valueOf(3), nextOp.getOperationValue());
		
		assertFalse(testCalOp.areMoreStackOperations());
	}

}