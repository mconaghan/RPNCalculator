package data;

public enum CalculatorOperator {
	SQRT("sqrt"), CLEAR("clear"), MINUS("-"), UNDO("undo"), MULTIPLY("*"), DIVIDE("/");
	
	private String keyword;
	
	CalculatorOperator(String value) {
		keyword = value;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public String toString() {
		return getKeyword();
	}
	
	public static boolean isValidOperator(String inputOperator) {
		
	    for (CalculatorOperator validOp : CalculatorOperator.values()) {	    
	    	if (validOp.getKeyword().equalsIgnoreCase(inputOperator)) {
	            return true;
	        }
	    }

	    return false;
	}

	public static CalculatorOperator getOperator(String inputOperator) {
		
	    for (CalculatorOperator validOp : CalculatorOperator.values()) {	    
	    	if (validOp.getKeyword().equalsIgnoreCase(inputOperator)) {
	            return validOp;
	        }
	    }

	    return null;
	}
}
