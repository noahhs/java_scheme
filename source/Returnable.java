public abstract class Returnable extends Expression {
	
	public String toString ();

	public void print (Runtime runtime) { runtime.ostream().println(this.toString()); }

	public Boolean isSymbol ();

	public Pair cons (Expression exp) { return new Pair(exp, this); }

	public Boolean isCompound ();

	public Compiled compile (Runtime runtime);

	public Returnable eval (Runtime runtime) {
		return this.compile(runtime).eval(runtime);
	}
}
