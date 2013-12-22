public interface Function extends EvalType {
	private Closure closure;
	private ArrayList<String> parameters;
	private Expression body;

	public Function (Closure closure, ArrayList parameters, Expression body) {
		this.closure = closure;
		this.parameters = parameters;
		this.body = body;
	}

	public String toString () { return "#<procedure>"; }
	
	public EvalType apply (ArrayList<EvalType> arguments, OutputStream ostream) {
		if (parameters.size() != arguments.size()) { throw new InvalidEvalException("Invalid number of arguments"); }
		Closure applicationClosure = closure.clone();
		for (int i = 0; i < parameters.size(); i += 1) {
			applicationClosure.addBinding(parameters.get(i), arguments.get(i));
		}
		return body.eval(applicationClosure, ostream);
	}
}
