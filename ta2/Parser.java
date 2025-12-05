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

	private Token peek() throws SyntaxException {
		return scanner.peek();
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
	//   match() = method that scans the next token if the current token is the expected
	//   SyntaxException = exception thrown when a syntax error occurs
	//   NodeRelop = node class for relational operators
	//   returns a NodeRelop object representing a relational operator

	private NodeRelop parseRelop() throws SyntaxException {
    String t = curr().tok();
    int p = pos();

    switch (t) {
        case "<":
            match("<");
            return new NodeRelop(p, "<");

        case "<=":
            match("<=");
            return new NodeRelop(p, "<=");

        case ">":
            match(">");
            return new NodeRelop(p, ">");

        case ">=":
            match(">=");
            return new NodeRelop(p, ">=");

        case "==":
            match("==");
            return new NodeRelop(p, "==");

        case "<>":
            match("<>");
            return new NodeRelop(p, "<>");
    }
    throw new SyntaxException( p, new Token("<, <=, >, >=, ==, <>"),curr());
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
	//   NodeFactNeg = node class for negated factors
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
		// unary prefix minus: '-' factor
		if (curr().equals(new Token("-"))) {
			match("-");
			// parse the following factor and wrap it in a negation node
			return new NodeFactNeg(parseFact());
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
	//   NodeExpr = node class for expressions
	//   NodeRelop = node class for relational operators
	//   NodeBoolExpr = node class for boolean expressions
	//   left = NodeExpr object representing the left-hand expression
	//   op = NodeRelop object representing the relational operator
	//   right = NodeExpr object representing the right-hand expression
	//   returns a NodeBoolExpr object representing the boolean expression
	NodeBoolExpr parseBoolExpr() throws SyntaxException {
    	NodeExpr left = parseExpr();
    	NodeRelop op = parseRelop();
    	NodeExpr right = parseExpr();
    	return new NodeBoolExpr(left, op, right);
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
	//   stmt = NodeStmt object representing the statement
	//   block = NodeBlock object representing the block
	//   returns a NodeStmt object representing the statement
	//   if the current token is 'wr', parse it as a write statement
	//   if the current token is 'rd', parse it as a read statement
	//   if the current token is 'if', parse it as an if statement
	//   if the current token is 'while', parse it as a while statement
	//   if the current token is 'begin', parse it as a block statement
	//   if the current token is an identifier followed by '=', parse it as an assignment
	//   otherwise, try parsing it as a boolean expression

private NodeStmt parseStmt() throws SyntaxException {
    if (curr().equals(new Token("wr"))) {
        return parseWr();
    }
    else if (curr().equals(new Token("rd"))) {
        return parseRd();
    } 
    else if (curr().equals(new Token("if"))) {
        match("if");
        NodeBoolExpr boolExpr = parseBoolExpr();
        match("then");
        NodeBlock thenBlock = parseBlock();
        NodeBlock elseBlock = null;
        if (curr().equals(new Token("else"))) {
            match("else");
            elseBlock = parseBlock();
        }
        return new NodeStmtIf(boolExpr, thenBlock, elseBlock);
    }
    else if (curr().equals(new Token("while"))) {
        match("while");
        NodeBoolExpr boolExpr = parseBoolExpr();
        match("do");
        NodeBlock block = parseBlock();
        return new NodeStmtWhile(boolExpr, block);
    }
	   else if (curr().equals(new Token("begin"))) {
        match("begin");               // consume 'begin'
        NodeBlock block = parseBlock();  // parse inner block
        match("end");                 // consume 'end'
        return new NodeStmtBlock(block); // wrap as NodeStmtBlock
    }
    else if (curr().equals(new Token("id")) && peek().equals(new Token("="))) {
        return parseAssn();
    }
    else { 
        // anything else: try parsing a boolean expression
        NodeBoolExpr boolExpr = parseBoolExpr();
        return new NodeStmtBool(boolExpr);
    }
}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected
	//   SyntaxException = exception thrown when a syntax error occurs
	//   NodeWr = node class for write statements
	//   NodeExpr = node class for expressions
	//   expr = NodeExpr object representing the expression
	//   returns a NodeWr object representing the write statement
	private NodeWr parseWr() throws SyntaxException {
		match("wr");
		NodeExpr expr = parseExpr();
		return new NodeWr(expr);
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected
	//   SyntaxException = exception thrown when a syntax error occurs
	//   NodeRd = node class for read statements
	//   NodeFactId = node class for factor identifiers
	//   id = Token object representing an identifier
	//   returns a NodeRd object representing the read statement
	private NodeRd parseRd() throws SyntaxException {
		match("rd");           // consume 'rd'
		Token idToken = curr();
		match("id");           // consume identifier
		NodeFactId id = new NodeFactId(pos(), idToken.lex());
		return new NodeRd(id);
	}
	//arguments: none
	//Members:
	//   curr() = method that returns the current token
	//   match() = method that scans the next token if the current token is the expected

	//   SyntaxException = exception thrown when a syntax error occurs	
	//   NodeStmt = node class for statements
	//   NodeBlock = node class for blocks
	//   stmt = NodeStmt object representing the statement
	//   block = NodeBlock object representing the block
	//   returns a NodeStmt object representing the statement

	private NodeBlock parseBlock() throws SyntaxException {
      NodeStmt stmt = parseStmt();
      if(curr().equals(new Token(";"))) {
          match(";");
          if (!curr().equals(new Token("end")) &&
              !curr().equals(new Token("else")) &&
              !curr().equals(new Token("EOF"))) {
              NodeBlock block = parseBlock();
              return new NodeBlock(stmt, block);
          }
      }
      return new NodeBlock(stmt, null);
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
		NodeBlock block = parseBlock();
		match("EOF");
		return block;
	}

}
