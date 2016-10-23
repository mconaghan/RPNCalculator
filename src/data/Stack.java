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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return size;
	}

}
