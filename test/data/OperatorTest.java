package data;

import static org.junit.Assert.*;

import org.junit.Test;

import data.CalculatorOperator;

public class OperatorTest {

	// test some of the internal static utility functions
	
	@Test	
	public void testStaticHelperFunctions() {
		
		assertFalse(CalculatorOperator.isValidOperator("foobar"));
		assertEquals(null, CalculatorOperator.getOperator("foobar"));
		
		
		assertFalse(CalculatorOperator.isValidOperator(null));
		assertEquals(null, CalculatorOperator.getOperator(null));
		
		assertTrue(CalculatorOperator.isValidOperator("sqrt"));
		assertEquals(CalculatorOperator.SQRT, CalculatorOperator.getOperator("sqrt"));
	}
}
