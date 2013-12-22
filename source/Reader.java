import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;

public class Reader {

	private InputStream istream;

	Interpreter (InputStream inp) {
		istream = inp;
	}

	public ArrayList<Expression> read () {
		Deque<Expression> stack = stackUpTerms("\n");
		ArrayList<Expression> list = new ArrayList<Expression>;
		while(!stack.empty()) { list.add(0, stack.pop()); }
	}
	
	private Deque<Expression> stackUpTerms (String terminator) {
		Deque<Expression> stack = new Deque<Expression>();
		while (true) {
			int ch = getChar();
			if (ch == ")" && terminator == "\n") {
				throw new InvalidReadException("ERROR: Too many closing parens.");
			}
			if (ch == terminator) {
				stack.push(null);
				break;
			}
			if (ch == "(") {
				stack.push(readCompound());
			}
			// add (") for string reader
			// add (') for quote reader
			if (ch == "." && whitespaceFollows()) {	
				Expression validEnd;
				if (terminator == ")" && (validEnd = validEnd()) != null) {
					stack.push(validEnd);
				} else {
					throwRead("Illegal use of '.'");
				}
				break;
			}
			if (Character.isDigit(ch)) {
				stack.push(readNumeric(ch));
			}
			if (!Character.isWhitespace(ch)) {
				stack.push(readIdentifier(ch));
			}
		}
		return stack;
	}

	private Boolean whitespaceFollows () {
		istream.mark(1);
		if(Character.isWhitespace(getChar())) {
			return true;
		} else {
			istream.reset();
			return false;
		}
	}
	
	private Expression validEnd () {
		istream.mark(2147483647);
		Deque<Expression> remainingTerms = stackTerms(")");
		if (remainingTerms.size() == 2 && remainingTerms.pop() == null) {
			return remainingTerms.pop();
		} else {
			istream.reset();
			return null;
		}
	}

	private int getChar () {
		int ch;
		try {
			ch = istream.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ch;
	}

	private Expression readCompound () {
		return buildExpression(stackUpTerms(")"));
	}

	private Expression buildExpression (Stack<Expression stack) {
		if (stack.empty()) { return null; }
		Expression head = stack.pop();
		if (stack.empty()) { return head; }
		return buildExpression(stack).cons(head);
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

	private Identifier readIdentifier (int ch) {
		String str = "";
		while(true) {
			if (whitespaceOrParen(ch)) {
				return new Identifier(str);
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

	private NumericType readNumeric (int ch) {
		String str = "";
		Boolean hasDot = false;
		while(true) {
			if (whitespaceOrParen(ch)) {
				if (hasDot) {
					return new DoubleType(Double.valueOf(str));
				} else {
					return new IntegerType(Integer.valueOf(str));
				}
			}
			if (ch == '.') {
				hasDot = true;
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
	
	public class InvalidReadException extends Exception {
		public InvalidReadException (String str) {
			super(str);
		}
	}
}
