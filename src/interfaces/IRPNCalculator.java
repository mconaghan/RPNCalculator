package interfaces;

import java.util.List;

public interface IRPNCalculator {

	/**
	 * Process a list of input Strings, writting the output onto the stack.
	 */
	public void process(List<String> inputArgument);
	
	public IStack getStack();
	
}
