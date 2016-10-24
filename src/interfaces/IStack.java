package interfaces;


/**
 * A stack data structure which stores Strings, where data is 'push'ed on and 'pop'ed off; first in first out.
 * 
 * Example if you push 'abc', push '123', pop -> '123', push 'def', pop -> 'def', pop -> 'abc'.
 */
public interface IStack <T> {
	
	/**
	 * Add an item to the top of the stack.
	 */
	public void push(T item);
	
	/**
	 * Remove the top (most recently added) item from the stack
	 */
	public T pop();
		
	/**
	 * Return the contents of the stack as an array without modifying/removing.
	 */
	public T[] getContentsAsArray();
	
	/**
	 * Return the number of items on the stack (without modifying/removing).
	 */
	public int size();
	
	/**
	 * Remove all content; leave the stack empty
	 */
	public void empty();

}
