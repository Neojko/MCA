
public class Triple {
	
	// Variables
	String out;
	double weight;
	String pred;
	
	// Shape of pred : X_nodebase_toNode_size_colorset or Y_node_size1_colorset1_size2_colorset2
		
	public Triple(String out, double weight, String pred) {
		super();
		this.out = out;
		this.weight = weight;
		this.pred = pred;
	}

	public String getOut() {
		return out;
	}

	public double getWeight() {
		return weight;
	}
	
	public String getPred() {
		return pred;
	}

	@Override
	public String toString() {
		return "Triple [out=" + out + ", weight=" + weight + ", pred=" + pred + "]";
	}
	
	
	
	

}
