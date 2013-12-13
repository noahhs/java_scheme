public class Lambda implements Sexp {

	private Sexp params;
	private Sexp body;

	public Lambda(Sexp params, Sexp body) {
		this.params = params;
		this.body = body;
	}

	public Sexp eval(Sexp args) {

	public boolean isAtom() { return true; }

}

