import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Interpreter {

	private InputStream istream;
	private OutputStream ostream;

	Interpreter (InputStream inp, OutputStream out) {
		istream = inp;
		ostream = out;
	}

	public ArrayList<TreeTerm> read () throws ParenException {
		// At the top level, interpret a sequence of expressions without enclosing parentheses.
		ArrayList<TreeTerm> topLevelTerms = new ArrayList<TreeTerm>();
		Stack<TokenTree> openTerms = new Stack<TokenTree>();

		while(true) {
			Boolean canTerminate = (openTerms.empty());
			//System.out.println("canTerminate = " + canTerminate);
			Token newToken = read_token(canTerminate);
			//System.out.println("newToken = " + newToken.toString());
			switch (newToken.type()) {
				case LEFT_PAREN:	//System.out.println("newToken.type() = " + newToken.type());
							TokenTree newTree = new TokenTree();
							//System.out.println("newTree = " + newTree.toString());
							if (canTerminate) {
								topLevelTerms.add(newTree);
							} else {
								openTerms.peek().add(newTree);
							}
							//System.out.println("Pushed item: " + openTerms.push(newTree));
							openTerms.push(newTree);
							break;
				case RIGHT_PAREN:	if (canTerminate) {
								throw new ParenException("ERROR: Too many closing parens.");
							} else {
								//System.out.println("Popped item: " + openTerms.pop());
								openTerms.pop();
							}
							break;
				case TERMINATE:	return topLevelTerms;
				default:		if (canTerminate) {
								topLevelTerms.add(newToken);
							} else {
								openTerms.peek().add(newToken);
							}
			}
		}
		

	}

	public class ParenException extends Exception {
		public ParenException (String str) {
			super(str);
		}
	}

	private Token read_token (Boolean canTerminate) {
		int ch = 0;
		try {
			ch = istream.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("ch = " + (char)ch);
		switch(ch) {
			case '(':	//System.out.println("It's a '('!");
					return new Token(Token.TokenType.LEFT_PAREN);
			case ')':	return new Token(Token.TokenType.RIGHT_PAREN);
			case '\n':	if (canTerminate) { return new Token(Token.TokenType.TERMINATE); }
			// To do: handle EOF
		}
		if (Character.isWhitespace(ch)) {
			return read_token(canTerminate);
		}
		if (Character.isDigit(ch)) {
			return read_numeric(ch);
		} else {
			return read_identifier(ch);
		}
	}

	private Boolean whitespaceOrParen(int ch) {
		switch(ch) {
			case '(':
			case ')':
			case '\n':	try {
						istream.reset();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
			default:	return Character.isWhitespace(ch);
		}
	}

	private Token read_identifier (int ch) {
		String str = "";
		while(true) {
			if (whitespaceOrParen(ch)) {
				return new Token(str);
			}
			str = str + (char)ch;
			istream.mark(1);
			try {
				ch = istream.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Token read_numeric (int ch) {
		String str = "";
		Boolean hasDot = false;
		while(true) {
			if (whitespaceOrParen(ch)) {
				if (hasDot) {
					return new Token(Double.valueOf(str));
				} else {
					return new Token(Integer.valueOf(str));
				}
			}
			if (ch == '.') {
				hasDot = true;
			}
			str = str + ch;
			istream.mark(1);
			try {
				ch = istream.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//public Sexp parse (ArrayList<Token> tokens) {
	//	return new List();
	//}

	//public void eval (Sexp sexp) {
	//	return new List();
	//}
}
