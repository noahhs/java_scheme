public class List<E extends Pairable> extends Pair<Pariable, List<Pairable>> {

	public List(E head, List<E> tail) {
		this.head = head;
		this.tail = tail;
	}
	
	// clean up later
	public String toString () {
		return "(" + SpecialForm.stringByType.get(type) + argsToString(arguments);
	}
	
	@Override
	public List<E> cdr () {
		return (List<E>)super.cdr();
	}
	
	@Override
	public List<E> cons (E exp) {
		return (List<E>)super.cons(exp);
	}

	public int size () {
		if (tail == null) {
			return 1;
		} else {
			return 1 + tail.size();
		}
	}

	private String argsToString (List arguments) {
		if (arguments == null) {
			return ")";
		} else {
			return " " + arguments.car().toString() + argsToString(arguments.cdr());
		}
	}

	public List<Compiled> compiledTerms (Runtime runtime) {
		if (head.satisfies(Returnable)) {
			Compiled compiledHead = head.compile(runtime);
			if (tail == null) {
				return new List<Compiled>(compiledHead, null);
			} else {
				return tail.compiledTerms(runtime).cons(compiledHead);
			}
		} else {
			return throwCompile("Not a compilable object");
		}
	}

	// this should be restricted to List<Expression>
	public List<Returnable> evaluatedTerms (Runtime runtime) {
		if (head.satisfies(Expression)) {
			Returnable evaluatedHead = head.eval(runtime);
			if (tail == null) {
				return List<Returnable>(evaluatedHead, null);
			} else {
				return tail.evaluatedTerms(runtime).cons(evaluatedHead);
			}
		} else {
			return throwCompile("Not a compilable object");
		}
	}

	public Compiled compile (Runtime runtime) {
		if (head.isCompound()) {
			if (((Pair)head).isList()) {
				return new Application((List)head, tail, runtime);
			} else {
				return throwCompile("Application: head not a procedure");
			}
		} else if ((Atom)head.isSymbol()) {
			Variable bound = runtime.resolve((Symbol)head);
			if (bound == null) {
				if ((SpecialForm.Keyword keyword = SpecialForm.keyword((Symbol)head)) != null) {
					return specialForm(keyword, runtime);
				} else {
					return new Application((Symbol)head, tail, runtime);
				}
			}
			return new Application(bound, tail, runtime);
		} else {
			// could there be a self-evaluating procedure in here? Think about later.
			throwCompile("Application: head not a procedure");
			return null;
		}
	}

	private SpecialForm specialForm(SpecialForm.Keyword keyword, Runtime runtime) {
		switch (keyword) {
			case DEFINE:	if (tail.termExistsAndIs(1, Symbol, false) && tail.termExistsAndIs(2, Returnable, false)) {
							return new DefineForm((Symbol)tail.term(1), (Returnable)tail.term(2), runtime);
						} else {
							throwCompile("Bad syntax in define form");
							return null;
						}
			case LAMBDA:	if (tail.termExistsAndIs(1, List, true) && tail.termExistsAndIs(2, Returnable, false)) {
							return new CompoundProcedure((List)tail.term(1), (Returnable)tail.term(2), runtime);
						} else {
							throwCompile("Bad syntax in lambda form");
							return null;
						}
			case COND:		return null;//return new CondForm(list, runtime);
			case QUOTE:		return null;//return new QuoteForm(list, runtime);
			case LET:		return null;//return new LetForm(list, runtime);
			case LET_SPLAT:	return null;//return new LetSplatForm(list, runtime);
			case BEGIN:		return null;//return new BeginForm(list, runtime);
			default:		return null;
		}
	}

	private Boolean termExistsAndIs (int num, Class dass, Boolean allowNull) {
		if (num == 1) {
			if (head == null) {
				return allowNull;
			} else {
				return head.satisfies(dass);
			}
		} else if (tail == null) {
			return false;
		} else {
			return tail.termExistsAndIs(num - 1, dass, allowNull);
		}
	}

	private Returnable term (int num) {
		if (num == 1) {
			return head;
		} else {
			return tail.term(num - 1);
		}
	}
}
