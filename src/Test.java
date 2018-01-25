import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



public class Test {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		/*
		// First, ensure there are 2 args : args[0] = c / in2 / both , args[1] = file_name
	    if (args.length != 2) {
	      throw new IllegalArgumentException("Exactly 2 parameters required !");
	    }
		
		// Variables
		Graph graph = new Graph(args[1]);
		//Graph graph = new Graph("Small_data_MCA/red-graphs-100-200/4.graph");
		//Graph graph = new Graph(50,8);
		
		// Don't put more than 30 colors : 2^31 = max_int + 1
		if (graph.getC() <= 30) {
			
			// if it is only asked
			if (args[0].equals("c")) {
				
				MCA_instance_FPT_C instance_c = new MCA_instance_FPT_C(graph);
				instance_c.compute();
				double res_c = instance_c.searchBest(0).getWeight();
				instance_c.retrieveSol();
				instance_c.afficheSol();	
				System.out.println("Best result with FPT C : " + res_c);
				
			}
			else if (args[0].equals("in2")) {
				
				MCA_instance_FPT_in2 instance_i = new MCA_instance_FPT_in2(graph);
				instance_i.short_display();
				instance_i.compute();
				double res_i = instance_i.searchBest(0).getWeight();
				instance_i.retrieveSol();
				instance_i.afficheSol();
				System.out.println("Best result with FPT in2 : " + res_i);
				
			}
			// do both
			else {
				
				MCA_instance_FPT_C instance_c = new MCA_instance_FPT_C(graph);
				MCA_instance_FPT_in2 instance_i = new MCA_instance_FPT_in2(graph);
				
				// display
				instance_i.short_display();
			
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
		else {
			System.out.println("Current algorithms can not handle more than 30 colors.");
		}
		
		*/
		
		
		
		
		String file = "truc";
		StringBuffer myChain = new StringBuffer("");
		
		FileOutputStream fw = null;
	    BufferedWriter bw = null;

		try {

			int a = 3;
			double b = 3.254;
			myChain.append(a);
			myChain.append(' ');
			myChain.append(b);
			myChain.append('\n');
			
			fw = new FileOutputStream(file, true);
			bw = new BufferedWriter(new OutputStreamWriter(fw));
		    
			bw.write(myChain.toString());

			System.out.println("Done");

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
