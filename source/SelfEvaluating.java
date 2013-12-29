import java.io.OutputStream;

public abstract class SelfEvaluating extends Returnable implements Compiled {
	public SelfEvaluating eval (Runtime runtime) { return this; }
	public SelfEvaluating compile (Runtime runtime) { return this; }
}
