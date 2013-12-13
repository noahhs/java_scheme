public class DoubleSexp implements Sexp {
	
	public boolean isAtom() { return true; }
	
	private Double dbl;
	
	public DoubleSexp(Double dbl) {
		this.dbl = dbl;
	}

	public Double eval() {
		return dbl;
	}
}
