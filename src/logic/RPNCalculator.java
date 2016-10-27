package logic;

import java.util.List;

import data.CalculatorOperation;
import data.CalculatorOperator;
import data.RPNCalculatorParameter;
import data.SimpleArrayStack;
import data.StackOperation;
import data.StackOperationType;
import exceptions.InsufficientParametersException;
import interfaces.IRPNCalculator;
import interfaces.IStack;

public class RPNCalculator implements IRPNCalculator {	
		
	// operating stack for the calculator, where values/operators are stored
	private IStack<Double> stack;
	
	// a stack used to record previous operations, so that they can be undone in an LIFO order
	private IStack<CalculatorOperation> operationHistory;
	
	/**
	 * Create an RPN calculator.
	 * 
	 * @param suppliedStack Optional - if null a SimpleArrayStack will be created and used.
	 */
	public RPNCalculator(IStack<Double> suppliedStack) {
		
		if (suppliedStack == null) {
			stack = new SimpleArrayStack<Double>();
		} else {
			stack = suppliedStack;
		}
		
		operationHistory = new SimpleArrayStack<CalculatorOperation>();
	}

	@Override
	public IStack<Double> getStack() {
		return stack;
	}
	
	@Override
	public void process(List<RPNCalculatorParameter> inputList) throws InsufficientParametersException {
						
		boolean recordOperation = true;
		
		int inputPosition = 0;
		for (RPNCalculatorParameter nextItem : inputList) {
			
			CalculatorOperation thisOperation = new CalculatorOperation();
								
			if (!nextItem.isOperator()) {
				// we got a number, put it on the stack
				pushDoubleToStack(nextItem.getNumber(), thisOperation);
						
			} else {				
				// we have an operator
				
				try {
					processOperator(nextItem.getOperator(), thisOperation);
				} catch (InsufficientParametersException e) {
					e.setOperator(nextItem.getOperator());
					e.setOperatorPosition(inputPosition);
					throw e;
				}				
				
				// Since 'undo undo' should undo the last two operations, we don't record what an 'undo' does
				if (CalculatorOperator.UNDO.equals(nextItem.getOperator())) {
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
			
			pushDoubleToStack(squareRoot, calcOperation);
			break;
			
		case CLEAR:
			
			while (stack.size() > 0) {
				popDoubleFromStack(calcOperation);
			}
			break;
			
		case MINUS:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
			
			Double result = numberTwo - numberOne;
			pushDoubleToStack(result, calcOperation);
			break;
		
		case PLUS:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
			
			Double addResult = numberTwo + numberOne;
			pushDoubleToStack(addResult, calcOperation);
			break;
			
		case UNDO:
			// get the last calculator operation from internal memory
			CalculatorOperation lastCalulatorOperation = operationHistory.pop();
			
			// for each underlying task operation (in the calculator operation), undo it
			while (lastCalulatorOperation.areMoreStackOperations()) {
				StackOperation<Double> lastStackOperation = lastCalulatorOperation.getLastStackOperation();
				Double value = lastStackOperation.getOperationValueAsDouble();
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
			pushDoubleToStack(multiplyResult, calcOperation);
				
			break;
			
		case DIVIDE:
			numberOne = popDoubleFromStack(calcOperation);
			numberTwo = getSecondNumber(calcOperation, numberOne);
			
			if (Double.valueOf(0).equals(numberOne)) {
				throw new ArithmeticException("Cannot divide by zero");
			}
			
			Double divideResult = numberTwo / numberOne;
			pushDoubleToStack(divideResult, calcOperation);
			break;
			
		default:
			throw new RuntimeException("Coding error - unhandled operator in RPNCalculator.processOperator:" + operator);
		}
	}
	
	private Double popDoubleFromStack(CalculatorOperation calcOp) throws InsufficientParametersException {
		
		Double number = stack.pop();
		
		if (number == null) {
			throw new InsufficientParametersException();
		}
		
		calcOp.addStackOperation(StackOperationType.POP, number);			
		return number;
			
	}
		
	private void pushDoubleToStack(Double value, CalculatorOperation calOp) {
		stack.push(value);
		calOp.addStackOperation(StackOperationType.PUSH, value);	
	}
	
	private Double getSecondNumber(CalculatorOperation calcOper, Double numberOne) throws InsufficientParametersException {
		try {
			return popDoubleFromStack(calcOper);
			
		} catch (InsufficientParametersException e) {
			// the spec implies that if we fail to process an operator which expects 2 params but only has 1 
			// we should leave the first param on the stack, so if we can't get number 2, put number 1 back
			pushDoubleToStack(numberOne, calcOper);
			
			throw e;
		}			
	}
}
