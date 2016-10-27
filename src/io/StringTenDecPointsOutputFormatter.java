package io;

import java.text.DecimalFormat;

import interfaces.IStack;
import interfaces.IStackStringOutputFormatter;

/**
 * A formatter that converts the contents of an IStack as follows:
 * 
 * - If number can be parsed as a double, display to a maximum of 10 decimal places (less if it causes no loss of precision)
 * - Otherwise call toString() on the object
 *
 */
public class StringTenDecPointsOutputFormatter implements IStackStringOutputFormatter {

	private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#.##########");
	
	@Override
	public String[] formatStack(IStack<Double> stack) {

		String[] output = new String[stack.size()];
		
		if (stack.size() == 0) {
			return output;
		}
		
		int counter = 0;
		
		Object[] stackContents =  stack.getContentsAsArray();
		
		for (Object nextItemObject : stackContents) {

			Double nextItemAsDouble = (Double)nextItemObject;
			
			if (nextItemAsDouble == null) {
				output[counter] = nextItemObject.toString();
			} else {
				output[counter] = DECIMAL_FORMATTER.format(nextItemAsDouble);
			}
			
			counter++;
		}
		
		return output;
	}

}
