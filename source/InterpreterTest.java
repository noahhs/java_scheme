import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;

public class InterpreterTest {
	private static Interpreter interpreter;
	private static InputStream stubInputStream;
	private static ByteArrayOutputStream stubOutputStream = new ByteArrayOutputStream();
	private static ArrayList<String> tests = new ArrayList<String>();
	private static ArrayList<Boolean> results = new ArrayList<Boolean>();

	public static void main (String[] args) {
		String testString = "(foo (bar baz (foo) bar)" + "\n" + "baz)";
		stubInputStream = IOUtils.toInputStream(testString);
		interpreter = new Interpreter(stubInputStream, stubOutputStream);
		
		// test lex
		ArrayList<Token> rightResult = new ArrayList<Token>();

		rightResult.add(new Token(Token.TokenType.LEFT_PAREN));
		rightResult.add(new Token("foo"));
		rightResult.add(new Token(Token.TokenType.LEFT_PAREN));
		rightResult.add(new Token("bar"));
		rightResult.add(new Token("baz"));
		rightResult.add(new Token(Token.TokenType.LEFT_PAREN));
		rightResult.add(new Token("foo"));
		rightResult.add(new Token(Token.TokenType.RIGHT_PAREN));
		rightResult.add(new Token("bar"));
		rightResult.add(new Token(Token.TokenType.RIGHT_PAREN));
		rightResult.add(new Token("baz"));
		rightResult.add(new Token(Token.TokenType.RIGHT_PAREN));
		
		ArrayList<Token> result = interpreter.lex();
		tests.add("Lex test");
		results.add(arrayListsEqual(result, rightResult));

		//display results
		for (int x = 0; x < tests.size(); x += 1) {
			System.out.println(tests.get(x) + "\t" + results.get(x));
			//System.out.println(rightResult);
			//System.out.println(result);
		}
	}

	private static Boolean arrayListsEqual(ArrayList<Token> al1, ArrayList<Token> al2) {
		if (al1.size() != al2.size()) { 
			System.out.println("Different size!");
			return false; }
		Boolean goodSoFar = true;
		for (int x = 0; x < al1.size(); x += 1) {
			if (!goodSoFar) { return false; }
			if(al1.get(x).getClass() != al2.get(x).getClass()) { 
				System.out.println("Different class!");
				return false; }
			goodSoFar = (al1.get(x).equal(al2.get(x)));
			if (!goodSoFar) { System.out.println("Not equal at x = " + x); }
		}
		return goodSoFar;
	}

	private static void printArrayList (ArrayList<Token> al) {
		for (int x = 0; x < al.size(); x += 1) {
			if (x > 0) {
				System.out.print(", ");
			}
			System.out.print(al.get(x));
		}
		System.out.println();
	}
}
