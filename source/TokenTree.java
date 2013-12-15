import java.io.OutputStream;

public class TokenTree implements TreeTerm {
	// Will eventually be a doubly linked list
	
	private class Term {
		private TreeTerm contents;
		private Term previous;
	
		public Term (TreeTerm contents, Term previous) {
			this.contents = contents;
			this.previous = previous;
		}

		public String toString() {
			if (previous == null) {
				return contents.toString();
			} else {
				return previous.toString() + " " + contents.toString();
			}
		}
	}
		
	private Term last;

	public void add (TreeTerm term) {
		last = new Term(term, last);	
	}

	public String toString() {
		if (last == null) {
			return "()";
		} else {
			return "(" + last.toString() + ")";
		}
	}

	public Object eval (OutputStream ostream) {
		return "";
	}
}

	//public boolean isEmpty() { return (first == null); }

	//public Sexp car() { return first.sexp; }

	//public List cdr() {
	//	List newList = new List();
	//	newList.first = first.next;
	//	return newList;
	//}

	//public List cons(Sexp sexp) {
	//	Term term = new Term();
	//	term.sexp = sexp;
	//	term.next = first;
	//	List newList = new List();
	//	newList.first = term;
	//	return newList;
	//}

	//public String toString() {
	//	String string = "(";
	//	Term term = first;
	//	while (term != null) {
	//		if (term != first) {
	//			string += " ";
	//		}
	//		string += term.sexp;
	//		term = term.next;
	//	}
	//	string += ")";
	//	return string;
	//}

	//public Sexp apply(Lambda lambda) {
	//	return lambda.eval(this);
	//}

	//public Sexp eval() {
	//	if(this.isEmpty()) {
	//		return this;
	//	}
	//	Sexp first = this.car().eval();
	//	Sexp rest = this.cdr();

	//	if(first.getClass() == Function) {
	//		return this.cdr().apply(first);
	//	}
	//	return partial;
	//}

	//public Sexp apply(Function function) {
	//	return function.eval(this.evalTerms());
	//}

	//private List evalTerms() {
	//	if (this.isEmpty()) { return this; }
	//	return this.cdr().evalTerms().cons(this.car().eval());
	//}
