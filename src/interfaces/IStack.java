package interfaces;

import java.util.Set;

/**
 * A stack data structure, where data is 'push'ed on and 'pop'ed off; first in first out.
 * 
 * Example if you push 'abc', push '123', pop -> '123', push 'def', pop -> 'def', pop -> 'abc'.
 */
public interface IStack {
	
	/**
	 * Add an item to the top of the stack.
	 */
	public void push(Object item);
	
	/**
	 * Remove the top (most recently added) item from the stack
	 */
	public Object pop();
	
	
	/**
	 * Return the contents of the stack without modifying/removing.
	 */
	public Set<Object> getContents();
	
	/**
	 * Return the number of items on the stack (without modifying/removing).
	 */
	public int size();

}
