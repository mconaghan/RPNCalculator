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
	
	// what was the position of the operator?
	private int operatorPosition = -1;
	
	// what was the position of the operator in the string?
	private int operatorPositionInString = -1;
	
	public InsufficientParametersException() {
    }

	public void setOperator(CalculatorOperator operator) {
		this.operator = operator;
	}

	public void setOperatorPosition(int position) {
		operatorPosition = position;
	}
	
	public void setOperatorPositionInString(int position) {
		operatorPositionInString = position;
	}
	
	public int getOperatorPosition() {
		return operatorPosition;
	}
	
	@Override
	public String getMessage() {
	
		return "operator " + this.operator.getKeyword() + " (position: " + operatorPositionInString + "): insufficent parameters";
	}

	public CalculatorOperator getOperator() {
		return operator;
	}
}
