import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import data.RPNCalculatorParameter;
import data.SimpleArrayStack;
import exceptions.InsufficientParametersException;
import interfaces.IRPNCalculator;
import interfaces.IStack;
import interfaces.IStackOutputFormatter;
import interfaces.IStringTokeniser;
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
	
	private static IStack<Double> stack;
	private static IRPNCalculator calculator;
	private static IStackOutputFormatter outputFormater;
	private static IStringTokeniser inputParser;
	private static BufferedReader console;

	public static void main(String[] args) throws IOException {
		
		// Create the objects we need
		stack          = new SimpleArrayStack<Double>();
		calculator     = new RPNCalculator(stack);
		outputFormater = new String10DPStackOutputFormatter();
		
		// set up stdin as input
		console =  new BufferedReader(new InputStreamReader(System.in));
		
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        doSomeWork("Welcome to Command-line RPN Calculator. Enter a white-spaced list of numbers and operators then press enter\n>");
                        
        while (true) {
        	doSomeWork("> ");
        }

	}

	private static void doSomeWork(String commandLinePrompt) throws IOException {
		
		System.out.println(commandLinePrompt);
		String initialLine = console.readLine();
		
		inputParser = new WhitespaceStringTokeniser(initialLine);
		List<RPNCalculatorParameter> tokens = inputParser.getTokens();

		
		stack.size();
		
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
