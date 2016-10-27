package interfaces;

import java.io.IOException;

/**
 * An object that listens for and returns input from the user.
 */
public interface IInputListener {
	
	/**
	 * Display supplied prompt to the user, wait for users input, and return input.
	 */
	public String listenForInput(String prompt) throws IOException;

}
