package logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorTest {

	// test some of the internal static utility functions
	
	@Test	
	public void testStaticHelperFunctions() {
		
		assertFalse(Operator.isValidOperator("foobar"));
		assertEquals(null, Operator.getOperator("foobar"));
		
		
		assertFalse(Operator.isValidOperator(null));
		assertEquals(null, Operator.getOperator(null));
		
		assertTrue(Operator.isValidOperator("sqrt"));
		assertEquals(Operator.SQRT, Operator.getOperator("sqrt"));
	}
}
