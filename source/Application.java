public class Application extends Expression implements Compiled {
	
	private Compiled applicative;
	private List arguments;//should really have a parametric List<Compiled> type

	public Application (List head, List tail, Runtime runtime) {
		applicative = head.compile(runtime);
		arguments = compiledTerms(tail, runtime);
	}
	
	public Application (Variable head, List tail, Runtime runtime) {//we've already determined it's not a keyword
		applicative = head;
		arguments = compiledTerms(tail, runtime);
	}

	private List compiledTerms (List list, Runtime runtime) {
		if (list == null) {
			return null;
		} else {
			return list.compiledTerms(runtime);
		}
	}

	public Expression eval (Runtime runtime) {
		Expression result = applicative.eval(runtime);
		if (Procedure.isAssignableFrom(result.class) {// if this doesn't work, we could use Class.forName("Procedure")
		//if (!result.isCompound() && ((Atom)result).isSelfEvaluating() && ((SelfEvaluating)result).isProcedure()) {
			return ((Procedure)result).apply(arguments, runtime);
		} else {
			throwEval("Applicative is not a procedure");
			return null;
		}
	}
}
