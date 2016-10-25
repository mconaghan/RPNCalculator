package io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IStringTokeniser;

public class WhitespaceStringTokeniser implements IStringTokeniser {

	private List<String> tokens;
	private Map<Integer, Integer> tokenToIndexMap;
	
	public WhitespaceStringTokeniser(String stringToTokenise) {
		
		tokens = new ArrayList<String>(10);
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
	}
	
	@Override
	public List<String> getTokens() {
		return tokens;
	}

	@Override
	public int getPositionForTokenAtIndex(int index) {
		return tokenToIndexMap.get(index);
	}

}
