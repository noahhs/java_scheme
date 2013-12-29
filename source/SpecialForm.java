public class SpecialForm {
	
	public static enum Keyword {DEFINE, LAMBDA, COND, QUOTE, LET, LET_SPLAT, BEGIN};

	public static Map<Symbol,Keyword> keywordsBySymbol;
	keywordsBySymbol.put(new Symbol("define"),	DEFINE);
	keywordsBySymbol.put(new Symbol("lambda"),	LAMBDA);
	keywordsBySymbol.put(new Symbol("cond"),		COND);
	keywordsBySymbol.put(new Symbol("quote"),		QUOTE);
	keywordsBySymbol.put(new Symbol("let"),		LET);
	keywordsBySymbol.put(new Symbol("let*"),		LET_SPLAT);
	keywordsBySymbol.put(new Symbol("begin"),		BEGIN);
	//keywordsBySymbol.put(new Symbol(""), );
	
	public static Keyword keyword (Symbol symbol) {
		return keywordsBySymbol.get(Symbol);
	}
}
