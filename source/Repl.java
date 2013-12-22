import java.util.ArrayList;

public class Repl {
	public static void main (String[] args) {
		Reader reader = new Reader(System.in);
		Closure closure = new Closure();
		while(true) {
			System.out.print(">> ");
			ArrayList<Expression> expressions = reader.read();
			for (Expression expression : expressions) {
				if (expression == null) { Expression.evalNull(); }
				Expression result = expression.eval(closure, System.out);
				result.print(System.out);
			}
		}
	}
}
