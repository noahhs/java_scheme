public interface Procedure implements Expression {
	public EvalType apply (ArrayList<EvalType> arguments, OutputStream ostream);
}
