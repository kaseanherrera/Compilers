package crux;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
    public static String studentName = "TODO: Your Name";
    public static String studentID = "TODO: Your 8-digit id";
    public static String uciNetID = "TODO: uci-net id";
    
	private String stringMatch = "\\d+";
	private String numberMatch = "(?:\\d+)\\.?\\d*";
	private String identifierMatch = "[a-zA-Z]+";
    
// Grammar Rule Reporting ==========================================
    private int parseTreeRecursionDepth = 0;
    private StringBuffer parseTreeBuffer = new StringBuffer();

    public void enterRule(NonTerminal nonTerminal) {
        String lineData = new String();
        for(int i = 0; i < parseTreeRecursionDepth; i++)
        {
            lineData += "  ";
        }
        lineData += nonTerminal.name();
      
        parseTreeBuffer.append(lineData + "\n");
        parseTreeRecursionDepth++;
    }
    
    private void exitRule(NonTerminal nonTerminal)
    {
        parseTreeRecursionDepth--;
    }
    
    public String parseTreeReport()
    {
        return parseTreeBuffer.toString();
    }

// Error Reporting ==========================================
    private StringBuffer errorBuffer = new StringBuffer();
    
    private String reportSyntaxError(NonTerminal nt)
    {
        String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected a token from " + nt.name() + " but got " + currentToken.kind() + ".]";
        errorBuffer.append(message + "\n");
        return message;
    }
     
    private String reportSyntaxError(Token.Kind kind)
    {
        String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected " + kind + " but got " + currentToken.kind() + ".]";
        errorBuffer.append(message + "\n");
        return message;
    }
    
    public String errorReport()
    {
        return errorBuffer.toString();
    }
    
    public boolean hasError()
    {
        return errorBuffer.length() != 0;
    }
    
    private class QuitParseException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        public QuitParseException(String errorMessage) {
            super(errorMessage);
        }
    }
    
    private int lineNumber()
    {
        return currentToken.lineNumber();
    }
    
    private int charPosition()
    {
        return currentToken.charPosition();
    }
          
// Parser ==========================================
    private Scanner scanner;
    private Token currentToken;
    
    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
        this.currentToken = scanner.next();
    }
    
    public void parse()
    {
        try {
            program();
        } catch (QuitParseException q) {
            errorBuffer.append("SyntaxError(" + lineNumber() + "," + charPosition() + ")");
            errorBuffer.append("[Could not complete parsing.]");
        }
    }
    
// Helper Methods ==========================================
    private boolean have(Token.Kind kind)
    {
        return currentToken.is(kind);
    }
    
    private boolean have(NonTerminal nt)
    {
        return nt.firstSet().contains(currentToken.kind());
    }

    private boolean accept(Token.Kind kind)
    {
        if (have(kind)) {
            currentToken = scanner.next();
            return true;
        }
        return false;
    }    
    
    private boolean accept(NonTerminal nt)
    {
        if (have(nt)) {
            currentToken = scanner.next();
            return true;
        }
        return false;
    }
   
    private boolean expect(Token.Kind kind)
    {
        if (accept(kind))
            return true;
        String errorMessage = reportSyntaxError(kind);
        throw new QuitParseException(errorMessage);
        //return false;
    }
        
    private boolean expect(NonTerminal nt)
    {
        if (accept(nt))
            return true;
        String errorMessage = reportSyntaxError(nt);
        throw new QuitParseException(errorMessage);
        //return false;
    }
    

// Grammar Rules =====================================================
    
    // literal := INTEGER | FLOAT | TRUE | FALSE .
    public void literal()
    {
    	enterRule(NonTerminal.LITERAL);
    	if(this.currentToken.kind == Token.Kind.INTEGER)
    		expect(Token.Kind.INTEGER);
    	else if(this.currentToken.kind == Token.Kind.FLOAT)
    		expect(Token.Kind.FLOAT);
    	else if(this.currentToken.kind == Token.Kind.TRUE)
    		expect(Token.Kind.TRUE);
    	else if(this.currentToken.kind == Token.Kind.FALSE)
    		expect(Token.Kind.FALSE);
    	exitRule(NonTerminal.LITERAL);
    }
    
    // designator := IDENTIFIER { "[" expression0 "]" } .
    public void designator()
    {
        enterRule(NonTerminal.DESIGNATOR);

        expect(Token.Kind.IDENTIFIER);
        while (accept(Token.Kind.OPEN_BRACKET)) {
            expression0();
            expect(Token.Kind.CLOSE_BRACKET);
        }
        
        exitRule(NonTerminal.DESIGNATOR);
    } 

    //program := declaration-list EOF .
    public void program()
    {
        enterRule(NonTerminal.PROGRAM);
        declerationList();
        exitRule(NonTerminal.PROGRAM);
    }
    
    //declaration-list := { declaration } .
    private void declerationList() {
		// TODO Auto-generated method stub
		enterRule(NonTerminal.DECLARATION_LIST);
		while(this.currentToken.kind == Token.Kind.VAR || this.currentToken.kind == Token.Kind.ARRAY || this.currentToken.kind == Token.Kind.FUNC)
			decleration();
		exitRule(NonTerminal.DECLARATION_LIST);
	}

    //declaration := variable-declaration | array-declaration | function-definition .
	private void decleration() {
		enterRule(NonTerminal.DECLARATION);
		
		if(this.currentToken.kind == Token.Kind.VAR){
			variableDeclaration();
		}
		else if(this.currentToken.kind == Token.Kind.ARRAY){
			arrayDeclaration();
		}
		else if(this.currentToken.kind == Token.Kind.FUNC){
			functionDefinition();
		}
		
		exitRule(NonTerminal.DECLARATION);
		
	}

	//function-definition := "func" IDENTIFIER "(" parameter-list ")" ":" type statement-block .
	private void functionDefinition() {
		enterRule(NonTerminal.FUNCTION_DEFINITION);
		expect(Token.Kind.FUNC);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.OPEN_PAREN);
		paramerterList();
		expect(Token.Kind.CLOSE_PAREN);
		expect(Token.Kind.COLON);
		type();
		statementBlock();
		exitRule(NonTerminal.FUNCTION_DEFINITION);
		
	}

	//statement-block := "{" statement-list "}"
	private void statementBlock() {
		// TODO Auto-generated method stub
		enterRule(NonTerminal.STATEMENT_BLOCK);
		expect(Token.Kind.OPEN_BRACE);
		statementList();
		expect(Token.Kind.CLOSE_BRACE);
		exitRule(NonTerminal.STATEMENT_BLOCK);
		
	}

	//statement-list := { statement } .
	private void statementList() {
		// TODO Auto-generated method stub

		enterRule(NonTerminal.STATEMENT_LIST);
		statement();
		//System.out.println(this.parseTreeReport());
		while(this.currentToken.kind != Token.Kind.CLOSE_BRACE){
			//System.out.println(this.parseTreeReport());
			statement();
			
		}

		exitRule(NonTerminal.STATEMENT_LIST);
		
	}

	/*statement := variable-declaration
           | call-statement
           | assignment-statement
           | if-statement
           | while-statement
           | return-statement . */
	private void statement() {
		// TODO Auto-generated method stub
		enterRule(NonTerminal.STATEMENT);
		if(this.currentToken.kind == Token.Kind.VAR)
			this.variableDeclaration();
		else if(this.currentToken.kind == Token.Kind.CALL)
			this.callStatement();
		else if(this.currentToken.kind == Token.Kind.LET)
			this.assignmentStatement();
		else if(this.currentToken.kind == Token.Kind.IF)
			this.ifStatement();
		else if(this.currentToken.kind == Token.Kind.WHILE)
			this.whileStatement();
		else if(this.currentToken.kind == Token.Kind.RETURN)
			this.returnStatement();
		else{
			String errorMessage = reportSyntaxError(Token.Kind.CLOSE_BRACE);
	        throw new QuitParseException(errorMessage);
		}
		
		exitRule(NonTerminal.STATEMENT);
		
	}

	private void callStatement() {
		//call-statement := call-expression ";"
		enterRule(NonTerminal.CALL_STATEMENT);
		this.callExpression();
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.CALL_STATEMENT);
	}

	//return-statement := "return" expression0 ";" .
	private void returnStatement() {
		enterRule(NonTerminal.RETURN_STATEMENT);
		this.expect(Token.Kind.RETURN);
		this.expression0();
		this.expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.RETURN_STATEMENT);
		
	}
//while-statement := "while" expression0 statement-block .
	private void whileStatement() {
		enterRule(NonTerminal.WHILE_STATEMENT);
		expect(Token.Kind.WHILE);
		this.expression0();
		statementBlock();
		exitRule(NonTerminal.WHILE_STATEMENT);
		
	}

	//if-statement := "if" expression0 statement-block [ "else" statement-block ] .
	private void ifStatement() {
		enterRule(NonTerminal.IF_STATEMENT);
		expect(Token.Kind.IF);
		expression0();
		statementBlock();
		if(accept(Token.Kind.ELSE))
			statementBlock();
		exitRule(NonTerminal.IF_STATEMENT);
	}

	//assignment-statement := "let" designator "=" expression0 ";"
	private void assignmentStatement() {
		enterRule(NonTerminal.ASSIGNMENT_STATEMENT);
		expect(Token.Kind.LET);
		designator();
		expect(Token.Kind.ASSIGN);
		expression0();
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.ASSIGNMENT_STATEMENT);
		
	}

	//type := IDENTIFIER .
	private void type() {
		enterRule(NonTerminal.TYPE);
		expect(Token.Kind.IDENTIFIER);
		exitRule(NonTerminal.TYPE);
		
	}
	//parameter-list := [ parameter { "," parameter } ] .
	private void paramerterList() {
		// TODO Auto-generated method stub
		enterRule(NonTerminal.PARAMETER_LIST);
		if(currentToken.kind != Token.Kind.CLOSE_PAREN){
			parameter();
			while(accept(Token.Kind.COMMA))
				parameter();
		}
	
		exitRule(NonTerminal.PARAMETER_LIST);
	}

	//parameter := IDENTIFIER ":" type .
	private void parameter() {
		enterRule(NonTerminal.PARAMETER);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.COLON);
		type();
		exitRule(NonTerminal.PARAMETER);
	}

	//array-declaration := "array" IDENTIFIER ":" type "[" INTEGER "]" { "[" INTEGER "]" } ";"
	private void arrayDeclaration() {
		enterRule(NonTerminal.ARRAY_DECLARATION);
		expect(Token.Kind.ARRAY);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.COLON);
		this.type();
		expect(Token.Kind.OPEN_BRACKET);
		expect(Token.Kind.INTEGER);
		expect(Token.Kind.CLOSE_BRACKET);
		while(this.accept(Token.Kind.OPEN_BRACKET)){
			expect(Token.Kind.INTEGER);
			expect(Token.Kind.CLOSE_BRACKET);
		}
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.ARRAY_DECLARATION);
		
	}

	//variable-declaration := "var" IDENTIFIER ":" type ";"
	private void variableDeclaration() {
		enterRule(NonTerminal.VARIABLE_DECLARATION);
		expect(Token.Kind.VAR);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.COLON);
		type();
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.VARIABLE_DECLARATION);
	}

	/*expression0 := expression1 [ op0 expression1 ] .
	*/
	private void expression0() {

		enterRule(NonTerminal.EXPRESSION0);
		expression1();
		if(this.currentToken.kind != Token.Kind.SEMICOLON){
			if(NonTerminal.OP0.firstSet().contains(this.currentToken.kind)){
				op0();
				expression1();
			}
		}
		exitRule(NonTerminal.EXPRESSION0);
		//System.out.println(this.parseTreeReport());
	}

	//op0 := ">=" | "<=" | "!=" | "==" | ">" | "<" .
	private void op0() {
		enterRule(NonTerminal.OP0);
		expect(currentToken.kind);
		exitRule(NonTerminal.OP0);
		
	}

	//expression1 := expression2 { op1  expression2 } .
	private void expression1() {
		enterRule(NonTerminal.EXPRESSION1);
		expression2();
		while(NonTerminal.OP1.firstSet().contains(this.currentToken.kind)){
			op1();
			expression2();
		}
		exitRule(NonTerminal.EXPRESSION1);
	}

	//op1 := "+" | "-" | "or" .
	private void op1() {
		enterRule(NonTerminal.OP1);
		expect(this.currentToken.kind());
		exitRule(NonTerminal.OP1);
		
	}

	//expression2 := expression3 { op2 expression3 } .
	private void expression2() {
		enterRule(NonTerminal.EXPRESSION2);
		expression3();
		while(NonTerminal.OP2.firstSet().contains(this.currentToken.kind())){
			op2();
			expression3();
		}
		
		exitRule(NonTerminal.EXPRESSION2);
	}
	
	//op2 := "*" | "/" | "and" 
	private void op2() {
		enterRule(NonTerminal.OP2);
		expect(this.currentToken.kind());
		exitRule(NonTerminal.OP2);
		
	}

	/*expression3 := "not" expression3
		       | "(" expression0 ")"
		       | designator
		       | call-expression
		       | literal . */
	private void expression3() {
		enterRule(NonTerminal.EXPRESSION3);
		
		if(this.currentToken.kind == Token.Kind.NOT){
			expect(Token.Kind.NOT);
			expression3();
		}
		else if(this.currentToken.kind == Token.Kind.OPEN_PAREN){
			expect(Token.Kind.OPEN_PAREN);
			expression0();
			expect(Token.Kind.CLOSE_PAREN);
		}else if(this.currentToken.kind == Token.Kind.IDENTIFIER){
			designator();
		}else if(this.currentToken.kind == Token.Kind.CALL){
			callExpression();
		}else if(isLiteral()){
			literal();
		}
		exitRule(NonTerminal.EXPRESSION3);
		//System.out.println(this.parseTreeReport());
	}		

	private boolean isLiteral() {
		String token = currentToken.lexeme();
		
		return (token.equals("true") || token.equals("false") || token.matches(stringMatch) || token.matches(numberMatch) && token.contains("."));

	
	}

	//call-expression := "::" IDENTIFIER "(" expression-list ")" .
	private void callExpression() {
		enterRule(NonTerminal.CALL_EXPRESSION);
		expect(Token.Kind.CALL);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.OPEN_PAREN);
		expressionList();
		expect(Token.Kind.CLOSE_PAREN);
		exitRule(NonTerminal.CALL_EXPRESSION);
		
	}

	private void expressionList() {
		enterRule(NonTerminal.EXPRESSION_LIST);
		//expression-list := [ expression0 { "," expression0 } ] .
		if(NonTerminal.EXPRESSION0.firstSet.contains(this.currentToken.kind)){
			this.expression0();
			while(accept(Token.Kind.COMMA)){
				expression0();
			}
		}
		exitRule(NonTerminal.EXPRESSION_LIST);
	}
    

}
