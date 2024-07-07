package csen1002.main.task1;

/**
 * Write your info here
 * 
 * @name Maram Hossam El-Deen Mohamed
 * @id 49-1891
 * @labNumber 18
 */

public class RegExToNfa {
	private String alphabetString;
	private String regexString;
	private String[] alphabet;
	private String[] regex;

	/**
	 * Constructs an NFA corresponding to a regular expression based on Thompson's
	 * construction
	 * 
	 * @param input The alphabet and the regular expression in postfix notation for
	 *              which the NFA is to be constructed
	 */
	public RegExToNfa(String input) {
		//split by '#'
		String [] inputArray = input.split("#");
		this.alphabetString = inputArray[0];
		this.regexString = inputArray[1];
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns a formatted string representation of the NFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {
        String input = "a;b#ab|";
        RegExToNfa nfa = new RegExToNfa(input);
        System.out.println(alphabetString);
        System.out.println(regexString);
    }

}
