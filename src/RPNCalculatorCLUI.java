import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import data.RPNCalculatorParameter;
import data.SimpleArrayStack;
import exceptions.InsufficientParametersException;
import interfaces.IInputListener;
import interfaces.IRPNCalculator;
import interfaces.IStack;
import interfaces.IStackOutputFormatter;
import interfaces.IStringTokeniser;
import io.StandardInListener;
import io.String10DPStackOutputFormatter;
import io.WhitespaceStringTokeniser;
import logic.RPNCalculator;

/**
 * A command-line RPN Calculator, which:
 * - reads data from standard in (stdin)
 * - processes data when the user hits enter
 * - outputs the contents of the stack after each time data is processed.
 *
 */
public class RPNCalculatorCLUI {
	
	private static IStack<Double>        stack;
	private static IRPNCalculator        calculator;
	private static IStackOutputFormatter outputFormater;
	private static IStringTokeniser      inputParser;
	private static IInputListener        inputListener;

	public static void main(String[] args) throws IOException {
		
		// Create the objects we need
		stack          = new SimpleArrayStack<Double>();
		calculator     = new RPNCalculator(stack);
		outputFormater = new String10DPStackOutputFormatter();
		inputListener  = new StandardInListener();

		String input = inputListener.listenForInput("Welcome to Command-line RPN Calculator. Enter a white-spaced list of numbers and operators then press enter\n>");
        process(input);     
		
        while (true) {
        	input = inputListener.listenForInput("> ");
            process(input); 
        }

	}

	private static void process(String inputLine) throws IOException {
		
		inputParser = new WhitespaceStringTokeniser(inputLine);
		List<RPNCalculatorParameter> tokens = inputParser.getTokens();
		
		try {
			calculator.process(tokens);
		} catch (InsufficientParametersException e) {
			// this means an operator didn't have all the information it needed. We need to figure out what operator
			// caused the problem, then query the tokeniser to get the associated index, then output a message
			int operatorPositionInInputString = inputParser.getPositionForTokenAtIndex(e.getOperatorPosition());
			e.setOperatorPositionInString(operatorPositionInInputString);
			System.err.println(e.getMessage());
			System.err.flush();
		} catch (ArithmeticException e) {
			System.err.println(e.getMessage());
			System.err.flush();
		}
		
		String[] output = outputFormater.formatStack(stack);
		System.out.println("stack: " + Arrays.toString(output));
		
	}	
}