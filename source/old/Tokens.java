public class Interpreter {

	private InputStream istream;
	private OutputStream ostream;

	Interpreter (InputStream inp, OutputStream out) {
		istream = inp;
		ostream = out;
	}

	public ArrayList<Token> lex () {
		ArrayList<Token> tokens = new ArrayList<Token>();
		int level = 0;
		while(1) {
			Token token = read_token();
			switch (token.type) {
				case Token.TokenType.LEFT_PAREN:	level += 1;
										break;
				case Token.TokenType.RIGHT_PAREN:	level -= 1;
										break;
			}
			tokens.add(token);
			if (level == 0) {
				return tokens;
			}
		}
	}

	private Token read_token () {
		Character ch = new Character((char)istream.read());
		if (ch.isWhitespace()) {
			return read_token();
		}
		switch (ch.charValue()) {
			case '(':	return new Token(LEFT_PAREN);
			case ')':	return new Token(RIGHT_PAREN);
		}
		if (ch.isDigit()) {
			return read_numeric(ch.charValue());
		} else {
			return read_identifier(ch.charValue());
		}
		Token token = new Token();

	}

	private Token read_identifier () {
	}

	private Token read_numeric () {
	}

	public Sexp parse (ArrayList<Token> tokens) {
	}

	public void eval (Sexp sexp) {
	}
}
