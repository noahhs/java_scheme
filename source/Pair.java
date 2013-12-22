import java.io.OutputStream;

public class Pair extends Expression {
	
	// uh-oh, this suggests a rethink of the lexing process. Bringing back the "reverse" idea. No backward-linking. No nested Term class.
	private Expression head;
	private Expression tail;
	
	public Pair (Expression head, Expression tail) {
		this.head = head;
		this.tail = tail;
	}
		
	public void append (Expression term) {
		last = new Term(term, last);
		if (first == null) { first = last; }
	}

	public String toString () {
		if (last == null) {
			return "()";
		} else {
			return "(" + last.toString() + ")";
		}
	}

	public EvalType eval (Closure closure, OutputStream ostream) {
		if (head == null) { Expression.evalNull(); }
		Expression application = head.eval(closure, ostream);
		// first check special forms
		if (
	}
}
