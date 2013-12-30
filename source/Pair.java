import java.io.OutputStream;

public class Pair extends Returnable {
	
	private Pairable head;
	private Pairable tail;
	
	public Pair (Pairable head, Pairable tail) {
		this.head = head;
		this.tail = tail;
	}

	public Pairable car () { return head; }

	public Pairable cdr () { return tail; }

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
