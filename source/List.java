public class List implements Sexp {
	
	public boolean isAtom() { return false; }

	private class Term {
		Sexp sexp;
		Term next;

		Sexp eval() { return sexp.eval(); }
	}

	private Term first;

	public boolean isEmpty() { return (first == null); }

	public Sexp car() { return first.sexp; }

	public List cdr() {
		List newList = new List();
		newList.first = first.next;
		return newList;
	}

	public List cons(Sexp sexp) {
		Term term = new Term();
		term.sexp = sexp;
		term.next = first;
		List newList = new List();
		newList.first = term;
		return newList;
	}

	public String toString() {
		String string = "(";
		Term term = first;
		while (term != null) {
			if (term != first) {
				string += " ";
			}
			string += term.sexp;
			term = term.next;
		}
		string += ")";
		return string;
	}

	public Sexp apply(Lambda lambda) {
		return lambda.eval(this);
	}

	public Sexp eval() {
		if(this.isEmpty()) {
			return this;
		}
		Sexp first = this.car().eval();
		Sexp rest = this.cdr();

		if(first.getClass() == Function) {
			return this.cdr().apply(first);
		}
		return partial;
	}

	public Sexp apply(Function function) {
		return function.eval(this.evalTerms());
	}

	private List evalTerms() {
		if (this.isEmpty()) { return this; }
		return this.cdr().evalTerms().cons(this.car().eval());
	}
}
