import java.io.OutputStream;

public class Identifier implements Expression {
	private String string;

	public Identifier (String str) {
		string = str;
	}

	public toString () {
		return "'" + string;
	}

	public Expression eval (Closure closure, OutputStream ostream) { eval(closure); }

	public Expression eval (Closure closure) {
		if (isKeyword()) { throwEval("Attempt to eval a keyword"); }
		
		Expression boundExpression = closure.resolve(this);
		if (boundExpression == null) {
			boundExpression = BuiltInProcedure.resolve(this);
		}
		if (boundExpression == null) {
			throwEval("Unbound identifier");
		}
		return boundExpression;
	}
	
	public Boolean equals (Identifier other) { string.equals(other.toString()); } // for Map key
}
