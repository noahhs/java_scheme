public class Token {
	public enum TokenType {LEFT_PAREN, RIGHT_PAREN, NUMERIC, IDENTIFIER};

	private TokenType type;
	private String string;
	private Number number;

	Token (TokenType type) {
		this.type = type;
	}

	Token (Number contents) {
		this.type = TokenType.NUMERIC;
		this.number = contents;
	}

	Token (String contents) {
		this.type = TokenType.IDENTIFIER;
		this.string = contents;
	}

	public TokenType type () {
		return this.type;
	}

	public Boolean equal (Token token) {
		switch (this.type()) {
			case LEFT_PAREN:
			case RIGHT_PAREN:	return (this.type() == token.type());
			case NUMERIC:	return (this.number == token.number);
			case IDENTIFIER:	return (this.string == token.string);
			default:	return false;
		}
	}
	
	@Override
	public String toString() {
		switch (type) {
			case LEFT_PAREN:	return "LEFT_PAREN";
			case RIGHT_PAREN:	return "RIGHT_PAREN";
			case NUMERIC:	return number.toString();
			// HA HA
			//case NUMERIC:	switch (number.getClass().getName()) {
			//				case "Integer":	return Integer.toString((int)number);
			//				case "Double":	return Double.toString((double)number);
			//				default:		return "";
			//			}
			case IDENTIFIER:	return string;
			default:		return "";
		}
	}
}
