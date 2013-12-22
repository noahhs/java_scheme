import java.io.OutputStream;

public abstract class SelfEvaluating implements Expression {
	public Expression eval (Closure closure, OutputStream ostream) { return eval(); }

	public SelfEvaluating eval () { return this; }
}
