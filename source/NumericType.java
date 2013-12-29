public class NumericType extends SelfEvaluating {
	private Number number;

	public NumericType(Number num) {
		number = num;
	}

	public String toString () {
		return number.toString();
	}

	//@Override
	public Number value () {
		return number;
	}
}
