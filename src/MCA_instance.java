
public class MCA_instance {
	
	// Variables
	protected Graph graph;
	
	// Constructor and getters / setters
	
	public MCA_instance() {
		
	}
	
	public MCA_instance(int number_of_nodes, int number_of_colors) {
		graph = new Graph(number_of_nodes, number_of_colors);
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	// Useful functions
	
	public boolean isPowerOf2(int color) {
		boolean result = false;
		double test = 0;
		double calcul = 1;
		while (calcul < color) {
			test++;
			calcul = Math.pow(2, test);
		}
		if (calcul == color) {
			result = true;
		}
		return result;
	}
	
	public boolean are_disjoint(int s_one, int s_two) {
		boolean result = true;
		String first, second;
		int i = 0;
		
		if (s_one > s_two) {
			first = Integer.toBinaryString(s_one);
			second = Integer.toBinaryString(s_two);
			
		}
		else {
			first = Integer.toBinaryString(s_two);
			second = Integer.toBinaryString(s_one);
		}
		
		//System.out.println("first = " + first + ", second = " + second);
		
		while ((result) && (i<second.length()) ) {
			//System.out.println("i = " + i + ", first caracter = " + first.charAt(first.length()-1-i) + " and second caracter = " + second.charAt(second.length()-1-i));
			if ((first.charAt(first.length()-1-i) == '1') && (second.charAt(second.length()-1-i) == '1') ) {
				result = false;
			}
			i++;
		}
		
		//System.out.println("result = " + result);
		return result;
	}
	
	
	
	
	
	

}
