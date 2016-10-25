package exceptions;

import data.CalculatorOperator;

/**
 * Throws when RPNCalculator finds an operator which requires one or more numbers, but there are not enough
 * numbers on the stack.
 *
 */
public class InsufficientParametersException extends Exception {

	// what operator was being processed at the time?
	private CalculatorOperator operator = null;
	
	// what was the position of the operator in the input string?
	private int position = -1;
	
	public InsufficientParametersException() {
    }

	public void setOperator(CalculatorOperator operator) {
		this.operator = operator;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	@Override
	public String getMessage() {
	
		return "operator " + this.operator.getKeyword() + " (position: " + this.position + "): insufficent parameters";
	}

	public CalculatorOperator getOperator() {
		return operator;
	}
}
