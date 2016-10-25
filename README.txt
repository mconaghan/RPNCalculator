# RPNCalculator
Command-line based RPN calculator in Java

# How to run
The main / executable class is RPNCalculatorCLUI.java in the src directory.

# How to test
All unit tests are under the test directory, and can be run for example via Eclipse: right-click on test -> Run As -> JUnit Test

# Other comments
Since this was written as a technical test I decided to implement my own stack, rather than use one of the implementations that some with Java. Not because I think mine is better, just so that there was more code to write and test! Similarly I've tried to use as many features of Java as possible again since this is a technical test - generics, Exception handling...

I've designed the solution to be very modular and loosely-coupled, for example the requirement to display the contents of the stack rounded to 10 decimal places was achieved by having a formatter interface (IStackStringOutputFormatter) which de-couples the display requirement from the stack or calculator logic, so that if someone else came along and wanted 15dps it would simply require a new formatter class, or a small change the the existing one to allow the number of dps to be configurable. 

Another example of this is how I have coded the requirement to display an error message including the position of the offending operator in the original string. I think it is cleaner to have the core RPN calculator logic operate on a list of strings, instead of the raw input string - meaning that the tokenising logic is separate from the RPN processing logic. This meant more effort to be able to tie back an operator with insufficient parameters to the position in the original input string. The RPNCalculator class will throw an InsuffientParametersException which will contain the operator that caused the problem, and which token (index) it was. This information can be combined  with the original tokeniser, which can give the position in the original input string for any token token (index).

As noted in my unit tests I believe that the 4th part of Example 4 is wrong: it shows that after an undo operation is processed teh stack should contain a single item - the number 20, however I believe that the undo should only undo the multiplication (20 * 5) and therefore leave both the 20 an the 5 on the stack. A more minor point is that the output-ed value for square root of two differs - the spec shows 1.4142135623 whereas this code will give 1.4142135624, which is the correct rounding (full result to 11dps is 1.41421356237, and I don't see a requirement to always round down). 
