public class Variable extends Expression implements Compiled {
	private BoundExpression lexicalBound = null;
	private Symbol symbol;

	public Variable (Symbol symbol, BoundExpression bound) {
		this.symbol = symbol;
		this.lexicalBound = bound;
	}

	public Expression eval (Runtime runtime) {
		if (lexicalBound != null) {
			return lexicalBound.result();
		} else {
			return runtime.resolve(symbol).result();
		}
	}
}
