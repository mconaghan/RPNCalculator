package data;

/**
 * A parameter to an IRPNCalculator - either a double or a CalcualtorOperator.
 *
 */
public class RPNCalculatorParameter {
	
	private boolean isOperator;
	private Double number;
	private CalculatorOperator operator;
	
	public RPNCalculatorParameter(Double d) {
		isOperator = false;
		number     = d;
	}
	
	public RPNCalculatorParameter(CalculatorOperator o) {
		isOperator = true;
		operator   = o;
	}

	public boolean isOperator() {
		return isOperator;
	}

	public Double getNumber() {
		return number;
	}
	
	public CalculatorOperator getOperator() {
		return operator;
	}

}
