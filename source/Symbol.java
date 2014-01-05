import java.io.OutputStream;

public class Symbol extends Returnable implements Compiled {
	
	private static Map symbols = new HashMap<String, Symbol>();

	private static Symbol retrieve (String string) {
		return symbols.get(string);
	}

	private static Symbol add (Symbol symbol) {
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

	public Compiled compile (Runtime runtime) {
		Variable variable = runtime.resolve(this);
		if (variable == null) {
			if (isKeyword()) {
				return throwCompile("Bad syntax for keyword");
			} else {
				return this;
			}
		} else {
			return variable;
		}
	}

	public Returnable eval (Runtime runtime) {
		Variable variable = runtime.resolve(this);
		if (variable == null) {
			return throwEval("Unbound identifier");
		} else {
			return variable.eval();
		}
	}
}
