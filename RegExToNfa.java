package csen1002.main.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

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
	private String[] regex;
	private String[] alphabet; //A
	private ArrayList<String> stateSet = new ArrayList<>();; //Q
	//private String[] transitionStates; //T
	private ArrayList<String> transitionStates = new ArrayList<>(); //T
	int startState; //I
	int finalState; //F
	private int stateNum;
	private Stack<String> regexStack = new Stack<>();
	private Stack<Nfa> nfaStack = new Stack<>();

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
		//split by ';'
		this.alphabetString = inputArray[0];
		this.alphabet = alphabetString.split(";");
		
		this.regexString = inputArray[1];
		this.regex = regexString.split("");
		this.stateNum = -1;
		
		// TODO Auto-generated constructor stub
	}
	
	
	public Nfa NewCharacter(String c) {
		
		String stateOne = String.valueOf(stateNum+1);
		String stateTwo = String.valueOf(stateNum+2);
//		System.out.println("STATEONE: " + stateOne);
//		System.out.println("STATETWO: " + stateTwo);
		stateSet.add(stateOne);
		stateSet.add(stateTwo);	
		//System.out.println("CHARACTER STATE SET: " + stateSet);
		//I have two states to transition from starting from stateNum+1 to stateNum+2
		String newCharacterNFA = stateOne + "," + c + "," + stateTwo;
		//System.out.println(newCharacterNFA);
		transitionStates.add(newCharacterNFA);
		Nfa newNFA = new Nfa(stateOne, stateTwo);
		newNFA.addTransitionState(newCharacterNFA); // adding transition state to NFA
		//System.out.println("STATENUM BEFORE NEW CHAR: "+ c + " " + stateNum);
		stateNum+=2;
		//System.out.println("STATENUM AFTER NEW CHAR: " + c + " "+ stateNum);
		nfaStack.push(newNFA);
		return newNFA;
		
	}
	
	
	public void UnionCharacter(Nfa nfa1, Nfa nfa2) {
//		String start = String.valueOf(stateNum);
//		String end = String.valueOf(stateNum+1);
//		String newUnionNFA = start + ";" + "e" + ";" + end;Nfa nfa = new Nfa();
		//System.out.println("STATENUM BEFORE UNION: " + stateNum);
		String start = String.valueOf(stateNum+1);
		//System.out.println("ANA HENA: " +stateNum);
		String end = String.valueOf(stateNum+2);
        Nfa unionNFA = new Nfa(start, end);
        stateSet.add(start);
		stateSet.add(end);	
		//System.out.println("UNION STATE SET: " + stateSet);

        ArrayList<String> allTransitions = new ArrayList<>();
        allTransitions.addAll(nfa1.getTransitionStates()); //a
        allTransitions.addAll(nfa2.getTransitionStates()); //b
        allTransitions.add(start + "," + "e" + "," + nfa1.getInitialState()); 
        allTransitions.add(start + "," + "e" + "," + nfa2.getInitialState());
        allTransitions.add(nfa1.getFinalState() + "," + "e" + "," + end);
        allTransitions.add(nfa2.getFinalState() + "," + "e" + "," + end);
        unionNFA.setTransitionStates(allTransitions);

        stateNum += 2; //for the new bedaya and new nehaya
       // System.out.println("STATENUM AFTER UNION: " + stateNum);
        nfaStack.push(unionNFA);
		
	}
	
	
	public void ConcatCharacter(Nfa nfa1, Nfa nfa2) {
      //  String initialNFAState1 = nfa1.getInitialState();
      //  String finalNFAState1 = nfa1.getFinalState();
       // String initialNFAState2 = nfa2.getInitialState();
       // String finalNFAState2 = nfa2.getFinalState();
        
        //nfa1.setInitialState(nfa2.getFinalState());
		Nfa concatNFA = new Nfa(nfa2.getInitialState(), nfa1.getFinalState());
		
		ArrayList<String> allTransitions = new ArrayList<>();
		//in concat i only add one new transition 
		allTransitions.addAll(nfa1.getTransitionStates());
        allTransitions.addAll(nfa2.getTransitionStates()); 
        
        for (int i = 0; i < nfa1.getTransitionStates().size(); i++) {
        	String[] subStrings = nfa1.getTransitionStates().get(i).split(",");
        	String replaced =  subStrings[0];
        	//System.out.println("REPLACED: " + replaced);
        	//System.out.println("SS SIZE: " + subStrings.length);
    		
        	if (replaced.equals(nfa1.getInitialState())) {
        		//System.out.println("REPLACED: " + replaced);
        		String res = nfa2.getFinalState() + "," + subStrings[1] + "," + subStrings[2];
        		//allTransitions.remove(i);
        		allTransitions.remove(nfa1.getTransitionStates().get(i));
        		stateSet.remove(replaced);
        		allTransitions.add(res);
        		//System.out.println("res: " + res);
        	}
        	
        	
        }
        
       // System.out.println("ALL TRANSITIONS: " );
//        for (int i = 0; i < allTransitions.size(); i++) {
//        	System.out.println(allTransitions.get(i));
//        }
       // allTransitions.add(nfa2.getFinalState() + "," + "e" + "," + );
       // allTransitions.add(initialNFAState + "," + "e" + "," + finalNFAState);
        concatNFA.setTransitionStates(allTransitions);
        nfaStack.push(concatNFA);
        
		
	}
	
	
	public void KleeneStarCharacter(Nfa nfa) {
		 String initialNFAState = nfa.getInitialState();
		 String finalNFAState = nfa.getFinalState();
		 String start = String.valueOf(stateNum+1);
		 String end = String.valueOf(stateNum+2);
		 stateSet.add(start);
		 stateSet.add(end);
		
		 ArrayList<String> allTransitions = new ArrayList<>();
		 allTransitions.addAll(nfa.getTransitionStates());
		 allTransitions.add(start + "," + "e" + "," + initialNFAState);
		 allTransitions.add(start + "," + "e" + "," + end); //direct epsilon transition
		 allTransitions.add(finalNFAState + "," + "e" + "," + initialNFAState);
		 allTransitions.add(finalNFAState + "," + "e" + "," + end);
		 Nfa kleeneStarNFA = new Nfa(start, end);
		 kleeneStarNFA.setTransitionStates(allTransitions);
		 stateNum+=2;
		 nfaStack.push(kleeneStarNFA);

	}
	
	

	/**
	 * @return Returns a formatted string representation of the NFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		String finalOutputaya;
		String Q = "";
		//String A;
		String T = "";
		String I;
		String F;
		for(int i = 0; i < regex.length; i++) {
			
			//if ((Character.isLetter(regex[i].charAt(0))) && !(regex[i].equals("e"))) 
			if ((Character.isLetter(regex[i].charAt(0))))
			{
				//System.out.println("eh el character: " + regex[i].charAt(0) );
				NewCharacter(regex[i]);
				//System.out.println("STATENUM BAAD NEW CHAR FE TOSTRING " + stateNum);
			}
			
			else if(regex[i].equals("|")) {
			//	String charOne = regexStack.pop();
			//	String charTwo = regexStack.pop();
			//	Nfa nfa1 = NewCharacter(charTwo);
			//	Nfa nfa2 = NewCharacter(charOne);
			//	stateNum-=2;
				//System.out.println("STATENUM ABL EL UNION 1 " + stateNum);
     			Nfa nfa1 = nfaStack.pop();
				Nfa nfa2 = nfaStack.pop();
				//System.out.println("STATENUM ABL EL UNION 2 " + stateNum);
				UnionCharacter(nfa1, nfa2);
				//System.out.println("UNION");
			}
			
			else if(regex[i].equals(".")) {
				Nfa nfa1 = nfaStack.pop();
				Nfa nfa2 = nfaStack.pop();
				ConcatCharacter(nfa1, nfa2);
				//System.out.println("CONCAT");
			}
			
			else if(regex[i].equals("*")) {
				//System.out.println("ANA DAKHALT KLEENE EL AWAL");
				Nfa nfa1 = nfaStack.pop();
				KleeneStarCharacter(nfa1);
		//		System.out.println("ASTRICS");
			}
			
		}
		// TODO Auto-generated method stub
		
		for (int i = 0; i < stateSet.size(); i++) {
			if (Q.equals("")) {
				Q = stateSet.get(i);
			}
			
			else {
				Q = Q  + ";" + stateSet.get(i);
			}
		}
		
		
		//T states
		Nfa finalNFA = nfaStack.pop();
		//ArrayList<String> sortedTList = finalNFA.getTransitionStates();
		//finalNFA.getTransitionStates().sort(null);
		Collections.sort(finalNFA.getTransitionStates(), new TransitionStatesComp());
		for (int i = 0; i < finalNFA.getTransitionStates().size(); i++) {
			if (T.equals("")) {
				T = finalNFA.getTransitionStates().get(i);
			}
			
			else {
				T = T + ";" + finalNFA.getTransitionStates().get(i);
			}
		}
		
		I = finalNFA.getInitialState();
		F = finalNFA.getFinalState();
		
		finalOutputaya = Q + "#" + alphabetString + "#" + T + "#" + I + "#" + F;
		
		//System.out.println(finalOutputaya);
		return finalOutputaya;
	}
	
	
	public static void main(String[] args) {
      //  String input = "a;b#ab|";
        String input = "a;b#eab*|.a.*";
        
       // NewCharacter("a");
       // NewCharacter("b");
        
       
        
        RegExToNfa nfa = new RegExToNfa(input);
        nfa.toString();
       // System.out.println(nfa.alphabetString);
       // System.out.println(nfa.regexString);
        
        
//        System.out.println("Alphabet:");
//        for (int i = 0; i < nfa.alphabet.length; i++) {
//        	System.out.println(nfa.alphabet[i]);
//        }
//        
//        System.out.println("Regex:");
//        for (int i = 0; i < nfa.regex.length; i++) {
//        	System.out.println(nfa.regex[i]);
//        }
//        
//        System.out.println("Elements of the stack:");
//        while (!nfa.regexStack.isEmpty()) {
//            String element = nfa.regexStack.pop();
//            System.out.println(element);
//        }
//        
//        System.out.println("transition");
//        for (int i = 0; i < nfa.transitionStates.size(); i++) {
//        	System.out.println(nfa.transitionStates.get(i));
//        }
//        
//        System.out.println("stateNum "+  nfa.stateNum);
        
        
    }

}
//I need to keep track of each NFAs initial, final and transition states.
class Nfa {
    private String initialState;
    private String finalState;
    private ArrayList<String> transitionStates;

    public Nfa(String init, String finalS) {
        this.initialState = init; 
        this.finalState = finalS;  
        this.transitionStates = new ArrayList<>();
    }

    // getters n setters for the initial, final state, n transition states
    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public ArrayList<String> getTransitionStates() {
        return transitionStates;
    }

    public void addTransitionState(String transitionState) {
        transitionStates.add(transitionState);
    }
    
    public void setTransitionStates(ArrayList<String> tStates) {
        transitionStates = tStates;
    }
    
}


class TransitionStatesComp implements Comparator<String> {
    @Override
    public int compare(String t1, String t2) {
    	 String[] hagatT1 = t1.split(",");
         String[] hagatT2 = t2.split(",");
         int fNum1 = Integer.parseInt(hagatT1[0]);
         int fNum2 = Integer.parseInt(hagatT2[0]);
         int lNum1 = Integer.parseInt(hagatT1[2]);
         int lNum2 = Integer.parseInt(hagatT2[2]);
         
         if (fNum1 != fNum2) {
             return Integer.compare(fNum1, fNum2); 
         }
         else {
             return Integer.compare(lNum1, lNum2);
        }
    }
}

