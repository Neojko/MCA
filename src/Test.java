

public class Test {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		// Don't put more than 30 colors : 2^31 = max_int + 1
		
		// Variables
		Graph graph = new Graph("Small_data_MCA/red-graphs-100-200/2.graph");
		MCA_instance_FPT_C instance_c = new MCA_instance_FPT_C(graph);
		MCA_instance_FPT_in2 instance_i = new MCA_instance_FPT_in2(graph);
		
		// display
		//instance_i.short_display();
		
		// Pour algo |C|
		instance_c.compute();
		double res_c = instance_c.searchBest(0).getWeight();
		instance_c.retrieveSol();
		
		// Pour algo in2
		instance_i.compute();
		double res_i = instance_i.searchBest(0).getWeight();
		instance_i.retrieveSol();
		
		instance_c.afficheSol();
		instance_i.afficheSol();		
		System.out.println("Best result with FPT C : " + res_c);
		System.out.println("Best result with FPT in2 : " + res_i);
		
		

	}

}
