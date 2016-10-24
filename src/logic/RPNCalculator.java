package logic;

import java.util.Arrays;
import java.util.List;

import data.SimpleArrayStack;
import interfaces.IRPNCalculator;
import interfaces.IStack;

public class RPNCalculator implements IRPNCalculator {	
		
	private IStack<String> stack;
	
	/**
	 * Create an RPN calculator.
	 * 
	 * @param suppliedStack Optional - if null a SimpleArrayStack will be created and used.
	 */
	public RPNCalculator(IStack<String> suppliedStack) {
		
		if (suppliedStack == null) {
			stack = new SimpleArrayStack<String>();
		} else {
			stack = suppliedStack;
		}
	}

	@Override
	public IStack<String> getStack() {
		return stack;
	}
	
	@Override
	public void process(List<String> inputList) {
						
		for (String nextItem : inputList) {
						
			Double nextObjectAsNumber = getValueAsNumber(nextItem);		
					
			if (nextObjectAsNumber != null) {
				// we got a number, put it on the stack
				stack.push(nextItem);
			} else if (!Operator.isValidOperator(nextItem)) {
				
				String[] validOperators = new String[Operator.values().length];
				
				int counter = 0;
				for (Operator operator : Operator.values()) {
					validOperators[counter] = operator.toString();
					counter++;
				}
				
				System.err.println("Unexpected argument '" + nextItem + 
						           "' - it is not a number or a valid operator. Valid operators are " + 
						           Arrays.toString(validOperators));
			} else {
				// must have a valid operator, so process it
				processOperator(Operator.getOperator(nextItem));
			}			
		}		

	}	
	
	private void processOperator(Operator operator) {
		
		String numberOneString;
		String numberTwoString;
		Double numberOne;
		Double numberTwo;
		
		switch (operator) {
		
		case SQRT:
			// square root - pop off a single number from the stack, calculate the square root and push it on the stack
			numberOneString = stack.pop();
			numberOne = getValueAsNumber(numberOneString);
			double squareRoot = Math.sqrt(numberOne);
			
			stack.push(Double.toString(squareRoot));
			break;
			
		case CLEAR:
			stack.empty();
			break;
			
		case MINUS:
			numberOneString = stack.pop();
			numberOne = getValueAsNumber(numberOneString);
			numberTwoString = stack.pop();
			numberTwo = getValueAsNumber(numberTwoString);
			
			Double result = numberTwo - numberOne;
			stack.push(Double.toString(result));
			break;
			
		default:
			throw new RuntimeException("Coding error - unhandled operator in RPNCalculator.processOPerator:" + operator);
		}
	}
	
	// 'protected' instead of 'private' so that unit tests can access, 
	// otherwise could use reflection to change modifier (and make this private as it ideally should be)
	protected static Double getValueAsNumber(String value) {
		
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
