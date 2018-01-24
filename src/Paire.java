public class Paire {
	
	// Variables
	double weight;
	String pred;
	
	// Shape of pred : X_nodebase_toNode_size_colorset or Y_node_size1_colorset1_size2_colorset2
		
	public Paire(double weight, String pred) {
		super();
		this.weight = weight;
		this.pred = pred;
	}

	public double getWeight() {
		return weight;
	}
	
	public String getPred() {
		return pred;
	}

	@Override
	public String toString() {
		return "Triple [weight=" + weight + ", pred=" + pred + "]";
	}
	
	
	
	

}
