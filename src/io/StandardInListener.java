package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import interfaces.IInputListener;

public class StandardInListener implements IInputListener {

	private BufferedReader console;
	
	public StandardInListener() {

		console = new BufferedReader(new InputStreamReader(System.in));
	
    	if (console == null) {
    		System.err.println("No console.");
    		System.exit(1);
    	}
    }

	@Override
	public String listenForInput(String prompt) throws IOException {
	
		System.out.println(prompt);
		return console.readLine();
	}

}
