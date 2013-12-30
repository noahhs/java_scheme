public class BoundExpression {

	private Compiled expression;
	private Returnable result = null;

	public BoundExpression(Returnable expression, Runtime runtime) {
		this.expression = expression.compile(runtime);
	}

	public BoundExpression(SelfEvaluating expression) {
		this.expression = expression;
		this.result = expression;
	}

	public void eval (Runtime runtime) {
		result = expression.eval(runtime);
	}

	public Expression result () {
		return result;
	}
}
