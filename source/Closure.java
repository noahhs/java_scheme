public class Closure {
	private Map<Identifier, Expression> bindings;
	
	public Closure () {
		//bindings = BuiltInProcedure.bindings().clone();
		bindings = new HashMap<Identifier, Expression>();
	}
		  
	public Expression resolve (Identifier identifier) {
		Expression result = bindings.get(identifier);
		if (result == null) {
			throw new InvalidEvalException("Unbound identifier");
		}
		return result;
	}

	public void addBinding (Identifier identifier, Expression expression) {
		// to do: handle illegal strings
		bindings.put(identifier, expression);
	}
}
