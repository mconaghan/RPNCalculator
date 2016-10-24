package interfaces;

import java.util.List;

public interface IRPNCalculator {

	/**
	 * Take a stack, process the data and return a stack containing any results.
	 */
	public IStack process(List<String> inputStack);
	
}
