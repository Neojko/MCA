// Write in files
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

// Limit time
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Test {
	
	public static void main(String[] args) throws Exception {
		
		// Start measuring time
		long lStartTime = System.nanoTime();
	    
		if (args.length != 4) {
	      throw new IllegalArgumentException("Exactly 4 parameters required !");
	    }
	    
		// Variables
		String where_read = args[0];
		String where_write = args[1];
		String what_to_do = args[2];
		long time_solve_seconds = Integer.valueOf(args[3]);
		
		/* Use it when too lazy to make a .jar to check if modified things are ok
		String where_read = "Small_data_MCA/red-graphs-100-200/4.graph";
		String where_write = "truc";
		String what_to_do = "c";
		long time_solve_seconds = 3L;*/
		
        // Building graph
        Graph graph = new Graph(where_read);
        
        // Filling output
        StringBuffer output =  new StringBuffer(get_basename_instance(where_read));
        output.append(' ');
        output.append(graph.getN());
        output.append(' ');
        output.append(graph.getM());
        output.append(' ');
        output.append(graph.getC());
        output.append(' ');
        output.append(graph.give_intwo());
        //output.append(' ');
        //output.append(what_to_do);
        
        
        // PRE : algo can not handle graphs containing more than 30 colors
        if ( (graph.is_created()) && (graph.getC() <= 30) ) {
        	
        	// Dealing with "threads" for time limit
    		ExecutorService executor = Executors.newSingleThreadExecutor();
    		Task myTask = new Task(graph, what_to_do);
    		Future<String> future = executor.submit(myTask);

            try {
                // add weight_sol to output if solution has been solved
            	output.append( future.get(time_solve_seconds*1000-20, TimeUnit.MILLISECONDS) );
            	
            } catch (TimeoutException e) {
                future.cancel(true);;
            }
            
            executor.shutdownNow();
        	
        }
        
        // Get time which is spent to solve instance
        long lEndTime = System.nanoTime();
        long output_time = lEndTime - lStartTime;
        
		
        // Add -1 to result if no solution found
        String[] word_sep = output.toString().split("\\s+");
        if (word_sep.length < 6) {
        	output.append(" -1");
        }
        
        // Add time to result in seconds + 3 decimals
        output.append(' ');
        output.append(nano_to_sec(output_time));
        output.append('\n');
        
        
        
        // Writing results in file where_write
        FileOutputStream fw = null;
	    BufferedWriter bw = null;
	    
		try {

			fw = new FileOutputStream(where_write, true);
			bw = new BufferedWriter(new OutputStreamWriter(fw));
		    
			bw.write(output.toString());

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
	
	
	public static StringBuffer nano_to_sec(long x) {
		String x_s = String.valueOf(x);
		StringBuffer res = new StringBuffer("");
		int i;
		
		// if not even a millisecond
		if (x_s.length() <= 6) {
			res.append('0');
		}
		// if not even a second
		else if (x_s.length() <= 9) {
			res.append("0.");
			for (i = 0; i <= x_s.length()-7; i++) {
				res.append(x_s.charAt(i));
			}
		}
		else {
			for (i = 0; i < x_s.length()-9; i++) {
				res.append(x_s.charAt(i));
			}
			res.append('.');
			while (i < x_s.length()-6) {
				res.append(x_s.charAt(i));
				i++;
			}
		}
		
		return res;
	}
	
	public static String get_basename_instance(String file_name) {
		
		// Search last '/' in the String
		int pos = file_name.lastIndexOf("/");
		
		if (pos != file_name.length()-1) {
			return file_name.substring(pos+1);
		}
		else {
			return file_name;
		}
	}

}
