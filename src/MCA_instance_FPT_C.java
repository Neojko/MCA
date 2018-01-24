import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MCA_instance_FPT_C extends MCA_instance {
	
	// Variables
	protected HashMap3DG<Integer, Integer, Integer, Paire> hmap;
	protected ArrayList<Node> nodes_sol;
	protected ArrayList<Arc> arcs_sol;
	
	public MCA_instance_FPT_C (int number_of_nodes, int number_of_colors) {
		hmap = new HashMap3DG<Integer, Integer, Integer, Paire>();
		graph = new Graph(number_of_nodes, number_of_colors);
	}
	
	// To compare results on same graph
	public MCA_instance_FPT_C (Graph example) {
		hmap = new HashMap3DG<Integer, Integer, Integer, Paire>();
		graph = example;
	}
	
	public HashMap3DG<Integer, Integer, Integer, Paire> getHmap() {
		return hmap;
	}
		
	
	// FPT algorithm
	
	public void initialise_compute() {
		
		for (int i = 0; i < graph.getN(); i++) {
			hmap.addElement(Integer.valueOf(graph.getNode(i).getNumero()), Integer.valueOf(1), Integer.valueOf(graph.getNode(i).getColor()), new Paire(0, ""));
		}
		
		//hmap.display();
	}
	
	
	
	
	public void compute() {
		
		initialise_compute();
		
		int num_outneighbor;
		Double first;
		Paire second;
		StringBuffer pred = new StringBuffer("");
		
		HashMap2DG<Integer, Integer, Paire> innermap_node;
		HashMap<Integer, Paire> innermap_size;
		HashMap<Integer, Paire> innermap_size2;
		
		
		// Going from node n-1 to node 0 ensures that all outneighbors of current_node have already been computed thanks to color hierarchy
		// Indeed we can start at node n - number_leaves
		for (int current_node = graph.getN()-1; current_node > -1; current_node--) {
			//System.out.println("-------------------------------------------------------");
			//System.out.println("current_node = " + current_node);
			
			if (graph.getNbOutneighbors(current_node) > 0) {
				// For each size between 2 and c
				for (int current_size = 2; current_size < graph.getC()+1; current_size++) {
					//System.out.println("-------- current_size = " + current_size);
					//System.out.println();
					
					// For each outneighbor of current_node
					for (int u = 0; u < graph.getNbOutneighbors(current_node); u++) {
						//System.out.println(current_node + " has outneighbor " + outneighbors[current_node][u]);
						num_outneighbor = graph.getOutneighbors(current_node,u);
						
						// Get part of the table for node u
						innermap_node = hmap.getPartSize(Integer.valueOf(num_outneighbor));
						if (innermap_node != null) {
							//System.out.println("innermap_node display : ");
							//innermap_node.display();
							
							// Get part of the table for color sets of size current_size-1
							innermap_size = innermap_node.getHashMap(Integer.valueOf(current_size-1));
							
							if (innermap_size != null) {
								//System.out.println(innermap_size);
								
								// For all color sets of size current_size-1 of node u
								for (Map.Entry<Integer, Paire> entry : innermap_size.entrySet()) {
					            	Integer color_set = entry.getKey();
					            	Paire value = entry.getValue();
					            	
					            	// use key and value
					                //System.out.println("For color set " + color_set + " of weight " + value + " :");
					                first = value.getWeight() + graph.getWeight_outneighbors(current_node,u);
					                second = hmap.getElement(Integer.valueOf(graph.getNode(current_node).getNumero()), Integer.valueOf(current_size), 
				                			Integer.valueOf(color_set+graph.getNode(current_node).getColor()));
					                //System.out.println("first = " + first + " and second = " + second);
					                
					                if ( ( (second == null) || (first > second.getWeight()) ) && ( first > 0 ) ) {
					                	
					                	pred.setLength(0);
					                	pred.append(String.valueOf(current_node) + '-' + String.valueOf(num_outneighbor));
					                	if (value.getPred() != "") {
					                		pred.append('_' + value.getPred());
					                	}
					                	
					                	hmap.addElement(Integer.valueOf(graph.getNode(current_node).getNumero()), Integer.valueOf(current_size), 
					                			Integer.valueOf(color_set+graph.getNode(current_node).getColor()), new Paire(first, pred.toString()));
						                
						                /*System.out.print("New entry A : ");
						                System.out.print("node = " + Integer.valueOf(graph.getNode(current_node).getNumero()));
						                System.out.print(", size = " + Integer.valueOf(current_size));
						                System.out.print(", color_set = " + Integer.valueOf(color_set+graph.getNode(current_node).getColor()));
						                System.out.print(", paire.weight : " + first);
						                System.out.println(", paire.pred : " + pred);
						                System.out.println();*/
					                }
					                
					            }
								
								
								
							}
							/*else {
								System.out.println("No innermap_size for size " + (current_size-1) );
								System.out.println();
							}*/
							
						}
						else {
							System.out.println("CODE PROBLEM : no innermap_node.");
							System.out.println();
						}
						
					} // end for each outneighbor
					
					
					// Second part of the equation
					// Searching subsets s_one
					for (int sub_cur_size = 1; sub_cur_size < current_size; sub_cur_size++) {
						//System.out.println("sub_cur_size = " + sub_cur_size);
						
						// Get part of the table for node current_node
						innermap_node = hmap.getPartSize(Integer.valueOf(graph.getNode(current_node).getNumero()));
						if (innermap_node != null) {
							// Get part of the table for color sets of size sub_cur_size
							innermap_size = innermap_node.getHashMap(Integer.valueOf(sub_cur_size));
							
							if (innermap_size != null) {
								
								// For all color sets s_one of size sub_cur_size of node current_node
								for (Map.Entry<Integer, Paire> entry_one : innermap_size.entrySet()) {
					            	Integer s_one = entry_one.getKey();
					            	Paire value_s_one = entry_one.getValue();
					            	
					            	//System.out.println("--------------------- la j'ai s_one = " + s_one + " de poids " + value_s_one);
					            	
					            	// We are now searching for s_two
					            	// Get part of the table for color sets of size current_size - sub_cur_size +1
									innermap_size2 = innermap_node.getHashMap(Integer.valueOf(current_size - sub_cur_size +1));
									
									if (innermap_size2 != null) {
										
										// For all color sets s_two of size (current_size - sub_cur_size +1) of node current_node
										for (Map.Entry<Integer, Paire> entry_two : innermap_size2.entrySet()) {
							            	Integer s_two = entry_two.getKey();
							            	Paire value_s_two = entry_two.getValue();
							            	
							            	//System.out.println("--------------------- la j'ai s_two = " + s_two + " de poids " + value_s_two);
							            	
							            	if (are_disjoint(s_one-graph.getNode(current_node).getColor(), s_two-graph.getNode(current_node).getColor())) {
							            		
							            		first = value_s_one.getWeight() + value_s_two.getWeight();
							            		
							            		second = hmap.getElement(Integer.valueOf(graph.getNode(current_node).getNumero()),
							            				Integer.valueOf(current_size), (s_one+s_two-graph.getNode(current_node).getColor()));
							            		
								                //System.out.println("first = " + first + " and second = " + second);
								                
								                if ( ( (second == null) || (first > second.getWeight()) ) && (first > 0) ) {
								                	
								                	
								                	pred.setLength(0);
								                	if (value_s_one.getPred() != "") {
								                		pred.append(value_s_one.getPred());
								                		if (value_s_two.getPred() != "") {
								                			pred.append("_" + value_s_two.getPred());
								                		}		
								                	}
								                	else {
								                		pred.append(value_s_two.getPred());
								                	}
								                	
								                	
								                	hmap.addElement(Integer.valueOf(graph.getNode(current_node).getNumero()),
								            				Integer.valueOf(current_size), (s_one+s_two-graph.getNode(current_node).getColor()), new Paire(first, pred.toString()));
									                
									                /*System.out.print("New entry B : ");
									                System.out.print("node = " + Integer.valueOf(graph.getNode(current_node).getNumero()));
									                System.out.print(", size = " + Integer.valueOf(current_size));
									                System.out.print(", color_set = " + (s_one+s_two-graph.getNode(current_node).getColor()));
									                System.out.print(", paire.weight : " + first);
									                System.out.println(", paire.pred : " + pred);
									                System.out.println();*/
								                }
								                
							            	}
							                
										}
										
									}
					            	
					            }
								
							}
							
						}
						
					}
					//System.out.println();
					
				} // end for each current_size
				//System.out.println();
				
			} // end if node has outneighbors
			//System.out.println();
			
			
		}
		
		
	}
	
	
	// Search best weight for a vertex
		public Paire searchBest(Integer i) {
				
			double res = -1;
			Paire best = hmap.getOuterMap().get(i).getElement(0, 0);
			
			HashMap2DG<Integer, Integer, Paire> hmap2d = hmap.getOuterMap().get(i);
		       
		       
			for (Map.Entry<Integer,HashMap<Integer, Paire>> entry : hmap2d.getOuterMap().entrySet()) {
				
		           HashMap<Integer, Paire> value = entry.getValue();
		           
		           //System.out.println("For size " + key + " : ");
		           
		           for (Map.Entry<Integer, Paire> entrybis : value.entrySet()) {
		           	
		           	Paire valuebis = entrybis.getValue();
		           	
		           	if (valuebis.getWeight() > res) {
		           		res = valuebis.getWeight();
		           		best = valuebis;
		           	}
		            	
		           }
		            
		       }
			return best;
			
		}
	
	
		public void retrieveSol() {		
			
			nodes_sol = new ArrayList<Node>();
			arcs_sol = new ArrayList<Arc>();
			
			String check = searchBest(0).getPred();
			//System.out.println(check);
			
			int curseur = 0;
			boolean turn = true;
			int node_one = 0;
			int node_two = 0;
			
			// Add root
			nodes_sol.add(graph.getNode(node_one));
			
			// String looking like node1-node2_node1-node2 ..
			while (curseur < check.length()) {
				
				//System.out.println("char = " + check.charAt(curseur) + " : ");
				
				// Changing node
				if (check.charAt(curseur) == '-') {
					turn = false;
				}
				
				// ready to add node 2 and arc
				else if (check.charAt(curseur) == '_') {
					
					// add node 2
					if (!nodes_sol.contains(graph.getNode(node_two))) {
						nodes_sol.add(graph.getNode(node_two));
						//System.out.println("Adding node_two = " + node_two);
					}
					
					// add arc
					arcs_sol.add(new Arc(node_one, node_two));
					//System.out.println("Adding arc from node_one = " + node_one + " to node_two = " + node_two);
					
					// reinitializing
					node_one = 0;
					node_two = 0;
					turn = true;
				}
				
				// building node_two or node_two
				else {
					if (turn) {
						node_one = 10 * node_one + Character.getNumericValue(check.charAt(curseur));
						//System.out.println("node_one = " + node_one);
					}
					else {
						node_two = 10 * node_two + Character.getNumericValue(check.charAt(curseur));
						//System.out.println("node_two = " + node_two);
					}
				}
				
				curseur++;
			}
			
			// Ending
			// add node 2
			if (!nodes_sol.contains(graph.getNode(node_two))) {
				nodes_sol.add(graph.getNode(node_two));
				//System.out.println("Adding node_two = " + node_two);
			}
			
			// add arc
			arcs_sol.add(new Arc(node_one, node_two));
			//System.out.println("Adding arc from node_one = " + node_one + " to node_two = " + node_two);
			
		}
	
	
	public void afficheSol() {
		System.out.print("Nodes display : ");
		System.out.print(nodes_sol.get(0).getNumero());
		for (int i = 1; i < nodes_sol.size(); i++) {
			System.out.print(", " + nodes_sol.get(i).getNumero());
		}
		System.out.println();
		
		System.out.print("Arcs display : ");
		if (arcs_sol.size() > 0) {
			System.out.print(arcs_sol.get(0));
			for (int i = 1; i < arcs_sol.size(); i++) {
				System.out.print(", " + arcs_sol.get(i));
			}
		}
		System.out.println();
		
		
		
	}
	
	
	
	
	
	
}
