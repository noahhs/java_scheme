import java.util.ArrayList;

public class Repl {
	public static void main (String[] args) {
		Interpreter interpreter = new Interpreter(System.in, System.out);

		while(true) {
			ArrayList<Token> tokens = interpreter.lex();
			Sexp sexp = interpreter.parse(tokens);
			interpreter.eval(sexp);
		}
	}
}
