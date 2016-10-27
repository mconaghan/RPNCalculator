package logic;

import java.util.Arrays;
import java.util.List;

import data.CalculatorOperation;
import data.CalculatorOperator;
import data.SimpleArrayStack;
import data.StackOperation;
import data.StackOperationType;
import exceptions.InsufficientParametersException;
import interfaces.IRPNCalculator;
import interfaces.IStack;
import utils.NumberUtilities;

public class RPNCalculator implements IRPNCalculator {	
		
	// operating stack for the caulculator, where values/operators are stored
	private IStack<String> stack;
	
	// a stack used to record previous operations, so that they can be undone in an LIFO order
	private IStack<CalculatorOperation> operationHistory;
	
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
		
		operationHistory = new SimpleArrayStack<CalculatorOperation>();
	}

	@Override
	public IStack<String> getStack() {
		return stack;
	}
	
	@Override
	public void process(List<String> inputList) throws InsufficientParametersException {
						
		boolean recordOperation = true;
		
		int inputPosition = 0;
		for (String nextItem : inputList) {
			
			CalculatorOperation thisOperation = new CalculatorOperation();
						
			Double nextObjectAsNumber = NumberUtilities.getValueAsNumber(nextItem);		
					
			if (nextObjectAsNumber != null) {
				// we got a number, put it on the stack
				pushStringToStack(nextItem, thisOperation);
				
			} else if (!CalculatorOperator.isValidOperator(nextItem)) {
				
				// not a valid operator, and we already know its not a number, so print an error
				
				// construct a string with all the valid operators to make a nicer error message
				String[] validOperators = new String[CalculatorOperator.values().length];
				int counter = 0;
				for (CalculatorOperator operator : CalculatorOperator.values()) {
					validOperators[counter] = operator.toString();
					counter++;
				}
				
				System.err.println("Unexpected argument '" + nextItem + 
						           "' - it is not a number or a valid operator. Valid operators are " + 
						           Arrays.toString(validOperators));
			} else {
				
				// must have a valid operator, so process it
				CalculatorOperator operator = CalculatorOperator.getOperator(nextItem);
				try {
					processOperator(operator, thisOperation);
				} catch (InsufficientParametersException e) {
					e.setOperator(operator);
					e.setOperatorPosition(inputPosition);
					throw e;
				}				
				
				// Since 'undo undo' should undo the last two operations, we don't record what an 'undo' does
				if (CalculatorOperator.UNDO.equals(operator)) {
					recordOperation = false;
				}				
				
			}		
			
			if (recordOperation) {
				operationHistory.push(thisOperation);
			}
			
			inputPosition++;
		}		

	}	
	
	private void processOperator(CalculatorOperator operator, CalculatorOperation calcOperation) throws InsufficientParametersException {
		
		Double numberOne;
		Double numberTwo;
		
		switch (operator) {
		
		case SQRT:
			// square root - pop off a single number from the stack, calculate the square root and push it on the stack
			numberOne = popDoubleFromStack(calcOperation);
			
			double squareRoot = Math.sqrt(numberOne);
			
			pushStringToStack(Double.toString(squareRoot), calcOperation);
			break;
			
		case CLEAR:
			
			while (stack.size() > 0) {
				popStringFromStack(calcOperation);
			}
			break;
			
		case MINUS:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
			
			Double result = numberTwo - numberOne;
			pushStringToStack(Double.toString(result), calcOperation);
			break;
		
		case PLUS:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
			
			Double addResult = numberTwo + numberOne;
			pushStringToStack(Double.toString(addResult), calcOperation);
			break;
			
		case UNDO:
			// get the last calculator operation from internal memory
			CalculatorOperation lastCalulatorOperation = operationHistory.pop();
			
			// for each underlying task operation (in the calcualtor operation), undo it
			while (lastCalulatorOperation.areMoreStackOperations()) {
				StackOperation<String> lastStackOperation = lastCalulatorOperation.getLastStackOperation();
				String value = (String) lastStackOperation.getOperationValue();
				StackOperationType type = lastStackOperation.getOperationType();
								
				if (StackOperationType.POP.equals(type)) {
					stack.push(value);
				} else if (StackOperationType.PUSH.equals(type)) {
					stack.pop();
				} else {
					throw new RuntimeException("Coding error in RPNCalculator.processOperator: " + type);
				}
			}
			break;
			
		case MULTIPLY:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
				
			Double multiplyResult = numberTwo * numberOne;
			pushStringToStack(Double.toString(multiplyResult), calcOperation);
				
			break;
			
		case DIVIDE:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
			
			if (Double.valueOf(0).equals(numberOne)) {
				throw new ArithmeticException("Cannot divide by zero");
			}
			
			Double divideResult = numberTwo / numberOne;
			pushStringToStack(Double.toString(divideResult), calcOperation);
			break;
			
		default:
			throw new RuntimeException("Coding error - unhandled operator in RPNCalculator.processOperator:" + operator);
		}
	}
	
	private Double popDoubleFromStack(CalculatorOperation calcOp) throws InsufficientParametersException {
		
		String numberString = stack.pop();
		
		if (numberString == null) {
			throw new InsufficientParametersException();
		}
		
		calcOp.addStackOperation(StackOperationType.POP, numberString);			
		return NumberUtilities.getValueAsNumber(numberString);
			
	}
	
	private String popStringFromStack(CalculatorOperation calcOp) throws InsufficientParametersException {
		
		String valueString = stack.pop();
		
		if (valueString == null) {
			throw new InsufficientParametersException();
		}
		
		calcOp.addStackOperation(StackOperationType.POP, valueString);			
		return valueString;
			
	}
		
	private void pushStringToStack(String value, CalculatorOperation calOp) {
		stack.push(value);
		calOp.addStackOperation(StackOperationType.PUSH, value);	
	}
	
	private Double getSecondNumber(CalculatorOperation calcOper, Double numberOne) throws InsufficientParametersException {
		try {
			return popDoubleFromStack(calcOper);
			
		} catch (InsufficientParametersException e) {
			// the spec implies that if we fail to process an operator which expects 2 params but only has 1 
			//we should leave the first param on the stack, so if we can't get number 2, put number 1 back
			pushStringToStack(Double.toString(numberOne), calcOper);
			
			throw e;
		}			
	}
}
