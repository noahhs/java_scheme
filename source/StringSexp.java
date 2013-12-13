public class StringSexp implements Sexp {
	
	public boolean isAtom() { return true; }

	private String string;

	public StringSexp(String string) {
		this.string = string;
	}
}
