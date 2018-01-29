import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;



public class Test {
	
	public static void main(String[] args) throws Exception {
		
	    if (args.length != 4) {
	      throw new IllegalArgumentException("Exactly 4 parameters required !");
	    }
		
		// Goal of call : return String = where_read what_to_do n m c 
    	//			+ running_time_c OR running_time_in 
    	
		
		// Final settings
		String where_read = args[0];
		String where_write = args[1];
		String what_to_do = args[2];            // => Only c or in2 => NOT BOTH (better to have separate data in case one fails timelimit)
		int time_solve_seconds = Integer.valueOf(args[3]);
		
		/* Test settings *
		String where_read = "Small_data_MCA/red-graphs-100-200/4.graph";
		String where_write = "truc";
		String what_to_do = "in2";
		int time_solve_seconds = 3;*/
		
		
		// Dealing with "threads" for time limit
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		Task myTask = new Task(what_to_do, where_read);
		
        Future<String> future = executor.submit(myTask);

        try {
            //System.out.println("Started..");
            System.out.println(future.get(time_solve_seconds, TimeUnit.SECONDS));
            //System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            //System.out.println("Terminated!");
        }
        
        executor.shutdownNow();
        
        
        
        // System.out.println("Value sol = " + myTask.getMyChain());
        
        // Result to return
     	StringBuffer return_this = new StringBuffer("");
     	return_this.append(where_read);
     	return_this.append(' ');
     	return_this.append(what_to_do);
     	return_this.append(' ');
        return_this.append(myTask.getMyChain());
        return_this.append('\n');
        
        FileOutputStream fw = null;
	    BufferedWriter bw = null;
	    
	    // Recopier infos importantes
		try {

			fw = new FileOutputStream(where_write, true);
			bw = new BufferedWriter(new OutputStreamWriter(fw));
		    
			bw.write(return_this.toString());

			//System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		/*Graph graph = new Graph(50,8);
		
		MCA_instance_FPT_C instance_c = new MCA_instance_FPT_C(graph);
		instance_c.compute();
		double res_c = instance_c.searchBest(0).getWeight();
		
		MCA_instance_FPT_in2 instance_i = new MCA_instance_FPT_in2(graph);
		instance_i.compute();
		double res_i = instance_i.searchBest(0).getWeight();
		
		System.out.println("c : " + res_c);
		System.out.println("i : " + res_i);
		System.out.println();
		*/
		
		
        
		
		
		

	}

}
