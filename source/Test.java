import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;

public class Test {
	private static Reader reader;
	private static InputStream stubInputStream;
	private static ByteArrayOutputStream stubOutputStream = new ByteArrayOutputStream();
	private static ArrayList<String> tests = new ArrayList<String>();
	private static ArrayList<Boolean> results = new ArrayList<Boolean>();

	public static void main (String[] args) {
		String e1 = "(1 (bar 2.22 (foo) bar)";
		String e2 = "baz)";
		String e3 = "3.3";
		String testString = e1 + "\n" + e2 + e3 + "\n" + "bar" + "\n";
		stubInputStream = IOUtils.toInputStream(testString);
		reader = new Reader(stubInputStream);
		
		ArrayList<Expression> expressions;
		String result;
		String rightResult;
		
		// test Reader#read
		try {
			expressions = reader.read();
			result = expressions.toString();
		} catch (Exception e) {
			result = e.toString();
			System.out.println(result);
		}
		rightResult = "[" + e1 + " " + e2 + ", " + e3 + "]";
		tests.add("Read");
		results.add(result.equals(rightResult));

		// test Expression#eval
		tests.add("Token result");
		results.add(evalIsCorrect(stubOutputStream,"1","1",""));

		tests.add("Compound result");
		results.add(evalIsCorrect(stubOutputStream,"(+ 1 2)","3",""));
		
		tests.add("Output");
		results.add(evalIsCorrect(stubOutputStream,"(display \"hello world\")","","hello world"));

		//display results
		for (int x = 0; x < tests.size(); x += 1) {
			System.out.println(tests.get(x) + "\t" + results.get(x));
			//System.out.println(rightResult);
			//System.out.println(result);
		}
	}
	
	private Boolean evalIsCorrect (OutputStream ostream, String inputString, String rightResult, String rightOutput) {
		ostream.reset();
		Expression expression = (new Reader(IOUtils.toInputStream(inputString))).read().first();
		String result = expression.eval(ostream);
		return expression.eval(ostream).equals(rightResult) && ostream.toString().equals(rightOutput);
	}
}
