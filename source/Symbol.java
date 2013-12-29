import java.io.OutputStream;

public class Symbol extends Returnable {
	
	private static Map symbols = new HashMap<String, Symbol>();

	private static Symbol retrieve (String string) {
		return symbols.get(string);
	}

	public static Symbol add (Symbol symbol) {
		symbols.put(symbol.string(), symbol);
	}

	private String string;

	public Symbol (String str) {
		string = str;
		Symbol.add(this);
	}

	public String string () { return string; }

	public toString () {
		return "'" + string;
	}

	public Boolean isSymbol () { return true; }

	public Expression compile (Runtime runtime) {
		BoundExpression boundExpression = runtime.resolve(this);
		if (boundExpression == null) {
		} else {
			return new Variable(this, boundExpression);
		}
			boundExpression = BuiltInProcedure.resolve(this);
			if (boundExpression == null) {
				if (isKeyword()) {
					throwEval("Attempt to compile a keyword");
				} else {
					throwEval("Unbound identifier");
				}
				return null;
			}
		}
		return boundExpression;
	}
	
	public Boolean equals (Identifier other) { string.equals(other.toString()); } // for Map key
}
