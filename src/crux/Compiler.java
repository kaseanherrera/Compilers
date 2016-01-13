package crux;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Compiler {
    public static String studentName = "kaseanherrera";
    public static String studentID = "kaseanh";
    public static String uciNetID = "33531582";
	
	public static void main(String[] args)
	{
        String sourceFile = args[0];
        Scanner s = null;

        try {
            s = new Scanner(new FileReader(sourceFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error accessing the source file: \"" + sourceFile + "\"");
            System.exit(-2);
        }

        Token t = s.next();
        while (t.lexeme() != "EOF") {
                System.out.println(t.toString());
                t = s.next();
        }
        System.out.println(t);
    }
}
