
public class Arc {
	
	//Attributs
	protected int a;
	protected int b;
	
	public Arc(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "(" + String.valueOf(a) + "," + String.valueOf(b) + ")";
	}
	
	
	
	
	
}
