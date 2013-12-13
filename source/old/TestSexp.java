public class TestSexp {
	public static void main(String [] args) {
		List l1 = new List();
		StringSexp a1 = new StringSexp("Alice");
		IntegerSexp a2 = new IntegerSexp(1);
		DoubleSexp a3 = new DoubleSexp(1.999);
		
		System.out.println("l1 = " + l1);
		System.out.println("a1 = " + a1);
		System.out.println("a2 = " + a2);
		System.out.println("a3 = " + a3);
		l1 = l1.cons(a1);
		System.out.println("(cons a1 l1) = " + l1);
		l1 = l1.cons(a2);
		System.out.println("(cons a2 l1) = " + l1);
		l1 = l1.cons(a3);
		System.out.println("(cons a3 l1) = " + l1);
		System.out.println("(car l1) = " + l1.car());
		System.out.println("(cdr l1) = " + l1.cdr());
	}
}

