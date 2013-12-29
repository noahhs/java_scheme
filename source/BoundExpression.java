public class BoundExpression {

	private Compiled expression;
	private Returnable result = null;

	public BoundExpression(Compiled expression) {
		this.expression = expression;
	}

	public void eval (Runtime runtime) {
		result = expression.eval(runtime);
	}

	public Expression result () {
		return result;
	}
}
