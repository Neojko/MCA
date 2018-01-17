
public class TripleG<A, B, C> {
	
	// Variables
	A a;
	B b;
	C c;
		
	public TripleG(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}
	
	public C getC() {
		return c;
	}

	@Override
	public String toString() {
		return "Triple [a=" + a + ", b=" + b + ", c=" + c + "]";
	}
	
	
	
	

}
