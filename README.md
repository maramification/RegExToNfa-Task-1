# RegExToNfa-Task-1

## German University in Cairo
### Department of Computer Science
### Assoc. Prof. Haythem O. Ismail

### CSEN1002 Compilers Lab, Spring Term 2024
**Task 1: Regular Expressions to Non-Deterministic Finite Automata**

## Overview
This project involves implementing Thompson’s construction for converting a regular expression into an equivalent Non-Deterministic Finite Automaton (NFA). The implementation should adhere to the guidelines and requirements specified below.

## Objective
Implement Thompson’s construction to convert a regular expression to an equivalent NFA. For more details on Thompson’s construction, refer to Chapter 3 of the textbook and [Thompson's Construction on Wikipedia](https://en.wikipedia.org/wiki/Thompson's_construction).

## Requirements

1. **Assumptions:**
   - The alphabet Σ of the regular expression is a subset of the Latin alphabet (excluding 'e').
   - Regular expressions do not include ∅.
   - The empty string ε is represented by 'e'.
   - Concatenation is represented by '.', and union by '|'.
   - Regular expressions are given in postfix notation.
   - States of the resulting NFA are numbers.
   - States introduced by the NFA for a prefix of a postfix regular expression R are smaller (numerically) than those for longer prefixes of R.
   - For operators (such as union and '*'), the start state is smaller (numerically) than the accept state.
   - Concatenation involves merging the accept state of the first (left) NFA with the start state of the second (right) NFA. The merged state is the accept state of the first NFA.

2. **Implementation:**
   - Implement a class `RegExToNfa` with a constructor and a `toString` method.
   - The constructor `RegExToNfa` takes a single parameter of the form `A#R`, where:
     - `A` is a semicolon-separated string of alphabetically sorted symbols representing the alphabet Σ.
     - `R` is a postfix regular expression over Σ.
   - The `toString` method returns a string describing the resulting NFA in the format `Q#A#T#I#F`:
     - `Q`: Semicolon-separated sequence of sorted integer literals representing the set of states.
     - `A`: Semicolon-separated sequence of alphabetically sorted symbols representing the input alphabet.
     - `T`: Semicolon-separated sequence of triples representing the transition function. Each triple is a comma-separated sequence `i,a,j` where `i` is a state in Q, `a` is a symbol in A or 'e', and `j` is a state in Q. The triples are sorted by the source state `i`, then by the input `a`, and then by the destination state `j`.
     - `I`: Integer literal representing the initial state.
     - `F`: Semicolon-separated sequence of sorted integer literals representing the set of accept states.

   Example:
   - For the regular expression `a;b#ab|`, `toString` should return:
     ```
     0;1;2;3;4;5#a;b#0,a,1;1,e,5;2,b,3;3,e,5;4,e,0;4,e,2#4#5
     ```

For any further details or clarifications, refer to the lab manual or contact the course instructor.
