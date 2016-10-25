package io;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.SimpleArrayStack;
import interfaces.IStack;

public class StringTenDecPointsOutputFormatterTest {

	private StringTenDecPointsOutputFormatter testFormatter;
	private IStack<String> testStack;
	
	@Before
	public void setUp() throws Exception {
		
		testFormatter = new StringTenDecPointsOutputFormatter();
		testStack     = new SimpleArrayStack<>();
	}

	@After
	public void tearDown() throws Exception {
		testFormatter = null;
		testStack     = null;
	}

	@Test
	public void test() {
		
		// nothing in, nothing out
		String[] output1 = testFormatter.formatStack(testStack);		
		assertEquals(0, output1.length);
		
		// single string in, single string out
		testStack.empty();
		testStack.push("abc123");
		String[] output2 = testFormatter.formatStack(testStack);		
		assertEquals(1, output2.length);
		assertEquals("abc123", output2[0]);
		
		// single number in, single number out
		testStack.empty();
		testStack.push("123");
		String[] output3 = testFormatter.formatStack(testStack);		
		assertEquals(1, output3.length);
		assertEquals("123", output3[0]);
		
		// mixed
		testStack.empty();
		
		testStack.push("abc123");
		testStack.push("123");
		
		String[] output4 = testFormatter.formatStack(testStack);		
		assertEquals(2,        output4.length);
		assertEquals("123",    output4[0]);
		assertEquals("abc123", output4[1]);
		
		// a number with > 10 dps
		testStack.empty();
		testStack.push("0.123456789123456789");
		String[] output5 = testFormatter.formatStack(testStack);		
		assertEquals(1, output5.length);
		assertEquals("0.1234567891", output5[0]);
		
		// a number with some dps but not 10
		testStack.empty();
		testStack.push("1.2345");
		String[] output6 = testFormatter.formatStack(testStack);		
		assertEquals(1, output6.length);
		assertEquals("1.2345", output6[0]);
	}

}
