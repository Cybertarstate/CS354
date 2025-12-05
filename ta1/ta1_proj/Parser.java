// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

public class Parser {

	private Scanner scanner;

	private void match(String s) throws SyntaxException {
		scanner.match(new Token(s));
	}

	private Token curr() throws SyntaxException {
		return scanner.curr();
	}

	private int pos() {
		return scanner.pos();
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeMulop = node class for multiplication and division operators
	//   returns a NodeMulop object representing a multiplication or division operator
	private NodeMulop parseMulop() throws SyntaxException {
		if (curr().equals(new Token("*"))) {
			match("*");
			return new NodeMulop(pos(), "*");
		}
		if (curr().equals(new Token("/"))) {
			match("/");
			return new NodeMulop(pos(), "/");
		}
		return null;
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeAddop = node class for addition and subtraction operators
	//   returns a NodeAddop object representing an addition or subtraction operator
	private NodeAddop parseAddop() throws SyntaxException {
		if (curr().equals(new Token("+"))) {
			match("+");
			return new NodeAddop(pos(), "+");
		}
		if (curr().equals(new Token("-"))) {
			match("-");
			return new NodeAddop(pos(), "-");
		}
		return null;
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeFactop = node class for factor operators
	//   parseExpr() = method that parses an expression and returns a NodeExpr object
	//   NodeFactExpr = node class for factor expressions
	//   NodeFactId = node class for factor identifiers
	//   NodeFactNum = node class for factor numbers
	//   id = Token object representing an identifier
	//   num = Token object representing a number
    //   expr = NodeExpr object representing an expression
	//   fact = NodeFact object representing the factor
	//   Token = class representing a token
	//   pos() = method that returns the current position in the input
	//   returns a NodeFactop object representing a factor expression, identifier, or number
	private NodeFact parseFact() throws SyntaxException {
		if (curr().equals(new Token("("))) {
			match("(");
			NodeExpr expr = parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {
			Token id = curr();
			match("id");
			return new NodeFactId(pos(), id.lex());
		}
		Token num = curr();
		match("num");
		return new NodeFactNum(num.lex());
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeFactop = node class for factor operators
	//   NodeMulop = node class for multiplication and division operators
	//   NodeTerm = node class for term expressions
	//   fact = NodeFact object representing the factor
	//   mulop = NodeMulop object representing the multiplication or division operator
	//   term = NodeTerm object representing the term expression
	//   returns a NodeTerm object representing a term expression
	private NodeTerm parseTerm() throws SyntaxException {
		NodeFact fact = parseFact();
		NodeMulop mulop = parseMulop();
		if (mulop == null)
			return new NodeTerm(fact, null, null);
		NodeTerm term = parseTerm();
		term.append(new NodeTerm(fact, mulop, null));
		return term;
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeTerm = node class for term expressions
	//   NodeAddop = node class for addition and subtraction operatorsq
	//   Nodeexpr = node class for expression
	//   term = NodeTerm object representing the term expression
	//   addop = NodeAddop object representing the addition or subtraction operator
	//   expr = NodeExpr object representing the expression
	//   returns a NodeExpr object representing the expression
	private NodeExpr parseExpr() throws SyntaxException {
		NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
		if (addop == null)
			return new NodeExpr(term, null, null);
		NodeExpr expr = parseExpr();
		expr.append(new NodeExpr(term, addop, null));
		return expr;
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeExpr = node class for expression
	//   NodeAssn = node class for assignment
	//   id = Token object representing an identifier
	//   expr = NodeExpr object representing the expression
	//   assn = NodeAssn object representing the assignment
	//   returns a NodeAssn object representing the assignment
	private NodeAssn parseAssn() throws SyntaxException {
		Token id = curr();
		match("id");
		match("=");
		NodeExpr expr = parseExpr();
		NodeAssn assn = new NodeAssn(id.lex(), expr);
		return assn;
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeAssn = node class for assignment
	//   NodeStmt = node class for statement
	//   assn = NodeAssn object representing the assignment
	//   stmt = NodeStmt object representing the statement
	//   returns a NodeStmt object representing the statement
	private NodeStmt parseStmt() throws SyntaxException {
		NodeAssn assn = parseAssn();
		match(";");
		NodeStmt stmt = new NodeStmt(assn);
		return stmt;
	}
	//arguments: program = the input program as a string
	//Members:
	//   scanner = the Scanner object for the program
	//   parseStmt() = method that parses a statement and returns a NodeStmt object
	//   match() = method that scans the next token if the current token is the expected token
	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeStmt = node class for statements
	//   returns a Node object representing the parse tree for the program
	public Node parse(String program) throws SyntaxException {
		scanner = new Scanner(program);
		scanner.next();
		NodeStmt stmt = parseStmt();
		match("EOF");
		return stmt;
	}

}
