import java.io.OutputStream;

public abstract class Expression extends Pairable {

	public Expression eval (Runtime runtime);

	protected void throwEval (String msg) { throw new InvalidEvalException(msg); }
}
