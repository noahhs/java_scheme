public class CompoundProcedure extends SelfEvaluating {
		// push frame to runtime
		// add parameter bindings
		// compile the expression.
		//	the symbols will be replaced with variables,
		//	mapping the symbol to a BoundExpression,
		//	which is an expression-result pair.
		//	in the case of parameter bindings, there is no expression yet defined at compile time.
		//	no define form has been processed. It's as if we said "(define a ())".
		//	These variables will have their BoundExpression expressions populated when the function is invoked.
		//	How? It will happen in the "apply" method.
		//	A compound procedure has a list of BoundExpressions as its "parameters" member.
		//	Actually, they'd be best as simple results. We should probably take the time to think about this.
		//	We assumed that we could just compile the body of the lambda against some runtime,
		//	and it would populate its variables correctly.
		//
		// Okay, I've got it. We had the wrong structure set up for BoundExpressions. We should have just made them BoundResults.
		// The expression piece should have resided in the DefineForm object.
	
		// Let's continue to articulate this.
		// During compilation we create variables, which link to BoundResults with the "assigned" flag to false, and result member == null.
		// These BoundResults were added to the runtime environment, paired with the parameter symbols.
		// When we are done compiling the body, we pop the frame off the runtime.
		// But the BoundResult references still persists in the parameters list.
		// At lambda-evaluation time, a procedure is generated, from the compiled body, and the environment needed to evaluate it.
		// That environment just consists of the parameter list of BoundResult objects.
		//
		// Let's do a thought experiment. What happens when we reference an unbound variable in the body?
		// It's not in the parameter list. The Variable object has a null reference in place of its BoundResult. OK, that's fine.
		//
		// Now what happens if the variable is bound in some parent context, and not locally?
		// The compilation of the symbol will hit the runtime environment, and find the BoundResult.
		// The generated variable will persist in the procedure. Its lexical definition trumps any dynamic one.
		//
		// I think we're good on this.
	
	private List parameters;
	private Compiled body;

	public String toString () { return "#<procedure>"; }
	
	public CompoundProcedure (List paramSymbols, Returnable body, Runtime runtime) {
		EnvironmentFrame frame = new EnvironmentFrame();
		this.parameters = paramsList(paramSymbols, frame);// also takes care of populating the EnvironmentFrame. Should name it better
		runtime.pushFrame(frame);
		this.body = body.compile(runtime);
		runtime.popFrame();
		// is this it?
	}

	private List paramsList (List paramSymbols, EnvironmentFrame frame) {
		if (paramSymbols == null) {
			return null;
		} else {
			BoundResult resultRef = new BoundResult();
			frame.bind(paramSymbols.car(), resultRef); 
			List tail;
			if (paramSymbols.cdr() == null) {
				tail = null;
			} else {
				tail = boundParameters(paramSymbols.cdr(), frame);
			}
			return new List(resultRef, tail);
		}
	}

	public Returnable apply (List arguments, Runtime runtime) {// arguments are Returnable, because they have been evaluated
		// remember to pop frame when done, to clear out internal definitions
		runtime.push(new EnvironmentFrame());
		setParams(arguments, parameters);
		Returnable result = body.eval(runtime);
		runtime.pop();
		return result;
	}

	private void setParams (List arguments, List parameters) {
		if (arguments == null) {
			if (parameters != null) {
				throw new InvalidEvalException("Invalid number of arguments");
			}
			return;
		} else {
			((Variable)parameters.car()).set((Returnable)arguments.car());
			setParams(arguments.cdr(), parameters.cdr());
		}
	}
}
