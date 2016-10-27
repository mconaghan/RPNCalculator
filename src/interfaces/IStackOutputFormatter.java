package interfaces;

/**
 * An object that takes the contents of a String IStack and formats it into an array of Strings.
 */
public interface IStackOutputFormatter {
	
	public String[] formatStack(IStack<Double> stack);

}
