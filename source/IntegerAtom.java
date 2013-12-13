public class IntegerSexp extends Atom {
	
	private Integer integer;
	
	public IntegerSexp(Integer integer) {
		this.integer = integer;
	}

	public String toString() {
		return integer.toString();
	}
}

