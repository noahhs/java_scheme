import java.lang.Math

public class BuiltInProcedure extends Procedure {

	public static enum Type {PLUS, MINUS, TIMES, DIVIDEDBY, EQUALS, MODULO, EXPONENTIAL, LESSTHAN, DISPLAY, CAR, CDR, CONS};

	private Type type;

	public BuiltInProcedure (Type type) {
		this.type = type;
	}

	public String toString () { return "#<procedure:" + this.string + ">"; }
	
	public Expression apply (List arguments, Runtime runtime) {
		List args = arguments.evaluatedTerms(runtime);
		Expression a1 = args.car();
		List a2 = args.cdr().car();
		switch (type) {
			case PLUS:		return sum(args, runtime);
			case MINUS:		return subtract(args, runtime);
			case TIMES:		return multiply(args, runtime);
			case DIVIDEDBY:	return divide(args, runtime);
			case EQUALS:	return equals(args, runtime);
			case MODULO:	return c.value() % d.car().value();
			case EXPONENTIAL:	return args.car().value() ^ args.cdr().car().value();
			case LESSTHAN:	return args.car().value() < args.cdr().car().value();
			case DISPLAY:	return args.car().print(runtime);
			case CAR:		return ((Pair)args.car()).car()
			case CDR:		return ((Pair)args.car()).cdr();
			case CONS:		return args.cdr().car().cons(args.car());
			default:		return null;
	}
	
	private static void arity (int arity, int value) {
		arityRange (arity, value, value);
	}

	private static void arityRange (int arity, int min, int max) {
		arityMin(arity, min);
		arityMax(arity, max);
	}

	private static void arityMin (int arity, int min) {
		if (arity < min) { throw InvalidEvalException("Arity mismatch"); }
	}

	private void arityMax (int arity, int max) {
		if (arity > max) { throw InvalidEvalException("Arity mismatch"); }
	}

	private static Expression sum (List arguments) {
		Number accum = 0; // should cast
		for (Expression exp : arguments) {
			accum += exp.value();
		}
		return new NumericType(accum);
	}

	private static Expression subtract (ArrayList<Expression> arguments) {
		arityMin(arguments.size(), 1);
		Number accum;
		if (arguments.size() > 1) {
			accum = arguments.get(0).value();
			arguments.remove(0);
		} else {
			accum = 0;
		}
		for (Expression exp : arguments) {
			accum -= exp.value();
		}
		return new NumericType(accum);
	}

	private static Expression multiply (ArrayList<Expression> arguments) {
		Number accum = 1; // should cast
		for (Expression exp : arguments) {
			accum *= exp.value();
		}
		return new NumericType(accum);
	}

	private static Expression divide (ArrayList<Expression> arguments) {
		Number accum;
		if (arguments.size() > 1) {
			accum = arguments.get(0).value();
			arguments.remove(0);
		} else {
			accum = 1;
		}
		for (Expression exp : arguments) {
			accum /= exp.value();
		}
		return new NumericType(accum);
	}

	private static Expression equals (ArrayList<Expression> arguments) {
		arityMin(arguments.size(), 2);
		Boolean accum = true;
		for (Expression exp : arguments) {
			accum = accum && exp.toBoolean();
		}
		return new BooleanType(accum);
	}

	private static Expression modulo (ArrayList<Expression> arguments) {
		arity(arguments.size(), 2);
		return new NumericType(arguments.get(0).value() % arguments.get(1).value());
	}

	private static Expression exponential (ArrayList<Expression> arguments) {
		arity(arguments.size(), 2);
		return new NumericType(Math.pow(arguments.get(0).value(), arguments.get(1).value()));
	}

	private static Expression lessthan (ArrayList<Expression> arguments) {
		arity(arguments.size(), 2);
		return new BooleanType(arguments.get(0).value() < arguments.get(1).value());
	}

	private static Expression display (ArrayList<Expression> arguments, OutputStream ostream) {
		arity(arguments.size(), 1);
		ostream.println(arguments.get(0).toString());
		return null;
	}

	private static Expression car (ArrayList<Expression> arguments) {
		arity(arguments.size(), 1);
		return arguments.get(0).car();
	}

	private static Expression cdr (ArrayList<Expression> arguments) {
		arity(arguments.size(), 1);
		return arguments.get(0).cdr();
	}

	private static Expression cons (ArrayList<Expression> arguments, OutputStream ostream) {
		arity(arguments.size(), 2);
		return arguments.get(1).cons(arguments.get(0));
	}
}
