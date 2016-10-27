package io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.CalculatorOperator;
import data.RPNCalculatorParameter;
import interfaces.IStringTokeniser;
import utils.NumberUtilities;

public class WhitespaceStringTokeniser implements IStringTokeniser {

	private List<String> tokens;
	private List<RPNCalculatorParameter> parameters;
	private Map<Integer, Integer> tokenToIndexMap;
	
	public WhitespaceStringTokeniser(String stringToTokenise) {
		
		tokens          = new ArrayList<String>(10);
		parameters      = new ArrayList<RPNCalculatorParameter>();
		tokenToIndexMap = new HashMap<Integer, Integer>();
		
		StringBuilder nextStringToken = new StringBuilder();
		boolean haveStringToken       = false;
				
		int tokenIndex = 0;
		
		for (int inputStringIndex = 0 ; inputStringIndex < stringToTokenise.length(); inputStringIndex ++) {
			
			char nextChar = stringToTokenise.charAt(inputStringIndex);
			
			if (' ' != nextChar) {
				
				// have non-whitespace, append it to current token
				
				if (!haveStringToken) {
					haveStringToken = true;
				}
				nextStringToken.append(nextChar);
				
			} else {
				
				// have whitespace, may need to close off last token
				if (haveStringToken) {
					
					tokens.add(nextStringToken.toString());
					tokenToIndexMap.put(tokenIndex,  inputStringIndex);
					
					tokenIndex++;
					nextStringToken = new StringBuilder();
					haveStringToken = false;
				}				
			}			
		}
		
		if (haveStringToken) {
			
			tokens.add(nextStringToken.toString());
			tokenToIndexMap.put(tokenIndex,  stringToTokenise.length());
		}
		
		convertStringsToParameters();
	}
	
	@Override
	public List<RPNCalculatorParameter> getTokens() {
		return parameters;
	}

	@Override
	public int getPositionForTokenAtIndex(int index) {
		return tokenToIndexMap.get(index);
	}
	
	private void convertStringsToParameters() {
		
		for (String nextItem : tokens) {
		
			Double nextObjectAsNumber = NumberUtilities.getValueAsNumber(nextItem);		
			
			if (nextObjectAsNumber != null) {
				parameters.add(new RPNCalculatorParameter(nextObjectAsNumber));
				
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
				
				parameters.add(new RPNCalculatorParameter(CalculatorOperator.getOperator(nextItem)));
			}
		}			
	}
}
