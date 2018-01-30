import java.util.concurrent.Callable;


class Task implements Callable<String> {
	
	// Variables
	Graph graph;
	String what_to_do;
	
	// Constructor
	public Task(Graph graph, String what_to_do) {
		super();
		this.graph = graph;
		this.what_to_do = what_to_do;
	}
	
	@Override
    public String call() throws Exception {
		
		StringBuffer result = new StringBuffer("");
        
		if (what_to_do.equals("c")) {
			
			MCA_instance_FPT_C instance = new MCA_instance_FPT_C(graph);
			
			instance.compute();
			
			// If a solution has been found within the time limit
			if (!Thread.interrupted()) {
				double res = instance.searchBest(0).getWeight();
				//instance.retrieveSol();
				//instance.afficheSol();
				
				result.append(' ');
				result.append(res);
    		}
			
		}
		// if ( what_to_do == "in2" )
		else  {
			
			MCA_instance_FPT_in2 instance = new MCA_instance_FPT_in2(graph);
			
			instance.compute();
			
			// If a solution has been found within the time limit
			if (!Thread.interrupted()) {
				double res = instance.searchBest(0).getWeight();
				//instance.retrieveSol();
				//instance.afficheSol();
				
				result.append(' ');
				result.append(res);
    		}
			
		}
		
        return result.toString();
    }
}