package crux;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class Scanner implements Iterable<Token> {
	public static String studentName = "Kasean Herrera";
	public static String studentID = "33531582";
	public static String uciNetID = "kaseanh";
	//String charMatches = "(|)|{|}|[|]|+|-|*|/|>|<|=|,|:";
	boolean go;
	String charMatches = "==|=|^[0-9]"; //string that match basic characters
	String ignoreChars = "\n";
	public StringBuilder masterStr;
	private String lastMatch; //tracks the last match that we had
	
	private int lineNum;  // current line count
	private int charPos;  // character offset for current line
	private int nextChar; // contains the next char (-1 == EOF)
	private String nextCharString; //char form of nect char
	private Reader input;
	private int bufferStringPosition;
	
	private int lastMarkedPosition;
	
	
	Scanner(Reader reader)
	{
		//initialize the Scanner
		this.lineNum  = 0;
		this.input = reader;
		
		go = true;
		masterStr = new StringBuilder();
		//readChar();

		this.lastMatch = "";
		lastMarkedPosition = 0;
		bufferStringPosition = 0;
		
	}	
	
	// OPTIONAL: helper function for reading a single char from input
	//           can be used to catch and handle any IOExceptions,
	//           advance the charPos or lineNum, etc.
	
	private int readChar() {
		//if we are at the end of buffer , get char from file
		
		if(bufferStringPosition == masterStr.toString().length()){
			try{
				//get the next char. If there is a new line, add to the line number, and reset position 
				nextChar = input.read();
				nextCharString = Character.toString((char)nextChar);
				//add the next char to the string if it is not in list of ignore characters 
				if(!nextCharString.matches(ignoreChars))
					masterStr.append(nextCharString);
			}catch(IOException ex){
				return -1;
			}
		}
		else{
			//get char from the buffer
			nextChar = masterStr.toString().charAt(bufferStringPosition);
			nextCharString = Character.toString((char)nextChar);
		}
		
		if(nextChar == '\n'){
			lineNum++;
			charPos = 0;   
		}else{
			//add to the current Pos 
			charPos++;
			bufferStringPosition++;
		}
		return nextChar;
		
	}
		

	/* Invariants:
	 *  1. call assumes that nextChar is already holding an unread character
	 *  2. return leaves nextChar containing an untokenized character
	 */
	

	public Token next()
	{		
	
		//get go 
		go = true;
	
		while(go){
			//get the next character 
			readChar();
			
			//check if the next char is one of the 'basic kinds' stop and tokenize
			if((masterStr.toString()).matches(charMatches)){
				//set the last match to the currentString
				lastMatch = masterStr.toString();
				//set the last match position 
				lastMarkedPosition = bufferStringPosition;
			}
			
			//if it matches any of the ignore characters, move on 
			else if((nextCharString).matches(ignoreChars)){
				readChar();
			}
			//does not match anything
			else{
				go = false;
				//set char postion back to last place we match 
				charPos = this.lastMarkedPosition;
				//delete the matched part of the master string 
				masterStr = new StringBuilder(masterStr.toString().substring(lastMarkedPosition, masterStr.length()));
				//move the buffer back 
				bufferStringPosition = 0;
				lastMarkedPosition = 0;	
			}
		}

		//create a token of the last match
		if(lastMatch == ""){
			return new Token("EOF", this.lineNum, this.charPos);
		}
		Token T = new Token(lastMatch, this.lineNum, this.charPos);
		//reset last matched
		lastMatch = "";
		return T;
	}

	@Override
	public Iterator<Token> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	// OPTIONAL: any other methods that you find convenient for implementation or testing
}
