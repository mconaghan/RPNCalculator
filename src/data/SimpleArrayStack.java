package data;

import interfaces.IStack;

/**
 * A simple array-based implementation of a stack: a data structure onto which data can be pushed or popped, both in FIFO basis.
 *
 */
public class SimpleArrayStack<T> implements IStack<T> {

	private final static int INITIAL_STACK_SIZE = 10;
	
	// Implement the stack as an array
	private T[] objects;
	private int size;
	
	@SuppressWarnings("unchecked")
	public SimpleArrayStack() {
		objects = (T[])(new Object[INITIAL_STACK_SIZE]);
		size    = 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void push(Object item) {

		// ignore a null input
		if (item == null) {
			return;
		} else {
			
			// check if we need to expand the array we're using to store
			if (objects.length <= size) {
				Object[] newObjects = new Object[size * 2];
				
				for (int counter = 0; counter < size; counter++) {
					newObjects[counter] = objects[counter];
				}
				
				objects = (T[])newObjects;
			} 
			
			objects[size] = (T)item;
			size++;			
				
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T pop() {

		if (size == 0) {
			return null;
		} else {
			Object value = objects[size-1];
			
			objects[size-1] = null;
			size--;
			
			return (T)value;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] getContentsAsArray() {
		// create a copy of the array, of the exact size needed.
		
		Object[] contents = new Object[size];
		
		for (int counter = 0; counter < size; counter++) {
			contents[counter] = objects[counter];
		}
				
		return (T[])contents;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void empty() {
		
		int counter = 0;
		
		while (counter < size) {
			objects[counter] = null;
			counter++;
		}
		
		size = 0;
	}

}
