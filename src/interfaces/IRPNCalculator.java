package interfaces;

import java.util.List;

public interface IRPNCalculator {

	/**
	 * Take list of Strings as the input, process the data and return a stack containing any results.
	 * Optionally a stack can be provided - if so it will be used during the calculations. If the provided
	 * stack is null a new empty stack will be used in the calculations.
	 */
	public IStack process(List<String> inputArgument, IStack stack);
	
}
