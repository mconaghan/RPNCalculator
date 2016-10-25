package interfaces;

import java.util.List;

import exceptions.InsufficientParametersException;

public interface IRPNCalculator {

	/**
	 * Process a list of input Strings, writting the output onto the stack.
	 */
	public void process(List<String> inputArgument) throws InsufficientParametersException;
	
	public IStack<String> getStack();
	
}
