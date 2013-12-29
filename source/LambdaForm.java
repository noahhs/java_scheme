public class LambdaForm extends SpecialForm {

	private ;

	public LambdaForm (List params, Compiled body, Runtime runtime) {

	}
	
	public CompoundProcedure eval (Runtime runtime) {
		binding.eval(runtime);
		return new VoidType();
	}
}
