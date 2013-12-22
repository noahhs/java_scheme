public class BooleanType extends SelfEvaluating {
	private Boolean bool;

	public BooleanType(Boolean bool) {
		this.bool = bool;
	}

	public String toString () {
		return bool.toString();
	}

	@Override
	public Boolean toBoolean () {
		return bool;
	}
}
