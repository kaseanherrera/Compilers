package crux;
import java.util.HashSet;
import java.util.Set;

public enum NonTerminal {
    
    // TODO: mention that we are not modeling the empty string
    // TODO: mention that we are not doing a first set for every line in the grammar
    //       some lines have already been handled by the CruxScanner
    
  /*  DESIGNATOR(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
            add(Token.Kind.IDENTIFIER);
            add(Token.Kind.OPEN_BRACKET);
            addAll(EXPRESSION0.firstSet);
            add(Token.Kind.CLOSE_BRACKET);
            
        }}),*/
    TYPE(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        	add(Token.Kind.IDENTIFIER);
        }}), /*
    LITERAL(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
            add(Token.Kind.INTEGER);
            add(Token.Kind.FLOAT);
            add(Token.Kind.TRUE);
            add(Token.Kind.FALSE);
        }}),
    CALL_EXPRESSION(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
     
        }}),
    OP0(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
      
       }}),
    OP1(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        
       }}),
    OP2(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
         
       }}),
    EXPRESSION3(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {

       }}),
    EXPRESSION2(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
    
        }}),
    EXPRESSION1(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
 
        }}),
    EXPRESSION0(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
   
        }}),
    EXPRESSION_LIST(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        
        }}),
    PARAMETER(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        	add(Token.Kind.IDENTIFIER);
        	add(Token.Kind.COLON);
        	addAll(TYPE.firstSet);
        }}),
    PARAMETER_LIST(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        	addAll(PARAMETER.firstSet);
        	add(Token.Kind.COMMA);
        	
        }}), */
    VARIABLE_DECLARATION(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        	add(Token.Kind.VAR);
        	add(Token.Kind.IDENTIFIER);
        	add(Token.Kind.COLON);
        	addAll(TYPE.firstSet);
        	add(Token.Kind.SEMICOLON);
        }}),
    ARRAY_DECLARATION(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
           
        }}), /*
    FUNCTION_DEFINITION(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        	add(Token.Kind.FUNC);
        	add(Token.Kind.IDENTIFIER);
        	add(Token.Kind.OPEN_PAREN);
        	addAll(PARAMETER_LIST.firstSet);
            add(Token.Kind.COLON);
            addAll(TYPE.firstSet);
            addAll(STATEMENT_BLOCK.firstSet);
        }}), */
    DECLARATION(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
            addAll(VARIABLE_DECLARATION.firstSet);
            addAll(ARRAY_DECLARATION.firstSet);
            addAll(ARRAY_DECLARATION.firstSet);
        }}),
    DECLARATION_LIST(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
            addAll(DECLARATION.firstSet);
        }}), 
  /*  ASSIGNMENT_STATEMENT(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
      
        }}),
    CALL_STATEMENT(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
  
        }}),
    IF_STATEMENT(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
      
        }}),
    WHILE_STATEMENT(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {

        }}),
    RETURN_STATEMENT(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {

        }}),
    STATEMENT_BLOCK(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
           add(Token.Kind.OPEN_BRACKET);
     	   addAll(STATEMENT_LIST.firstSet);
     	   add(Token.Kind.CLOSE_BRACKET);
        }}),
    STATEMENT(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {

        }}),
    STATEMENT_LIST(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
      
        }}), */
    PROGRAM(new HashSet<Token.Kind>() {
        private static final long serialVersionUID = 1L;
        {
        	addAll(DECLARATION_LIST.firstSet);
        	add(Token.Kind.EOF);
        }});
           
    public final HashSet<Token.Kind> firstSet = new HashSet<Token.Kind>();

    NonTerminal(HashSet<Token.Kind> t)
    {
        firstSet.addAll(t);
    }
    
    public final Set<Token.Kind> firstSet()
    {
        return firstSet;
    }
}
