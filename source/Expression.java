import java.io.OutputStream;

public abstract class Expression extends Pairable {

	public Expression eval (Runtime runtime);

	protected Object throwEval (String msg) {
		throw new InvalidEvalException(msg);
		return null;
	}
}
