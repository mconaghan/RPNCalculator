package data;

import interfaces.IStack;

public class Stack implements IStack {

	private final static int INITIAL_STACK_SIZE = 10;
	
	// Implement the stack as an array
	private Object objects[];
	private int size;
	
	public Stack() {
		objects = new Object[INITIAL_STACK_SIZE];
		size    = 0;
	}
	
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
				
				objects = newObjects;
			} 
			
			objects[size] = item;
			size++;			
				
		}
	}

	@Override
	public Object pop() {

		if (objects.length == 0) {
			return null;
		} else {
			//TODO implement me
			return null;
		}
	}

	@Override
	public Object[] getContents() {
		// create a copy of the array, of the exact size needed.
		
		Object[] contents = new Object[size];
		
		for (int counter = 0; counter < size; counter++) {
			contents[counter] = objects[counter];
		}
				
		return contents;
	}

	@Override
	public int size() {
		return size;
	}

}
