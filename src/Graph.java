import java.lang.Math; 
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Graph {

	// Variables
	protected int n; // number of nodes
	protected int m; // number of arcs
	protected int c; // number of colors
	protected List<Node> nodeList;
	
	// Stocking int takes 4 times less space than stocking Integer. Random number of second part of the table makes it more sure to have a fixed table. 
	protected int[][] outneighbors; // contains outneighbors numeros from case 0 to case nbOutneighbors-1
	protected double[][] weight_outneighbors; // contains weights of arcs towards outneighbors from case 0 to case nbOutneighbors-1
	protected int[] nbOutneighbors;
	
	
	// Random generation of a graph
	public Graph(int number_of_nodes, int number_of_colors) {
		
		// Basic attribution
		n = number_of_nodes;
		m = 0;
		c = number_of_colors;
		nodeList = new ArrayList<Node>();
		outneighbors = new int[n][n];
		nbOutneighbors = new int[n];
		weight_outneighbors = new double[n][n];
		
		
		// Determine number of occurrences of each color
		int random = 0;
		int[] occ_color = new int[c];
		// Only the root has color 0
		occ_color[0] = 1;
		for (int i=1;i<n;i++) {
			// Generate random color between 0 and c-1 (excluded)
			random = (int)(Math.random() * (c-1));
			// Adding in case random+1 because only root has color 0
			occ_color[random+1]++;
		}
		
		/*System.out.print("Color repartition : ");
		for (int i=0;i<c;i++) {
			System.out.print(occ_color[i] + " ");
		}
		System.out.println();*/
		
		int node_color;
		//String binary_color;
		int current_color = 0;
		int counter_current_color = 0;
		int cumulative_sum_colors  = occ_color[0];
		int out;
		double weight_out;
		for (int current_vertex = 0; current_vertex < n; current_vertex++) {
			
			//System.out.println("current_vertex = " + current_vertex);
			//System.out.println("current_color = " + current_color);
			
			// Create node
			node_color = (int)Math.pow(2., current_color);
			//binary_color = Integer.toBinaryString(node_color);
			Node node = new Node(node_color);
			nodeList.add(node);	
			
			// Create outneighbors (only if color of outneighbors come after current_color)
			for (out = cumulative_sum_colors; out < n; out++) {
				// Does current_vertex has out as outneighbor ?
				random = (int)(Math.random() * 2); //0 or 1
				
				if (random == 1) {
					outneighbors[current_vertex][nbOutneighbors[current_vertex]] = out;
					//Calculate weight (between -5 and 5)
					weight_out = (Math.random() * 11) - 5;
					weight_outneighbors[current_vertex][nbOutneighbors[current_vertex]] = weight_out;
					nbOutneighbors[current_vertex]++;
					m++;
				}
			}
			
			// update color
			if (counter_current_color < occ_color[current_color]-1) {
				counter_current_color++;
			}
			else {
				current_color++;
				while ( (current_color < c) && (occ_color[current_color] == 0) ) {
					current_color++;
				}
				counter_current_color = 0;
				if (current_color < c) {
					cumulative_sum_colors += occ_color[current_color];
				}
			}
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
	
	public ArrayList<Integer> compute_intwo() {
		
		int[] res = new int[c];
		ArrayList<Integer> intwo = new ArrayList<Integer>();
		int color_index, color_pow;
		
		// Initialization
		for (int i = 0; i < c; i++) {
			res[i] = -1;
		}
		
		for (int i = 0; i < n; i++) {
			//System.out.println("i = " + i + ", couleur de i = " + nodeList.get(i).getColor() + " and jmax = " + nbOutneighbors[nodeList.get(i).getNumero()]);
			
			for (int j = 0; j < nbOutneighbors[nodeList.get(i).getNumero()]; j++) {
				
				// Vertex true color
				color_pow = nodeList.get(outneighbors[i][j]).getColor();
				// Index in table of vertex true color
				color_index = (int)(Math.log(color_pow) / Math.log(2.0));
				
				//System.out.println("--- j = " + j + ", color_index = " + color_index + " and color_pow = " + color_pow);
				
				// if no color yet
				if (res[color_index] == -1) {
					res[color_index] = nodeList.get(i).getColor();
					//System.out.println("------ Pas d'innei deja entre pour la couleur " + color_pow + ", on ajoute la couleur de i.");
					
				}
				// if not same color and not already X color
				else if ( (res[color_index] != -2) && (res[color_index] != nodeList.get(i).getColor()) ) {
					
					//System.out.println("------ La couleur " + color_pow + " avait deja la couleur " + res[color_index] + " d'entree, on la rajoute a intwo.");
					
					intwo.add(color_pow);
					res[color_index] = -2;
				}
				
			}
			
		}
		
		return intwo;
		
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
