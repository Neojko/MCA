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
		
	    if (args.length != 4) {
	      throw new IllegalArgumentException("Exactly 4 parameters required !");
	    }
    	
		// Variables
		String where_read = args[0];
		String where_write = args[1];
		String what_to_do = args[2];            // => Only c or in2 => NOT BOTH (better to have separate data in case one fails timelimit)
		int time_solve_seconds = Integer.valueOf(args[3]);
		
		/* Use it when too lazy to make a .jar to check if modified things are ok *
		String where_read = "Small_data_MCA/red-graphs-100-200/4.graph";
		String where_write = "truc";
		String what_to_do = "in2";
		int time_solve_seconds = 3;*/
		
		StringBuffer result =  new StringBuffer("");
        long lStartTime = System.nanoTime();
		
		// Dealing with "threads" for time limit
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Task myTask = new Task(what_to_do, where_read);
		Future<String> future = executor.submit(myTask);

        try {
            //System.out.println("Started..");
        	result.append(future.get(time_solve_seconds, TimeUnit.SECONDS));
            //System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            //System.out.println("Terminated!");
        }
        
        executor.shutdownNow();
        
        // Get time which is spent to solve instance
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;
        
        // Add it to result without too much precision.
        result.append(' ');
        result.append(to_seconds(output));
        result.append('\n');
        
        
        // Writing results in file
        FileOutputStream fw = null;
	    BufferedWriter bw = null;
	    
		try {

			fw = new FileOutputStream(where_write, true);
			bw = new BufferedWriter(new OutputStreamWriter(fw));
		    
			bw.write(result.toString());

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
	
	
	public static StringBuffer to_seconds(long x) {
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

}
