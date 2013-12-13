public abstract class Atom implements Sexp {
	boolean isAtom() { return true; }
	Sexp eval() { return this; }
	Object value() { return value; }
}

