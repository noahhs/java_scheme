public class CompoundProcedure implements Procedure {
	private List arguments;
	private Compiled body;
	private List closureFrames;

	public String toString () { return "#<procedure>"; }
	
	public CompoundProcedure (Closure closure, ArrayList<String> parameters, Expression body) {
		this.closure = closure;
		this.parameters = parameters;
		this.body = body;
	}

	public Expression apply (, OutputStream ostream) {


		
		if (parameters.size() != arguments.size()) { throw new InvalidEvalException("Invalid number of arguments"); }
		Closure applicationClosure = closure.clone();
		for (int i = 0; i < parameters.size(); i += 1) {
			applicationClosure.addBinding(parameters.get(i), arguments.get(i));
		}
		return body.eval(applicationClosure, ostream);
	}
}
