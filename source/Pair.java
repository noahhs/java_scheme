import java.io.OutputStream;

public class Pair<E extends Pairable, F extends Pairable> extends Returnable {
	
	protected E head;
	protected F tail;
	
	public Pair (E head, F tail) {
		this.head = head;
		this.tail = tail;
	}

	public E car () { return head; }

	public F cdr () { return tail; }

	public Boolean isSymbol () { return false; }
	
	public Returnable eval (Runtime runtime) {
		return compile(runtime).eval(runtime);
	}

	public Compiled compile (Runtime runtime) {
		return throwCompile("Cannot compile non-list pair");
	}
}
