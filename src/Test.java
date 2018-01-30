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
    	
		// Variables
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
        String result = "";

        try {
            //System.out.println("Started..");
        	result = future.get(time_solve_seconds, TimeUnit.SECONDS);
            //System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            //System.out.println("Terminated!");
        }
        
        executor.shutdownNow();
        
        
        // Writing results in file
        FileOutputStream fw = null;
	    BufferedWriter bw = null;
	    
		try {

			fw = new FileOutputStream(where_write, true);
			bw = new BufferedWriter(new OutputStreamWriter(fw));
		    
			bw.write(result);

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

	}

}
