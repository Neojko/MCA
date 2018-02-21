import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.lang.IndexOutOfBoundsException;
import java.lang.NumberFormatException;

public class Graph {

	// Variables
	protected int n; // number of nodes
	protected int m; // number of arcs
	protected int c; // number of colors
	protected int intwo; // number of colors of indegree at least 2 in \h
	protected ArrayList<Integer> list_intwo;
	protected int s; // number of arcs we need to remove in \h in order to make it a tree.
	protected List<Node> nodeList;
	
	// Stocking int takes 4 times less space than stocking Integer. Random number of second part of the table makes it more sure to have a fixed table. 
	protected int[][] outneighbors; // contains outneighbors numeros from case 0 to case nbOutneighbors-1
	protected double[][] weight_outneighbors; // contains weights of arcs towards outneighbors from case 0 to case nbOutneighbors-1
	protected int[] nbOutneighbors;
	
	// Check if instance is correct
	boolean correct_instance;
	String molecule_name;
	
	
	/****************         Generate graph from file      ************************/
	public Graph(String nomInstance) {
		
		correct_instance = true;
		try
		{
			FileInputStream fIs = new FileInputStream(new File(nomInstance));
		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(fIs));
		    
			
		    try
		    {
		    	String line;
			    String[] word_sep;
			    int read_col;
			    int tmp;
			    int first;
			    int second;
			    double weight;
		    	int count_n = 0;
			    
		    	// Get n, m and c
		    	line = br.readLine();
		    	word_sep = line.split("\\t");
		    	
		    	// Basic attribution
		    	molecule_name = word_sep[1];
		    	n = Integer.valueOf(word_sep[2]);
				m = Integer.valueOf(word_sep[3]);
				c = 0;
				
				nodeList = new ArrayList<Node>();
				
				
				/**************** Generate vertices ********/
				
				// Create vertices with colors
				// first node
				line = br.readLine();
		    	read_col = 0;
				nodeList.add(new Node(0));
				count_n++;
				
				// other nodes
				line = br.readLine();
		    	word_sep = line.split("\\t");
		    	
		    	while (count_n < n) {
		    		
		    		// change color if second term is different from previous line
		    		tmp = Integer.valueOf(word_sep[1]);
		    		if (tmp != read_col) {
		    			read_col = tmp;
		    			c++;
		    		}
		    		
		    		// add new node
		    		nodeList.add(new Node(c));
		    		count_n++;
		    		
		    		// keep reading
		    		line = br.readLine();
			    	word_sep = line.split("\\t");
		    		
		    	}
				
				// remember number of colors
				c++;
				
				/*********** Generate arcs ******/
				intwo = 0;
				s = 0;
				list_intwo = new ArrayList<Integer>();
				outneighbors = new int[n][n];
				weight_outneighbors = new double[n][n];
				nbOutneighbors = new int[n];
				boolean[][] in = new boolean[c][c]; // color i has inneighbor j in \h if in[i][j] = 1
		    	
				int count_m = 0;
		    	while (count_m < m) {
		    		word_sep = line.split("\\t");
		    		
		    		first = Integer.valueOf(word_sep[0]);
		    		second = Integer.valueOf(word_sep[1]);
		    		weight = Double.valueOf(word_sep[2]);
		    		
		    		//System.out.println("first = " + first + ", second = " + second + " and weight = " + weight);
		    		
		    		// Build the arc
		    		outneighbors[first][nbOutneighbors[first]] = second;
		    		weight_outneighbors[first][nbOutneighbors[first]] = weight;
					nbOutneighbors[first]++;
					
					// For intwo and s
					in[nodeList.get(second).getColor()][nodeList.get(first).getColor()] = true;
					
					count_m++;
					
					// keep reading
		    		line = br.readLine();
		    	}
		    	
		    	
		    	// computing intwo and s
		    	int cpt;
		    	for (int i = 0; i < c; i++) {
		    		cpt = 0;
		    		for (int j = 0; j < c; j++) {
		    			if (in[i][j]) {
		    				cpt++;
		    			}
		    		}
		    		if (cpt > 1) {
		    			intwo++;
		    			list_intwo.add(i);
		    			s+=cpt-1;
		    		}
		    	}
		    	
		    	
		    	
		        br.close();
		    
		    
		    }
		    catch (NullPointerException exception) {
		    	correct_instance = false;
		    	//System.out.println("1");
		    }
		    catch (IndexOutOfBoundsException exception) {
		    	correct_instance = false;
		    	//System.out.println("2");
		    }
		    catch (IOException exception)
		    {
		    	correct_instance = false;
		    	//System.out.println("3");
		    }
		    catch (NumberFormatException exception)
		    {
		    	correct_instance = false;
		    	//System.out.println("4");
		    }
		}
		catch (FileNotFoundException exception)
		{
			correct_instance = false;
			//System.out.println("4");
		}
		
	}
	
	
	//Getters and setters
	public int getN() {
		return n;
	}
	public int getC() {
		return c;
	}
	public int getM() {
		return m;
	}
	public int getIntwo() {
		return intwo;
	}
	public ArrayList<Integer> getListIntwo() {
		return list_intwo;
	}
	public int getS() {
		return s;
	}
	public String getMolecule() {
		return molecule_name;
	}
	public Node getNode(int i) {
		return nodeList.get(i);
	}
	
	public int getOutneighbors(int node, int index) {
		return outneighbors[node][index];
	}
	
	public double getWeight_outneighbors(int node, int index) {
		return weight_outneighbors[node][index];
	}
	
	public int getNbOutneighbors(int node) {
		return nbOutneighbors[node];
	}
	
	public boolean is_instance_correct() {
		return correct_instance;
	}
	
	
	public void compute_col_out_nei(int[][] col, int[] nb) {
		
		// For all vertices
		for (int i = 0; i < n; i++) {
			//System.out.println("i = " + i);
			nb[i] = 0;
			
			if (nbOutneighbors[i] > 0) {
				
				col[i][nb[i]] = nodeList.get(outneighbors[i][0]).getColor();
				//System.out.println("col[" + i + "][nb[" + i + "]] = " + col[i][nb[i]]);
				nb[i]++;
				//System.out.println("nb[" + i + "] = " + nb[i]);
				
				
				// For all other outneighbors
				for (int j = 1; j < nbOutneighbors[i]; j++) {
					if ( nodeList.get(outneighbors[i][j]).getColor() != nodeList.get(outneighbors[i][j-1]).getColor() ) {
						col[i][nb[i]] = nodeList.get(outneighbors[i][j]).getColor();
						//System.out.println("col[" + i + "][nb[" + i + "]] = " + col[i][nb[i]]);
						nb[i]++;
						//System.out.println("nb[" + i + "] = " + nb[i]);
					}
				}
				
			}
		}
		
	}


	@Override
	

	// Display
	
	public String toString() {
		String result;
		result = "Graph containing " + n + " vertices, " + m + " arcs and " + c + " colors. \n";
		result += "Node list : \n";
		// Show nodes
		for (int i=0;i<n;i++) {
			result += nodeList.get(i).toString() + " with " + nbOutneighbors[nodeList.get(i).getNumero()] + " outneighbors : ";
			
			for (int j=0;j<nbOutneighbors[i];j++) {
				result += outneighbors[i][j] + " (w = " + weight_outneighbors[i][j] + ") ";
			}
			result += "\n";
			
		}
		return result;
	}
	
	public void short_display() {
		System.out.println("Graph containing " + n + " vertices, " + m + " arcs and " + c + " colors.");
		System.out.println();
	}
	
	
	
	
	
	

}
