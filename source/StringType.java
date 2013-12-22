public class StringType extends SelfEvaluating {
	private String string;

	public StringType(String str) {
		string = str;
	}

	public String toString () {
		return string;
	}
}
