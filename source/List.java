public class List extends Pair {

	private static Map<String, BuiltInProcedure.Type> procType = new HashMap<String, BuiltInProcedure.Type>();
	procType.put("+", PLUS);
	procType.put("-", MINUS);
	procType.put("*", TIMES);
	procType.put("/", DIVIDEDBY);
	procType.put("=", EQUALS);
	procType.put("modulo", MODULO);
	procType.put("^", EXPONENTIAL);
	procType.put("<", LESSTHAN);
	procType.put("display", DISPLAY);
	procType.put("car", CAR);
	procType.put("cdr", CDR);
	procType.put("cons", CONS);

	public List(Expression head, List tail) {
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
	public List cons (Expression exp) { return (List)super.cons(exp); }
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
	

	public Expression compile (Runtime runtime) {
		Compiled compiledHead;
		if (head.isCompound()) {
			compiledHead = head.compile(runtime);
		} else if (head.isSymbol()) {
			BoundExpression bound = runtime.resolve(head);
			if (bound == null) {
				SpecialForm.Keyword keyword = SpecialForm.keyword(head);
				if (keyword == null) {
					compiledHead = Variable(head, null);
				} else {
					switch (keyword) {
						case DEFINE:	if (tail != null && tail.cdr() != null && (Expression arg1 = tail.car()).isSymbol()) {
										Compiled compiledArg2 = tail.cdr().car().compile(runtime);
										return new DefineForm((Symbol)arg1, compiledArg2, runtime);
									} else {
										throwCompile("Bad syntax in define form");
										return null;
									}
						case let":		return null;//return new LetForm(list, runtime);
						case let*:		return null;//return new LetSplatForm(list, runtime);
						case quote":	return null;//return new QuoteForm(list, runtime);
						case begin":	return null;//return new BeginForm(list, runtime);
						default:		return null;
					}
				}
			} else {
				compiledHead = new Variable(head, bound);
			}
		} else {
			// could there be a self-evaluating procedure in here? Think about later.
			throwCompile("Application: not a procedure");
			return null;
		}
		List compiledTail;
		if (tail == null) {
			compiledTail = null;
		} else {
			compiledTail = tail.compiledTerms(runtime);
		}
		return new Application(compiledHead, compiledTail);
	}
}
