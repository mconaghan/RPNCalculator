package interfaces;

import java.util.List;

import data.RPNCalculatorParameter;
import exceptions.InsufficientParametersException;

public interface IRPNCalculator {

	/**
	 * Process a list of input parameters (numbers or operators), writing the output onto the stack.
	 */
	public void process(List<RPNCalculatorParameter> inputArgument) throws InsufficientParametersException;
	
	/**
	 * Get the calcualtor's stack.
	 */
	public IStack<Double> getStack();
	
}
