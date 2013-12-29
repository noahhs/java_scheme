import java.util.ArrayList;

public class Repl {
	public static void main (String[] args) {
		Runtime runtime = new Runtime(System.in, System.out);
		String replPrompt = "<repl> ";
		while(true) {
			System.out.print(replPrompt);
			ArrayList<Expression> expressions = runtime.read();
			for (Expression expression : expressions) {
				if (expression == null) { Expression.evalNull(); }
				else {
					Expression result = expression.eval(runtime);
					result.print(runtime);
				}
			}
		}
	}
}
