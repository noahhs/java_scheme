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
}
