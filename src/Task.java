import java.util.concurrent.Callable;


class Task implements Callable<String> {
	
	// Variables
	String type_algo, file_name;
	String myChain;
	
	// Constructor
	public Task(String what_to_do, String where_read) {
		super();
		type_algo = what_to_do;
		file_name = where_read;
		myChain = "";
	}
	
	public String getMyChain() {
		return myChain;
	}



	@Override
    public String call() throws Exception {
        
    	//System.out.println("call() commencé !");
    	
    	// Variables
		Graph graph = new Graph(file_name);
		//Graph graph = new Graph(50,8);
		
		if (graph.is_created()) {
			
			// Don't put more than 30 colors : 2^31 = max_int + 1
			if (graph.getC() <= 30) {
				
				// if it is only asked
				if (type_algo.equals("c")) {
					
					MCA_instance_FPT_C instance_c = new MCA_instance_FPT_C(graph);
					instance_c.compute();
					
					/************* For respecting time limit *************/
					if (!Thread.interrupted()) {
						Thread.currentThread().interrupt();
						double res_c = instance_c.searchBest(0).getWeight();
						//instance_c.retrieveSol();
						//instance_c.afficheSol();	
						//System.out.println("Best result with FPT C : " + res_c);
						
						myChain = String.valueOf(res_c);
		    		}
					/************* For respecting time limit *************/
					
					
				}
				else  {
					
					MCA_instance_FPT_in2 instance_i = new MCA_instance_FPT_in2(graph);
					//instance_i.short_display();
					instance_i.compute();
					
					/************* For respecting time limit *************/
					if (!Thread.interrupted()) {
						Thread.currentThread().interrupt();
						
						double res_i = instance_i.searchBest(0).getWeight();
						//instance_i.retrieveSol();
						//instance_i.afficheSol();
						//System.out.println("Best result with FPT in2 : " + res_i);
						
						myChain = String.valueOf(res_i);
		    		}
					/************* For respecting time limit *************/
					
					
				}
				
			}
			else {
				System.out.println("Current algorithms can not handle more than 30 colors.");
			}
			
			
		} // end if graph_created
    	
    	
		//System.out.println("call() fini !");
        
        return "";
        //return "Ready!";
    }
}