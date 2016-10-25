package io;

import java.text.DecimalFormat;

import interfaces.IStack;
import interfaces.IStackStringOutputFormatter;
import utils.NumberUtilities;

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
	public String[] formatStack(IStack<String> stack) {

		String[] output = new String[stack.size()];
		
		int counter = 0;
		while (stack.size() > 0) {
		
			String nextItem = stack.pop();
			Double nextItemAsDouble = NumberUtilities.getValueAsNumber(nextItem);
			
			if (nextItemAsDouble == null) {
				output[counter] = nextItem;
			} else {
				output[counter] = DECIMAL_FORMATTER.format(nextItemAsDouble);
			}
			
			counter++;
		}
		
		return output;
	}

}
