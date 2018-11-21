package apps;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    		/** COMPLETE THIS METHOD **/
    	String delim = "1234567890+-/* ()[]";
    	String exp = expr.replaceAll("\\s+", "");
    	StringTokenizer st = new StringTokenizer(exp);
    	scalars = new ArrayList<ScalarSymbol>();
    	arrays = new ArrayList<ArraySymbol>();
    	int index = 0;
    	
    	while(true) {
    		
    		String tok;
    		try {    			
    			tok = st.nextToken(delim);
    		} catch(NoSuchElementException e) { //Will continue to go through tokens until there are no more tokens, at which point the loop will break using the thrown exception  			
    			break;							
    		};
    		
    		index = exp.indexOf(tok, index); //gets the last index of the token
    		index += tok.length() - 1;
    		
    		if ((index == exp.length() - 1) || (exp.charAt(index+1) != '[')) { //a variable will always be scalar if it is the last element or the next element is not a '['
    			
    			ScalarSymbol ss = new ScalarSymbol(tok);					   
    			boolean add = true;
    			for (int i = 0; i < scalars.size(); i++) {
    				
    				if (scalars.get(i).name == tok) {   					
    					add = false;
    				}
    			}
    			if (add) {    				
    				scalars.add(ss);
    			}
    		} else {
    			
    			ArraySymbol as = new ArraySymbol(tok);
    			boolean add = true;
    			for (int i = 0; i < arrays.size(); i++) {
    				
    				if (arrays.get(i).name == tok) {    					
    					add = false;
    				}
    			}
    			if (add) {
    				arrays.add(as);
    			}
    		}
    	}
    }
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * @return Result of evaluation
     */
    public float evaluate() {
    		/** COMPLETE THIS METHOD **/
    	String exp = expr.replaceAll(" ", "");
    	exp = " " + exp + " ";
    	if (scalars != null) {
    		for (int i = 0; i < scalars.size();i++) {
    			int start = 0, end, scalarLength = scalars.get(i).name.length();
    			while(exp.indexOf(scalars.get(i).name, start) != -1) {
    				start = exp.indexOf(scalars.get(i).name, start);
    				end = start + scalarLength - 1;
    				//System.out.println(end);
    				if (!(Character.isLetter(exp.charAt(start-1)) || Character.isLetter(exp.charAt(end+1)))) {
    					String change = exp.substring(start, end+1).replaceAll(scalars.get(i).name, ""+scalars.get(i).value);
    					exp = exp.substring(0, start) + change + exp.substring(end+1, exp.length());
    				}
    				start = end+1;
    			}
    		}
    	}
    	
    	return Float.parseFloat(evaluate(exp)); //evaluating the method
    }
    
    private int[] findArray(String name) {
    	for (int i = 0; i < arrays.size(); i++) {
    		if (arrays.get(i).name.equals(name)) {
    			return arrays.get(i).values;
    		}
    	}
    	return null;
    }
    
    private String evaluate(String exp) {
    	exp = exp.replaceAll(" ", "");
    	exp = " "+exp;
    	if (exp.contains("[")) {
    		int start = exp.indexOf("[");
    		int end = start + 1;
    		for (int count = 0; end < exp.length();end++) {
    			if (count == 0 && exp.charAt(end) == ']') {
    				break;
    			} else if (exp.charAt(end) == '[') {
    				count++;
    				//System.out.println("count is " + count);
    			} else if (exp.charAt(end) == ']') {
    				count--;
    				//System.out.println("count is " + count);
    			}
    		}
    		int temp;
    		
    		for (temp = start-1; Character.isLetter(exp.charAt(temp)); temp--);
    		temp++;
    		
    		String name = exp.substring(temp, start);
    		//System.out.println("array is "+name);
    		int[] arr = findArray(name);
    		String arrayValue = "" + arr[(int)Float.parseFloat(evaluate(exp.substring(start+1, end)))];
    		//System.out.println("Value of " + name + " is " + arrayValue);
    		return evaluate(exp.substring(0, temp) + arrayValue + exp.substring(end+1));
    	} else {
    		if (exp.contains("(")){
    			int start = exp.indexOf("(");
        		int end = start + 1;
        		for (int count = 0; end < exp.length();end++) {
        			if (count == 0 && exp.charAt(end) == ')') {
        				break;
        			} else if (exp.charAt(end) == '(') {
        				count++;
        				//System.out.println("count is " + count);
        			} else if (exp.charAt(end) == ')') {
        				count--;
        				//System.out.println("count is " + count);
        			}
        		}
        		return evaluate(exp.substring(0, start) + evaluate(exp.substring(start+1, end)) + exp.substring(end+1));
    		} else {
    			exp = exp.replaceAll(" ", "");
    			exp = " "+exp+" ";
    			int ops = 0;
    			//boolean neg = false;
    			
    			for (int i = 0; i < exp.length(); i++) {
    				if ((i == 1 && exp.charAt(i) == '-') || (exp.charAt(i) == '-' && !Character.isDigit(exp.charAt(i-1)))) {
    					//neg = !neg;
    					//exp = exp.substring(0, i) + exp.substring(i+1);
    				} else if (exp.charAt(i) == '+' || exp.charAt(i) == '-' || exp.charAt(i) == '/' || exp.charAt(i) == '*') {
    					ops++;
    				}
    			}
    			if (ops == 0) {
    				return exp.replaceAll(" ", "");
    			}
    			Stack<Float> numbers = new Stack<Float>();
    			Stack<Character> operators = new Stack<Character>();
    			float num1;
    			float num2;
    			for (int i = 0; i < exp.length() ; i++) {
    				if ((i == 1 && exp.charAt(i) == '-') || (exp.charAt(i) == '-' && !Character.isDigit(exp.charAt(i-1)))) {
    					int j;
    					for (j = i;Character.isDigit(exp.charAt(j));j++) {};
    					j--;
    					numbers.push(Float.parseFloat(exp.substring(i, j+1)));
    					i = --j;
    				} else if (Character.isDigit(exp.charAt(i))) {
    					int j;
    					for (j = i;Character.isDigit(exp.charAt(j)) || exp.charAt(j) == '.';j++) {};
    					numbers.push(Float.parseFloat(exp.substring(i, j)));
    					i = --j;
    				} else if (exp.charAt(i) == '*' || exp.charAt(i) == '/') {
    					char op = exp.charAt(i);
    					num1 = numbers.pop();
    					int j = i+1, k;
    					for (k = j; Character.isDigit(exp.charAt(k));k++) {}
    					k--;
    					num2 = Float.parseFloat(exp.substring(j, k+1));
    					float result;
    					if (op == '*') {
    						result = num1 * num2;
    					} else {
    						result = num1 / num2;
    					}
    					int start;
    					for (start = i-1; Character.isDigit(exp.charAt(start)) || exp.charAt(start) == '.';start--);
    					start++;
    					int end = k;
    					exp = exp.substring(0, start) + result + exp.substring(end+1);
    					numbers.push(result);
    					i = (start-1) + (""+result).length();
    				} else if (exp.charAt(i) == '+' || exp.charAt(i) == '-') {
    					operators.push(exp.charAt(i));
    				}
    			}
    			
    			float temp = numbers.pop();
    			if (numbers.isEmpty()) {
    				return ""+temp;
    			}
    			numbers.push(temp);
    			float numbs[] = new float[numbers.size()];
    			char opers[] = new char[operators.size()];
    			for (int i = numbers.size()-1; !numbers.isEmpty(); i--) {
    				numbs[i] = numbers.pop();
    			}
    			for (int i = operators.size()-1; !operators.isEmpty(); i--) {
    				opers[i] = operators.pop();
    			}
    			int i = 2, j = 1; 
    			float result;
    			
    			if (opers[0] == '+') {
    				result = numbs[0] + numbs[1];
    			} else {
    				result = numbs[0] - numbs[1];
    			}
    			
    			while (j < opers.length) {
    				char ch = opers[j];
    				num1 = numbs[i];
    				switch(ch) {
    					case '+':
    						result += num1;
    						break;
    					case '-':
    						result -= num1;
    						break;
    				}
    				j++;
    				i += 2;
    			}
    			return ""+result;
    			/*while (!operators.isEmpty()) {
    				char op = operators.pop();
    				num2 = numbers.pop();
    				num1 = numbers.pop();
    				float result = 0;
    				switch (op) {
    					case '+': 
    						result = num1 + num2; 
    						break;
    					case '-':
    						result = num1 - num2;
    						break;
    				}
    				numbers.push(result);
    			}*/
    		}
    	}
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    		for (ArraySymbol as: arrays) {
    			System.out.println(as);
    		}
    }

}
