public class DefineForm extends SpecialForm {

	private BoundExpression boundExpression;

	public DefineForm(Symbol symbol, Expression expression, Runtime runtime) {
		boundExpression = new BoundExpression(expression);
		runtime.addBinding(symbol, boundExpression);
	}
	
	public Expression eval (Runtime runtime) {
		boundExpression.eval(runtime);
		return new VoidType();
	}
}
