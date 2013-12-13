import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;

public class InterpreterTest {
	private static Interpreter interpreter;
	private static InputStream stubInputStream;
	private static ByteArrayOutputStream stubOutputStream;
	private static ArrayList<String> tests;
	private static ArrayList<Boolean> results;

	public static void main (String[] args) {
		String testString = "(foo bar)";
		stubInputStream = IOUtils.toInputStream(testString);
		interpreter = new Interpreter(stubInputStream, stubOutputStream);
		
		// test lex
		ArrayList<Token> rightResult = new ArrayList<Token>();
		rightResult.add(new Token(Token.TokenType.LEFT_PAREN));
		rightResult.add(new Token("foo"));
		rightResult.add(new Token("bar"));
		rightResult.add(new Token(Token.TokenType.RIGHT_PAREN));
		ArrayList<Token> result = interpreter.lex();
		tests.add("Lex test");
		results.add(result == rightResult);

		//display results
		for (int x = 0; x < tests.size(); x += 1) {
			System.out.println(tests.get(x) + "\t" + results.get(x));
		}
	}

}
