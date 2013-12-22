public class LambdaProcedure implements Procedure {
	private Closure closure;
	private ArrayList<String> parameters;
	private Expression body;

	public String toString () { return "#<procedure>"; }
	
	public LambdaProcedure (Closure closure, ArrayList<String> parameters, Expression body) {
		this.closure = closure;
		this.parameters = parameters;
		this.body = body;
	}

	public EvalType apply (ArrayList<EvalType> arguments, OutputStream ostream) {
		if (parameters.size() != arguments.size()) { throw new InvalidEvalException("Invalid number of arguments"); }
		Closure applicationClosure = closure.clone();
		for (int i = 0; i < parameters.size(); i += 1) {
			applicationClosure.addBinding(parameters.get(i), arguments.get(i));
		}
		return body.eval(applicationClosure, ostream);
	}
}
