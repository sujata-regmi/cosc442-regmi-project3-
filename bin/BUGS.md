For every source-code fault that you discover and correct, document it in a file named BUGS.md in the root of your repository. For each fault, include:
•	The observed failure: 
A java.lang.ArrayIndexOutOfBoundsException occurred at line 64 of `VendingMachine.java` during initialization, crashing the program before test execution could complete.
•	The test that exposed it: testGetItem() and all other tests that instantiate a new `VendingMachine` object
•	The source-code fault that caused it: error inside the default constructor's `for` loop condition
•	How you diagnosed the fault: used debug tool to trace backwards 
The correction you made: updated the loop operator from `<=` to `<`  on line, Line 64 Vending machine
•	The observed failure: Compilation error when attempting to build the project and compile the test files.
•	The test that exposed it: Initial compilation before unit tests could be run
•	The source-code fault that caused it: use of unsupported named argument labels in method calls
•	How you diagnosed the fault**: reviewed compiler error messages pointing to unexpected token syntax during the build
•	The correction you made: removed the named argument labels and passed the parameters positionally in the correct method signature order
