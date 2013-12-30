public class EnvironmentFrame extends Pairable {
	private Map<Symbol, BoundExpression> map = new HashMap<Symbol, BoundExpression>();
	
	public BoundExpression resolve (Symbol symbol) {
		return map.get(symbol);
	}

	public void bind (Symbol symbol, BoundExpression bound) {
		map.put(symbol, bound);
	}
}
