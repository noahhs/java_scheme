import java.io;
import java.util;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Deque;
//import java.util.HashTable;
//import java.util.Map;

public class Runtime {

	private InputStream istream;
	private OutputStream ostream;
	private List environment;
	
	public Runtime (InputStream inp, OutputStream out, EnvironmentFrame initialBindings) {
		istream = inp;
		ostream = out;
		environment = new List(initialBindings, null);
	}

	public void addBinding (Symbol symbol, BoundExpression bound) {
		((EnvironmentFrame)environment.car()).bind(symbol, bound);
	}

	public BoundExpression resolve (Symbol symbol) {
		return resolveRest(symbol, environment);
	}
	
	private BoundExpression resolveRest (Symbol symbol, List frames) {
		BoundExpression found = ((EnvironmentFrame)frames.car()).resolve(symbol);
		if (found != null) {
			return found;
		} else if (frames.cdr() == null) {
			return null;
		} else {
			return resolveRest(symbol, frames.cdr());
		}
	}
	
	public void evalNull() { throwEval("Attempt to eval null"); }

	public OutputStream ostream () { return ostream; }

	public ArrayList<Expression> read () {
		Deque<Expression> stack = stackUpTerms("\n");
		ArrayList<Expression> list = new ArrayList<Expression>;
		while(!stack.empty()) {
			list.add(0, stack.pop());
		}
		return list;
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
				stack.push(buildExpression(stackUpTerms(")")));
			}
			// add (") for string reader
			// add (') for quote reader
			if (ch == "." && whitespaceFollows()) {
				if (terminator == ")") {
					// will want to test this with null
					stack.push(validEnd());
					break;
				} else {
					throwRead("Illegal use of '.'");
				}
			}
			if (Character.isDigit(ch)) {
				stack.push(readNumeric(ch));
			}
			if (!Character.isWhitespace(ch)) {
				stack.push(readSymbol(ch));
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
		// read the end of the form as though it were a list.
		// If only a single term remains, the stack size will be 2, with null on top.
		if (remainingTerms.size() == 2 && remainingTerms.pop() == null) {
			return remainingTerms.pop();
		} else {
			istream.reset();
			throwRead("Illegal use of '.'");
			return null; // null indicates invalid
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

	// the last frame on the stack will be null if a list, an expression if a pair.
	// null		null
	// (cons e null)	e null
	// (cons e1 e2)	e1 e2
	private Expression buildExpression (Stack<Expression> stack) {
		Expression head = stack.pop();
		if (stack.empty()) { return head; }
		return new Pair(head, buildExpression(stack));
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

	private Symbol readSymbol (int ch) {
		String str = "";
		while(true) {
			if (whitespaceOrParen(ch)) {
				return new Symbol(str);
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
