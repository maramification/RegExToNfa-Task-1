package csen1002.main.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class RegExToNfa {
	private String alphabetString;
	private String regexString;
	private String[] regex;
	private String[] alphabet; //A
	private ArrayList<String> stateSet = new ArrayList<>();; //Q
	private ArrayList<String> transitionStates = new ArrayList<>(); //T
	int startState; //I
	int finalState; //F
	private int stateNum;
	private Stack<String> regexStack = new Stack<>();
	private Stack<Nfa> nfaStack = new Stack<>();

	public RegExToNfa(String input) {
		//split by '#'
		String [] inputArray = input.split("#");
		//split by ';'
		this.alphabetString = inputArray[0];
		this.alphabet = alphabetString.split(";");
		
		this.regexString = inputArray[1];
		this.regex = regexString.split("");
		this.stateNum = -1;
		
	}
	
	
	public Nfa NewCharacter(String c) {
		
		String stateOne = String.valueOf(stateNum+1);
		String stateTwo = String.valueOf(stateNum+2);
		stateSet.add(stateOne);
		stateSet.add(stateTwo);	
		String newCharacterNFA = stateOne + "," + c + "," + stateTwo;
		transitionStates.add(newCharacterNFA);
		Nfa newNFA = new Nfa(stateOne, stateTwo);
		newNFA.addTransitionState(newCharacterNFA); 
		stateNum+=2;
		nfaStack.push(newNFA);
		return newNFA;
		
	}
	
	
	public void UnionCharacter(Nfa nfa1, Nfa nfa2) {

		String start = String.valueOf(stateNum+1);
		String end = String.valueOf(stateNum+2);
        Nfa unionNFA = new Nfa(start, end);
        stateSet.add(start);
		stateSet.add(end);	

        ArrayList<String> allTransitions = new ArrayList<>();
        allTransitions.addAll(nfa1.getTransitionStates()); //a
        allTransitions.addAll(nfa2.getTransitionStates()); //b
        allTransitions.add(start + "," + "e" + "," + nfa1.getInitialState()); 
        allTransitions.add(start + "," + "e" + "," + nfa2.getInitialState());
        allTransitions.add(nfa1.getFinalState() + "," + "e" + "," + end);
        allTransitions.add(nfa2.getFinalState() + "," + "e" + "," + end);
        unionNFA.setTransitionStates(allTransitions);

        stateNum += 2; 
        nfaStack.push(unionNFA);
		
	}
	
	
	public void ConcatCharacter(Nfa nfa1, Nfa nfa2) {
     
		Nfa concatNFA = new Nfa(nfa2.getInitialState(), nfa1.getFinalState());
		ArrayList<String> allTransitions = new ArrayList<>();
		allTransitions.addAll(nfa1.getTransitionStates());
        allTransitions.addAll(nfa2.getTransitionStates()); 
        
        for (int i = 0; i < nfa1.getTransitionStates().size(); i++) {
        	String[] subStrings = nfa1.getTransitionStates().get(i).split(",");
        	String replaced =  subStrings[0];
    		
        	if (replaced.equals(nfa1.getInitialState())) {
        		String res = nfa2.getFinalState() + "," + subStrings[1] + "," + subStrings[2];
        		allTransitions.remove(nfa1.getTransitionStates().get(i));
        		stateSet.remove(replaced);
        		allTransitions.add(res);
        	}
        	
        	
        }
        
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
	
	
	@Override
	public String toString() {
		String finalOutputaya;
		String Q = "";
		String T = "";
		String I;
		String F;
		for(int i = 0; i < regex.length; i++) {
			
			if ((Character.isLetter(regex[i].charAt(0))))
			{
				NewCharacter(regex[i]);
			}
			
			else if(regex[i].equals("|")) {
     			Nfa nfa1 = nfaStack.pop();
				Nfa nfa2 = nfaStack.pop();
				UnionCharacter(nfa1, nfa2);
			}
			
			else if(regex[i].equals(".")) {
				Nfa nfa1 = nfaStack.pop();
				Nfa nfa2 = nfaStack.pop();
				ConcatCharacter(nfa1, nfa2);
			}
			
			else if(regex[i].equals("*")) {
				Nfa nfa1 = nfaStack.pop();
				KleeneStarCharacter(nfa1);
			}
			
		}
		
		for (int i = 0; i < stateSet.size(); i++) {
			if (Q.equals("")) {
				Q = stateSet.get(i);
			}
			
			else {
				Q = Q  + ";" + stateSet.get(i);
			}
		}
		
		
		Nfa finalNFA = nfaStack.pop();
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
		
		return finalOutputaya;
	}
	
	
	public static void main(String[] args) {
        String input = "a;b#eab*|.a.*";
        
        
       
        
        RegExToNfa nfa = new RegExToNfa(input);
        nfa.toString();
        
        
    }

}

class Nfa {
    private String initialState;
    private String finalState;
    private ArrayList<String> transitionStates;

    public Nfa(String init, String finalS) {
        this.initialState = init; 
        this.finalState = finalS;  
        this.transitionStates = new ArrayList<>();
    }

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

