// This class is a scanner for the program
// and programming language being interpreted.

import java.util.*;

public class Scanner {

	private String program;		// source program being interpreted
	private int pos;			// index of next char in program
	private Token token;		// last/current scanned token

	// sets of various characters and lexemes
	private Set<String> whitespace=new HashSet<String>();
	private Set<String> digits=new HashSet<String>();
	private Set<String> letters=new HashSet<String>();
	private Set<String> legits=new HashSet<String>();
	private Set<String> keywords=new HashSet<String>();
	private Set<String> operators=new HashSet<String>();
	private Set<String> comment=new HashSet<String>();

	// initializers for previous sets

	private void fill(Set<String> s, char lo, char hi) {
		for (char c=lo; c<=hi; c++)
			s.add(c+"");
	}
	// whitespace characters, e.g., ' ', '\n', '\t'
	private void initWhitespace(Set<String> s) {
		s.add(" ");
		s.add("\n");
		s.add("\t");
	}
	// comment character, e.g., '#'
	private void initComment(Set<String> s) {
		s.add("#");
	}

	// digit characters, '0'..'9'
	private void initDigits(Set<String> s) {
		fill(s,'0','9');
	}
	// letter characters, 'A'..'Z', 'a'..'z'
	private void initLetters(Set<String> s) {
		fill(s,'A','Z');
		fill(s,'a','z');
	}
	// legal characters for ids: letters and digits
	private void initLegits(Set<String> s) {
		s.addAll(letters);
		s.addAll(digits);
	}
	// operators: e.g., "+", "-", "*", "/", "=", "(", ")", ";"
	private void initOperators(Set<String> s) {
		s.add("=");
		s.add("+");
		s.add("-");
		s.add("*");
		s.add("/");
		s.add("(");
		s.add(")");
		s.add(";");
	}
	// keywords: e.g., "if", "then", "else", "end", "repeat", "until", "read", "write"
	private void initKeywords(Set<String> s) {
	}

	// constructor:
	//     - squirrel-away source program
	//     - initialize sets
	public Scanner(String program) {
		this.program=program;
		pos=0;
		token=null;
		initComment(comment);
		initWhitespace(whitespace);
		initDigits(digits);
		initLetters(letters);
		initLegits(legits);
		initKeywords(keywords);
		initOperators(operators);
	}

	// when the position counter has reached the end of the length of the whole program, it will return a true once at end
	public boolean done() {
		return pos>=program.length();
	}
	// takes a set as a argument that contains string objects
	//while it is not done and the set s contains the current char converted into a string, it will then increment position 
	//Arguments:
	//     s = set of characters to search for
	// Members:
	//     program = the scanner's input
	//     pos = index of current input character
	private void many(Set<String> s) {
		while (!done()&&s.contains(program.charAt(pos)+""))
			pos++;
	}

	// This method advances the scanner,
	// until the current input character
	// is just after a sequence of one or more
	// of a particular character.
	// Arguments:
	//     c = the character to search for
	// Members:
	//     program = the scanner's input
	//     pos = index of current input character
	private void past(char c) {
		while (!done()&&c!=program.charAt(pos))
			pos++;
		if (!done()&&c==program.charAt(pos))
			pos++;
	}

	// Skip until and including newline
	private void skipLine() {
		while (!done() && program.charAt(pos)!='\n')
			pos++;
		if (!done()) pos++; // skip the newline too if we found one
	}

	// scan various kinds of lexeme
	//arguments: none
	//Members:
	//    program = the scanner's input
	//    pos = index of current input character
	//    token = last/current scanned token
	//scans for numbers and assigns a token

	private void nextNumber() {
		int old=pos;
		many(digits);
		token=new Token("num",program.substring(old,pos));
	}
	//looks for key words and identifiers
	//arguments: none
	//Members:
	//    program = the scanner's input
	//    pos = index of current input character
	//    token = last/current scanned token
	//	  old = temporary variable to hold the old position
	//    keywords = set of keywords
	//	  legits = set of legal characters for identifiers
	//	  lexeme = temporary variable to hold the current lexeme
	//    done() = method that checks if the position is at the end of the program
	//    letters = set of letter characters
	//scans for keywords and identifiers and assigns a token
	private void nextKwId() {
		int old=pos;
		many(letters);
		many(legits);
		String lexeme=program.substring(old,pos);
		token=new Token((keywords.contains(lexeme) ? lexeme : "id"),lexeme);
	}
	// scans for operators, it can do 1 char or 2 char operators, it assigns a token
	//arguments: none
	//Members:
	//    program = the scanner's input
	//    pos = index of current input character
	//    token = last/current scanned token
	//	  old = temporary variable to hold the old position
	//    operators = set of operators
	//	  lexeme = temporary variable to hold the current lexeme
	//	  done() = method that checks if the position is at the end of the program
	//scans for operators and assigns a token
	private void nextOp() {
		int old=pos;
		pos=old+2;
		if (!done()) {
			String lexeme=program.substring(old,pos);
			if (operators.contains(lexeme)) {
				token=new Token(lexeme); // two-char operator
				return;
			}
		}
		pos=old+1;
		String lexeme=program.substring(old,pos);
		token=new Token(lexeme); // one-char operator
	}

	// This method determines the kind of the next token (e.g., "id"),
	// and calls a method to scan that token's lexeme (e.g., "foo").
	// arguments: none
	// Members:
	//    program = the scanner's input
	//    pos = index of current input character
	//    token = last/current scanned token
	//    whitespace = set of whitespace characters
	//    digits = set of digit characters
	//    letters = set of letter characters
	//    operators = set of operator characters
	//    c = current character being examined
	//    done() = method that checks if the position is at the end of the program
	//scans the next token and assigns it to token, returns false if at end of file
	public boolean next() {
		String c=program.charAt(pos)+"";
		if (c.equals("#")) {
			skipLine();
			return next();
		}
		many(whitespace);
		if (done()) {
			token=new Token("EOF");
			return false;
		}
		c=program.charAt(pos)+"";
		if (digits.contains(c))
			nextNumber();
		else if (letters.contains(c))
			nextKwId();
		else if (operators.contains(c))
			nextOp();
		else {
			System.err.println("illegal character at position "+pos);
			pos++;
			return next();
		}
		return true;
	}

	// This method scans the next lexeme,
	// if the current token is the expected token.
	// Arguments:
	//     t = the expected token
	// Members:
	//    pos = index of current input character
	//    token = last/current scanned token
	//    curr() = method that returns the current token
	//    next() = method that scans the next token
	//    SyntaxException = exception thrown when a syntax error occurs
	public void match(Token t) throws SyntaxException {
		if (!t.equals(curr()))
			throw new SyntaxException(pos,t,curr());
		next();
	}

	// arguments: none
	// Members:
	//    pos = index of current input character
	//    token = last/current scanned token
	//    SyntaxException = exception thrown when a syntax error occurs
	// returns the current token, throws exception if there is none
	public Token curr() throws SyntaxException {
		if (token==null)
			throw new SyntaxException(pos,new Token("ANY"),new Token("EMPTY"));
		return token;
	}
	//this be like a counter and its being used to track the current position in the scans, just returns a integer
	public int pos() {
		return pos;
	}

	// for unit testing
	public static void main(String[] args) {
		try {
			Scanner scanner=new Scanner(args[0]);
			while (scanner.next())
				System.out.println(scanner.curr());
		} catch (SyntaxException e) {
			System.err.println(e);
		}
	}

}
