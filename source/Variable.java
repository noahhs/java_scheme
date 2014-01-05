public class Variable extends Expression implements Compiled {
	
	private Returnable result;
	private Boolean initialized = false;

	public Variable (Returnable result) {
		this.set(result);
	}

	public Variable () {}

	public void set (Returnable value) {
		result = value;
		initialized = true;
	}

	public Returnable eval (Runtime runtime) {
		return this.eval();
	}

	public Returnable eval () {
		if (!initialized) {
			return throwEval("That's odd--variable not initialized!");
		} else {
			return result;
		}
	}
}
