public class Repl {
	public static void main (String[] args) {
		Interpreter interpreter = new Interpreter(System.in, System.out);

		while(1) {
			ArrayList<Token> tokens = interpreter.lex();
			Sexp sexp = interpreter.parse(tokens);
			interpreter.eval(sexp);
		}
	}
}
