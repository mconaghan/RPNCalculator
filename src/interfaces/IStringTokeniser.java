package interfaces;

import java.util.List;

import data.RPNCalculatorParameter;

/**
 * An object that can take a string input, tokenise it into a list of strings, and be able to provide
 * on demand the 'position' of a token in the original string.
 *
 */
public interface IStringTokeniser {
	
	public List<RPNCalculatorParameter> getTokens();
	public int getPositionForTokenAtIndex(int index);

}
