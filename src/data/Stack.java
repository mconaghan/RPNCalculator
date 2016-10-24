package data;

import interfaces.IStack;

public class Stack implements IStack {

	private final static int INITIAL_STACK_SIZE = 10;
	
	// Implement the stack as an array
	private String objects[];
	private int size;
	
	public Stack() {
		objects = new String[INITIAL_STACK_SIZE];
		size    = 0;
	}
	
	@Override
	public void push(String item) {

		// ignore a null input
		if (item == null) {
			return;
		} else {
			
			// check if we need to expand the array we're using to store
			if (objects.length <= size) {
				String[] newObjects = new String[size * 2];
				
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
	public String pop() {

		if (size == 0) {
			return null;
		} else {
			String value = objects[size-1];
			
			objects[size-1] = null;
			size--;
			
			return value;
		}
	}

	@Override
	public String[] getContents() {
		// create a copy of the array, of the exact size needed.
		
		String[] contents = new String[size];
		
		for (int counter = 0; counter < size; counter++) {
			contents[counter] = objects[counter];
		}
				
		return contents;
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
