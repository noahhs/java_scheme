public class List extends Pair {

	public List(Pairable head, List tail) {
		this.head = head;
		this.tail = tail;
	}
	
	// clean up later
	public String toString () {
		return "(" + SpecialForm.stringByType.get(type) + argsToString(arguments);
	}
	
	@Override
	public List cdr () { return (List)super.cdr(); }
	@Override
	public List cons (Pairable exp) { return (List)super.cons(exp); }
	@Override
	public Boolean isList () { return true; }

	private String argsToString (List arguments) {
		if (arguments == null) {
			return ")";
		} else {
			return " " + arguments.car().toString() + argsToString(arguments.cdr());
		}
	}

	private List compiledTerms (Runtime runtime) {
		Expression compiledHead = this.car().compile(runtime);
		if (this.cdr() == null) {
			  return new List(compiledHead, null);
		} else {
			return this.cdr().compiledTerms(runtime).cons(compiledHead);
		}
	}

	public List evaluatedTerms (Runtime runtime) {
		Expression evaluatedHead = this.car().eval(runtime);
		if (this.cdr() == null) {
			return List(evaluatedHead, null);
		} else {
			return this.cdr().evaluatedTerms(runtime).cons(evaluatedHead);
		}
	}

	public Compiled compile (Runtime runtime) {
		if (head.isCompound()) {
			if (((Pair)head).isList()) {
				return new Application((List)head, tail, runtime);
			} else {
				throwCompile("Application: head not a procedure");
				return null;
			}
		} else if ((Atom)head.isSymbol()) {
			BoundExpression bound = runtime.resolve((Symbol)head);
			if (bound == null && (SpecialForm.Keyword keyword = SpecialForm.keyword((Symbol)head)) != null) {
				return specialForm(keyword, runtime);
			}
			return new Application(new Variable((Symbol)head, bound), this, runtime);
		} else {
			// could there be a self-evaluating procedure in here? Think about later.
			throwCompile("Application: head not a procedure");
			return null;
		}
	}

	private SpecialForm specialForm(SpecialForm.Keyword keyword, Runtime runtime) {
		switch (keyword) {
			case DEFINE:	Returnable arg1 = tail.arg(1, Symbol);
						Returnable arg2 = tail.arg(2, Returnable);
						if (arg1 != null && arg2 != null) {
							return new DefineForm((Symbol)arg1, (Returnable)arg2, runtime);
						} else {
							throwCompile("Bad syntax in define form");// should we put this checking logic in the constructor?
							return null;
						}
			case LAMBDA:	if (tail != null & tail.cdr() != null && (Expression arg1 = tail.car()).isCompound()) {
							  Expression arg2 = tail.cdr().car();

			case LET:		return null;//return new LetForm(list, runtime);
			case LET_SPLAT:	return null;//return new LetSplatForm(list, runtime);
			case QUOTE:		return null;//return new QuoteForm(list, runtime);
			case BEGIN:		return null;//return new BeginForm(list, runtime);
			default:		return null;
		}
	}

	private Returnable arg (int argNum, Class dass) {
		if (argNum == 1) {
			if (dass.isAssignableFrom(head.class) {
				return head;
			} else {
				return null;
			}
		} else {
			return tail.arg(argNum - 1, dass);
		}
	}
}
