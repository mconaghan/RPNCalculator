package interfaces;

/**
 * A stack data structure which stores Strings, where data is 'push'ed on and 'pop'ed off; first in first out.
 * 
 * Example if you push 'abc', push '123', pop -> '123', push 'def', pop -> 'def', pop -> 'abc'.
 */
public interface IStack {
	
	/**
	 * Add an item to the top of the stack.
	 */
	public void push(String item);
	
	/**
	 * Remove the top (most recently added) item from the stack
	 */
	public String pop();
	
	
	/**
	 * Return the contents of the stack without modifying/removing.
	 */
	public String[] getContents();
	
	/**
	 * Return the number of items on the stack (without modifying/removing).
	 */
	public int size();

}
