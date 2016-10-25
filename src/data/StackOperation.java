package data;

/**
 * A single operation performed on a stack, e.g. push 123, pop 'ABC'
 */
public class StackOperation<T> {

	// was it a push or a pop??
	private StackOperationType operationType;
	
	// what was the value?
	private Object operationValue;
	
	public StackOperation(StackOperationType type, T value) {
		operationType  = type;
		operationValue = value;		
	}

	public StackOperationType getOperationType() {
		return operationType;
	}

	public Object getOperationValue() {
		return operationValue;
	}
	
}
