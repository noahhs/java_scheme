import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
		while(true) {
			Token token = read_token();
			switch (token.type()) {
				case LEFT_PAREN:	level += 1;
										break;
				case RIGHT_PAREN:	level -= 1;
										break;
			}
			tokens.add(token);
			if (level == 0) {
				return tokens;
			}
		}
	}

	private Token read_token () {
		int ch = 0;
		try {
			ch = istream.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Character.isWhitespace(ch)) {
			return read_token();
		}
		switch(ch) {
			case '(':	return new Token(Token.TokenType.LEFT_PAREN);
			case ')':	return new Token(Token.TokenType.RIGHT_PAREN);
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
			case ')':	try {
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
			str = str + ch;
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
