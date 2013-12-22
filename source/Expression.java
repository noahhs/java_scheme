import java.io.OutputStream;

public abstract class Expression {

	public static void evalNull() { throw new InvalidEvalException("Attempt to eval null"); }

	public String toString ();
	public Expression eval (Closure closure, OutputStream ostream);

	public void print (OutputStream ostream) { ostream.println(this.toString()); }

	public void throwEval (String msg) { throw new InvalidEvalException(msg); }

	public Number value () { throwEval("Attempted math on non-numeric"); }

	public Boolean toBoolean () { throwEval("Attempted logic on non-boolean"); }
	
	public Expression car () { throwEval("Expected pair"); }
	
	public Expression cdr () { throwEval("Expected pair"); }

	public Pair cons (Expression head) { return new Pair(head, this); }
}
