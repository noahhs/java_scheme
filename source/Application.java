public class Application extends Expression implements Compiled {
	
	private Procedure procedure;
	private List arguments;

	// we have decided to pass in already-compiled arguments
	public Application (Procedure proc, List args) {
		procedure = proc;
		arguments = args;
	}

	public Expression eval (Runtime runtime) {
		return procedure.eval(arguments, runtime);
	}
}
