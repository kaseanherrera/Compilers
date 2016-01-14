package crux;

public class Token {
	String stringMatch = "\\d+";
	String numberMatch = "(?:\\d+)\\.?\\d*";
	String identifierMatch = "[a-zA-Z]+";
	
	public static enum Kind {
		AND("and"),
		OR("or"),
		NOT("not"),
		LET("let"),
		VAR("var"),
		ARRAY("array"),
		FUNC("func"),
		IF("if"),
		ELSE("else"),
		WHILE("while"),
		TRUE("true"),
		FALSE("false"),
		RETURN("return"),
		
		OPEN_PAREN("("),
		CLOSE_PAREN(")"),
		OPEN_BRACE("{"),
		CLOSE_BRACE("}"),
		OPEN_BRACKET("["),
		CLOSE_BRACKET("]"),
		ADD("+"),
		SUB("-"),
		MUL("*"),
		DIV("/"),
		GREATER_THEN(">"),
		LESS_THAN("<"),
		ASSIGN("="),
		COMMA(","),
		SEMICOLON(";"),
		COLON(":"),
		CALL("::"),
		EQUAL("=="),
		
		IDENTIFIER(),
		INTEGER(),
		FLOAT(),
		ERROR(),
		EOF();
		
		// TODO: complete the list of possible tokens
		
		private String default_lexeme;
		
		Kind()
		{
			default_lexeme = "";
		}
		
		Kind(String lexeme)
		{
			default_lexeme = lexeme;
		}
		
		public boolean hasStaticLexeme()
		{
			return default_lexeme != null;
		}
		
		// OPTIONAL: if you wish to also make convenience functions, feel free
		//           for example, boolean matches(String lexeme)
		//           can report whether a Token.Kind has the given lexeme
	}
	
	private int lineNum;
	private int charPos;
	Kind kind;
	private String lexeme = "";
	

	public Token(int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// if we don't match anything, signal error
		this.kind = Kind.ERROR;
		this.lexeme = "No Lexeme Given";
	}
	
	public Token(String lexeme, int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
		this.lexeme = lexeme;
		
		// TODO: based on the given lexeme determine and set the actual kind
		
		switch(lexeme){
			case "and" : this.kind = Kind.AND;
				return;
			case "or" : this.kind = Kind.OR;
				return;
			case "not" : this.kind = Kind.NOT;
				return;
			case "let" : this.kind = Kind.LET;
				return;
			case "var" : this.kind = Kind.VAR;
				return;
			case "array" : this.kind = Kind.ARRAY;
				return;
			case "func" : this.kind = Kind.FUNC;
				return;
			case "if" : this.kind = Kind.IF;
				return;
			case "else" : this.kind = Kind.ELSE;
				return;
			case "while" : this.kind = Kind.WHILE;
				return;
			case "true" : this.kind = Kind.TRUE;
				return;
			case "false" : this.kind = Kind.FALSE;
				return;
			case "return" : this.kind = Kind.RETURN;
				return;
			case "(" : this.kind = Kind.OPEN_PAREN;
				return;
			case ")" : this.kind = Kind.CLOSE_PAREN;
				return;
			case "{" : this.kind = Kind.OPEN_BRACE;
				return;
			case "}" : this.kind = kind.CLOSE_BRACE;
				return;
			case "[" : this.kind = kind.OPEN_BRACKET;
				return;
			case "]" : this.kind = kind.CLOSE_BRACKET;
				return;
			case "+" : this.kind = Kind.ADD;
				return;
			case "-" : this.kind = Kind.SUB;
				return;
			case "*" : this.kind = Kind.MUL;
				return;
			case "/": this.kind = Kind.DIV;
				return;
			case "==" : this.kind = Kind.EQUAL;
				return;
			case ">" : this.kind = Kind.GREATER_THEN;
				return;
			case "<" : this.kind = Kind.LESS_THAN;
				return;
			case "=" : this.kind = Kind.ASSIGN;
				return;
			case "," : this.kind = Kind.COMMA;
				return;
			case ";" : this.kind = Kind.SEMICOLON;
				return;
			case ":" : this.kind = Kind.COLON;
				return;
			case "::" : this.kind = Kind.CALL;
				return;
			
			}
		
			if(isInteger(lexeme))
				this.kind = Kind.INTEGER;
			else if(isFloat(lexeme))
				this.kind = Kind.FLOAT;
			else if(isIdentifier(lexeme))
				this.kind = Kind.IDENTIFIER;
			else{
				this.kind = Kind.ERROR;
			}	
	}
	
	public int lineNumber()
	{
		return lineNum;
	}
	
	public int charPosition()
	{
		return charPos;
	}
	
	// Return the lexeme representing or held by this token
	public String lexeme()
	{
		return lexeme;
	}
	
	//returns true is the input is an Integer
	public boolean isInteger(String input){
		//check if any character is not an integer
		return input.matches(stringMatch);
	}
	
	//return true if string represents a float 
	public boolean isFloat(String input){
		//if the input is a number and contains a period
		return (input.matches(numberMatch) && input.contains("."));
	}
	
	//check if the input is an Identifier
	public boolean isIdentifier(String input){
		return input.matches(identifierMatch);
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(this.kind.toString()); //kind
		str.append('(' + this.lexeme + ')'); //lexeme
		str.append("(lineNum :" + this.lineNumber() + ", " + "charPos:" + this.charPos + ')'); //line number and postion 
		return str.toString();
	}
	
	
	//implement factory functions for some tokens, as you see fit          
	public static Token EOF(int linePos, int charPos)
	{
		Token tok = new Token(linePos, charPos);
		tok.kind = Kind.EOF;
		tok.lexeme = "EOF";
		return tok;
	}
	
	// OPTIONAL: function to query a token about its kind
	//           boolean is(Token.Kind kind)

}
