public class VoidType extends SelfEvaluating {
	public String toString () {
		return "#<void>";
	}

	@Override
	public void print (OutputStream ostream) {}// do nothing, no line feed
}
