public class SpecialForm extends Expression implements Compiled {
	
	public static enum Keyword {DEFINE, LAMBDA, COND, QUOTE, LET, LET_SPLAT, BEGIN};

	public static Map<Symbol,Keyword> keywords;
	keywords.put(new Symbol("define"),	DEFINE);
	keywords.put(new Symbol("lambda"),	LAMBDA);
	keywords.put(new Symbol("cond"),	COND);
	keywords.put(new Symbol("quote"),	QUOTE);
	keywords.put(new Symbol("let"),	LET);
	keywords.put(new Symbol("let*"),	LET_SPLAT);
	keywords.put(new Symbol("begin"),	BEGIN);
	//keywords.put(new Symbol(""), );
	
	public static Keyword keyword (Symbol symbol) {
		return keywords.get(symbol);
	}
}
