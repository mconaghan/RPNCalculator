package utils;

public class NumberUtilities {

	/**
	 * Take a String value and try to parse it as a double, returning null if it cannot be parsed as a double.
	 */
	public static Double getValueAsNumber(String value) {
		
		Double number = null;
		
		try {
			number = Double.valueOf(value);
		} catch (NumberFormatException nfe) {
			// value isn't a number so return null
			number = null;
		}
		
		return number;
	}
	
}
