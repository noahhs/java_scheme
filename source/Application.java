public class Application extends Expression implements Compiled {
	
	private Expression applicative;
	private List arguments;//should really have a parametric List<Compiled> type

	public Application (List head, List tail, Runtime runtime) {
		setTerms(head.compilie(runtime), tail, runtime);
	}
	
	public Application (Variable head, List tail, Runtime runtime) {
		setTerms(head, tail, runtime);
	}

	public Application (Symbol head, List tail, Runtime runtime) {
		setTerms(head, tail, runtime);
	}

	private void setMembers(Expression head, List tail, Runtime runtime) {
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

	public Returnable eval (Runtime runtime) {
		Returnable result = applicative.eval(runtime);
		if (Procedure.isAssignableFrom(result.class) {// if this doesn't work, we could use Class.forName("Procedure")
		//if (!result.isCompound() && ((Atom)result).isSelfEvaluating() && ((SelfEvaluating)result).isProcedure()) {
			return ((Procedure)result).apply(arguments.evaluatedTerms(runtime), runtime);
			// technically this may not be the way DrRacket does it. (Edit: Oh, but it is! :D )
		} else {
			throwEval("Applicative is not a procedure");
			return null;
		}
	}
}
