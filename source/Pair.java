import java.io.OutputStream;

public class Pair extends Returnable {
	
	private Expression head;
	private Expression tail;
	
	public Pair (Expression head, Expression tail) {
		this.head = head;
		this.tail = tail;
	}

	public Expression car () { return head; }

	public Expression cdr () { return tail; }

	public Boolean isSymbol () { return false; }
	
	public Expression eval (Runtime runtime) {
		return compile(runtime).eval(runtime);
	}

	public Boolean isCompound () { return true; }

	public Boolean isList () {
		if (tail == null) {
			return true;
		} else if (tail.isCompound()) {
			return ((Pair)tail).isList();
		} else {
			return false;
		}
	}

	public Compiled compile (Runtime runtime) {
		if (!isList()) { throwCompile("Cannot compile non-list pair"); }
		return ((List)this).compile(runtime);
	}
}
