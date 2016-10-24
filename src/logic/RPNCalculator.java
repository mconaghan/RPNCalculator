package logic;

import java.util.Arrays;
import java.util.List;

import data.Stack;
import interfaces.IRPNCalculator;
import interfaces.IStack;

public class RPNCalculator implements IRPNCalculator {	
		
	// default constructor
	public RPNCalculator() {
		
	}

	@Override
	public IStack process(List<String> inputList) {
		
		IStack outputStack = new Stack();
		
		for (String nextItem : inputList) {
						
			Double nextObjectAsNumber = getValueAsNumber(nextItem);		
					
			if (nextObjectAsNumber != null) {
				// we got a number, put it on the stack
				outputStack.push(nextItem);
			} else if (!Operator.isValidOperator(nextItem)) {
				
				String[] validOperators = new String[Operator.values().length];
				
				int counter = 0;
				for (Operator operator : Operator.values()) {
					validOperators[counter] = operator.toString();
					counter++;
				}
				
				System.err.println("Unexpected argument - it is not a number or a valid operator. Valid operators are " + 
						Arrays.toString(validOperators));
			} else {
				// must have a valid operator, so process it
				processOperator(outputStack, Operator.getOperator(nextItem));
			}			
		}		
		
		return outputStack;
	}	
	
	private void processOperator(IStack stack, Operator operator) {
		
		String numberOneString;
		String numberTwoString;
		Double numberOne;
		Double numbertwo;
		
		switch (operator) {
		
		case SQRT:
			// square root - pop off a single number from the stack, calculate the square root and push it on the stack
			numberOneString = stack.pop();
			numberOne = getValueAsNumber(numberOneString);
			double squareRoot = Math.sqrt(numberOne);
			String squareRootString = Double.toString(squareRoot);
			stack.push(squareRootString);
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
