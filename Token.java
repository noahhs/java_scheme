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
		}
	}
	
	@Override
	public String toString() {
		return ("type: " + type + "; string: " + string + "; number: " + number);
	}
}
