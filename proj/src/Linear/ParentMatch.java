package Linear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

public class ParentMatch {
	
	public static boolean checkParen(String expression) {
		
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < expression.length(); i++) {
			char ch = expression.charAt(i);
			if (ch == '(') {
				stack.push(ch);
			} else if (ch == ')') {
				try {
					stack.pop();
				} catch(NoSuchElementException e) {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}

	public static void main(String[] args) {
		
		try { //surround the calling of method that might throw an exception with a try block
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter expression: ");
			String expr = br.readLine();
			
			if (checkParen(expr)) {
				System.out.println("Matched");
			} else {
				System.out.println("Not matched");
			}
		} catch (IOException e) {
			
		}
	}

}
