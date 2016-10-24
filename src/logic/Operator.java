package logic;

public enum Operator {
	SQRT("sqrt"), CLEAR("clear"), MINUS("-");
	
	private String keyword;
	
	Operator(String value) {
		keyword = value;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public String toString() {
		return getKeyword();
	}
	
	protected static boolean isValidOperator(String inputOperator) {
		
	    for (Operator validOp : Operator.values()) {	    
	    	if (validOp.getKeyword().equalsIgnoreCase(inputOperator)) {
	            return true;
	        }
	    }

	    return false;
	}

	protected static Operator getOperator(String inputOperator) {
		
	    for (Operator validOp : Operator.values()) {	    
	    	if (validOp.getKeyword().equalsIgnoreCase(inputOperator)) {
	            return validOp;
	        }
	    }

	    return null;
	}
}
