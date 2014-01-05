import java.lang.Math

public class BuiltInProcedure extends Procedure {

	public static enum Type {PLUS, MINUS, TIMES, DIVIDEDBY, EQUALS, MODULO, EXPONENTIAL, LESSTHAN, DISPLAY, CAR, CDR, CONS};

	public static EnvironmentFrame bindings () {
		Map<String, Type> types = new HashMap<String, Type>();
		map1.put("+"	, PLUS);
		map1.put("-" 	, MINUS);
		map1.put("*" 	, TIMES);
		map1.put("/" 	, DIVIDEDBY);
		map1.put("=" 	, EQUALS);
		map1.put("modulo" , MODULO);
		map1.put("^"	, EXPONENTIAL);
		map1.put("<"      , LESSTHAN);
		map1.put("display", DISPLAY);
		map1.put("car"    , CAR);
		map1.put("cdr"	, CDR);
		map1.put("cons"   , CONS);
		
		EnvironmentFrame frame = new EnvironmentFrame();
		for (String s : types.keySet()) {
			frame.bind(new Symbol(s), new Variable(new BuiltInProcedure(types.get(s))));// gorgeous
		}
		return frame;
	}

	private Type type;

	public BuiltInProcedure (Type type) {
		this.type = type;
	}

	public String toString () {
		  return "#<procedure:" + this.string + ">";
	}
	
	public Returnable apply (List args, Runtime runtime) {// arguments are Returnable, because they have been evaluated
		Expression a1 = args.car();
		Expression a2 = args.cdr().car();
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
	
	private void contract (List args, Class dass) {
		for (Expression e : args) {
			if (!e.satisfies(dass)) {
				throwEval("Contract violation");
			}
		}
	}
	
	private void arity (List args, int arity) {
		arityRange(args, arity, arity);
	}

	private void arityRange (List args, int min, int max) {
		arityMin(args, min);
		arityMax(args, max);
	}

	private void arityMin (List args, int min) {
		if (args.size() < min) {
			throwEval("Arity mismatch");
		}
	}

	private void arityMax (List args, int max) {
		if (args.size() > max) {
			throwEval("Arity mismatch");
		}
	}

	private NumericType sum (List arguments) {
		contract(arguments, NumericType);
		Number accum = 0; // should cast
		for (Pairable exp : arguments) {
			accum += ((NumericType)exp).value();
		}
		return new NumericType(accum);
	}

	private NumericType subtract (List arguments) {
		contract(arguments, NumericType);
		arityMin(arguments, 1);
		Number accum;
		if (arguments.size() > 1) {
			accum = ((NumericType)arguments.car()).value();
			arguments = arguments.cdr();
		} else {
			accum = 0;
		}
		for (Pairable exp : arguments) {
			accum -= ((NumericType)exp).value();
		}
		return new NumericType(accum);
	}

	private NumericType multiply (List arguments) {
		contract(arguments, NumericType);
		Number accum = 1; // should cast
		for (Pairable exp : arguments) {
			accum *= ((NumericType)exp).value();
		}
		return new NumericType(accum);
	}

	private NumericType divide (List arguments) {
		contract(arguments, NumericType);
		Number accum;
		if (arguments.size() > 1) {
			accum = ((NumericType)arguments).car().value();
			arguments = arguments.cdr();
		} else {
			accum = 1;
		}
		for (Pairable exp : arguments) {
			accum /= ((NumericType)exp).value();
		}
		return new NumericType(accum);
	}

	private BooleanType equals (List arguments) {
		contract(arguments, NumericType);
		arityMin(arguments, 2);
		Boolean accum = true;
		Number value = ((NumericType)arguments.car()).value();
		for (Pairable exp : arguments) {
			accum = accum && (((NumericType)exp).value() == value);
		}
		return new BooleanType(accum);
	}

	private NumericType modulo (List arguments) {
		contract(arguments, NumericType);
		arity(arguments, 2);
		return new NumericType(((NumericType)arguments.car()).value() % ((NumericType)arguments.cdr().car()).value());
	}

	private NumericType exponential (List arguments) {
		contract(arguments, NumericType);
		arity(arguments, 2);
		return new NumericType(Math.pow(arguments.get(0).value(), arguments.get(1).value()));
	}

	private BooleanType lessthan (List arguments) {
		contract(arguments, NumericType);
		arity(arguments, 2);
		return new BooleanType(arguments.get(0).value() < arguments.get(1).value());
	}

	private VoidType display (List arguments, OutputStream ostream) {
		arity(arguments, 1);
		ostream.println(arguments.get(0).toString());
		return null;
	}

	private Returnable car (List arguments) {
		arity(arguments, 1);
		return (Returnable)arguments.get(0).car();
	}

	private Returnable cdr (List arguments) {
		arity(arguments, 1);
		return arguments.get(0).cdr();
	}

	private Returnable cons (List arguments, OutputStream ostream) {
		arity(arguments, 2);
		return arguments.get(1).cons(arguments.get(0));
	}
}
