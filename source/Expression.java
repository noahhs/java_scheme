import java.io.OutputStream;

public abstract class Expression {

	public Expression eval (Runtime runtime);

	protected void throwEval (String msg) { throw new InvalidEvalException(msg); }
}
