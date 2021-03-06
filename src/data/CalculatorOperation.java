package data;

import interfaces.IStack;

/**
 * A single operation performed by an RPNCaulculator. A single operation consists of one or more
 * operations on the calculator's stack, e.g.:
 * 
 * calculator processes input of '1'     ->  push 1 
 * calculator processes input of 'clear' -> pop 1, pop 2
 * calculator processes input of '+'     -> pop 1, pop 2, push 3
 *
 */
public class CalculatorOperation {

	// use a stack to record the underlying stack operation(s), since we'll want to look at the operations in LIFO order.
	private IStack<StackOperation<Double>> stackOperations;
	
	public CalculatorOperation() {
		stackOperations = new SimpleArrayStack<StackOperation<Double>>();
	}
		
	public void addStackOperation(StackOperationType type, Double value) {
		
		StackOperation<Double> stackOperation = new StackOperation<Double>(type, value);
		stackOperations.push(stackOperation);
	}
	
	public boolean areMoreStackOperations() {
		return (stackOperations.size() != 0);
	}
	
	public StackOperation<Double> getLastStackOperation() {
		
		if (areMoreStackOperations()) {
			return stackOperations.pop();
		} else {
			return null;
		}		
	}	
	
}