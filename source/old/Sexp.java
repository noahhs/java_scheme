public interface Sexp {
	boolean isAtom();
	Sexp eval();
}
