import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.lang.StringBuffer;

public class MCA_instance_FPT_in2 extends MCA_instance {
	
	// Variables
	HashMap3D_spe_in2 hmap;
	protected ArrayList<Integer> intwo;
	protected int[][] col_out_nei; // list of colors in the outneighborhood of each node
	protected int[] nb_Col_out_nei; // |col(N^+(v))| for each node
	protected ArrayList<Node> nodes_sol;
	protected ArrayList<Arc> arcs_sol;
	
	public MCA_instance_FPT_in2 (Graph example) {
		hmap = new HashMap3D_spe_in2();
		graph = example;
		intwo = graph.compute_intwo();
		col_out_nei = new int[graph.getN()][graph.getC()];
		nb_Col_out_nei = new int[graph.getN()];
		graph.compute_col_out_nei(col_out_nei,nb_Col_out_nei);
	}
	
	public HashMap3DG<Integer, Integer, Integer, Triple> getOldHmap() {
		return hmap;
	}
	
	public void short_display() {
		System.out.println("Graph containing " + graph.getN() + " vertices, " + graph.getM() + " arcs and " + graph.getC() + " colors (in2 = " + intwo.size() + ").");
		System.out.println();
		
	}
	
	// a and b of form 'X_Y_Z...' with X, Y and Z that are node numbers.
	public boolean disjoint_strings(String a, String b) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		int i = 0;
		int tmp = 0;
		
		// adding node numbers from a
		while (i < a.length()) {
			//System.out.println("a.charAt(" + i + ") = " + a.charAt(i));
			// if we have to actualize tmp
			if (a.charAt(i) != '_') {
				tmp = 10 * tmp + Character.getNumericValue(a.charAt(i));
			}
						
			// if we have to actualize list
			if ( (a.charAt(i) == '_') || (i == a.length() -1)) {
				//System.out.println("We want to add tmp = " + tmp);
				// Either it's finished
				if (list.contains(tmp)) {
					return false;
				}
				else {
					list.add(tmp);
					tmp = 0;
				}
			}
			i++;
			//System.out.println("tmp = " + tmp);
		}
		
		// same for b except if two same numbers
		i = 0;
		tmp = 0;
		while (i < b.length()) {
			//System.out.println("b.charAt(" + i + ") = " + b.charAt(i));
			
			// if we have to actualize tmp
			if (b.charAt(i) != '_') {
				tmp = 10 * tmp + Character.getNumericValue(b.charAt(i));
			}
			
			// if we have to actualize list
			if ( (b.charAt(i) == '_') || (i == b.length() -1)) {
				//System.out.println("We want to add tmp = " + tmp);
				// Either it's finished
				if (list.contains(tmp)) {
					return false;
				}
				else {
					list.add(tmp);
					tmp = 0;
				}
			}
			i++;
			//System.out.println("tmp = " + tmp);
		}
		
		return true;
	}
	
	
	
	public void initialise_compute() {
		
		for (int i = 0; i < graph.getN(); i++) {
			hmap.addElement(Integer.valueOf(graph.getNode(i).getNumero()), Integer.valueOf(0), Integer.valueOf(0), new Triple("", 0, ""));
		}
		
		//hmap.display();
	}
	
	
	
	
	public void compute() {
		
		initialise_compute();
		
		int num_outneighbor, c;
		Double first;
		Triple second;
		int current_node;
		StringBuffer toAdd = new StringBuffer(""), pred = new StringBuffer("");
		int var_size, var_col;
		
		HashMap2DG<Integer, Integer, Triple> innermap_node;
		HashMap2DG<Integer, Integer, Triple> innermap_node2;
		HashMap<Integer, Triple> innermap_size;
		HashMap<Integer, Triple> innermap_size2;
		
		
		// Going from node n-1 to node 0 ensures that all outneighbors of current_node have already been computed thanks to color hierarchy
		// Indeed we can start at node n - number_leaves
		for (int v = graph.getN()-1; v > -1; v--) {
			current_node  = graph.getNode(v).getNumero();
			//System.out.println("-------------------------------------------------------");
			//System.out.println("current_node = " + graph.getNode(current_node).getNumero());
			
			
			if (graph.getNbOutneighbors(current_node) > 0) {
				
				// For each color in the outneighborhood of current_node
				for (int index_c = 0; index_c < nb_Col_out_nei[current_node]; index_c ++) {
					
					HashMap3DG<Integer, Integer, Integer, Triple> new_hmap = new HashMap3DG<Integer, Integer, Integer, Triple>();
					HashMap3DG<Integer, Integer, Integer, Triple> final_hmap = new HashMap3DG<Integer, Integer, Integer, Triple>();
					
					c = col_out_nei[current_node][index_c];
					//System.out.println("--- Working on color " + c + " (not in X)");
					//System.out.println("--- FIRST PART");

					
					// For each outneighbor of current_node
					for (int u = 0; u < graph.getNbOutneighbors(current_node); u++) {
						
						num_outneighbor = graph.getOutneighbors(current_node,u);
						
						
						// vertex u must be of color c
						if (graph.getNode(num_outneighbor).getColor() == c) {
							
							//System.out.println("------" + current_node + " has outneighbor " + num_outneighbor + " of color " + c);
							
					
							// For each size between 0 and |X|
							for (int current_size = 0; current_size <= intwo.size(); current_size++) {
								
								//System.out.println("--------- current_size = " + current_size);

								// Get part of the table for node u
								innermap_node = hmap.getPartSize(Integer.valueOf(num_outneighbor));
								if (innermap_node != null) {
									//System.out.println("innermap_node display : ");
									//innermap_node.display();
									
									// Get part of the table for color sets of size current_size
									innermap_size = innermap_node.getHashMap(Integer.valueOf(current_size));
									
									if (innermap_size != null) {
										//System.out.println(innermap_size);
										
										// For all color sets of size current_size of node u
										for (Map.Entry<Integer, Triple> entry : innermap_size.entrySet()) {
							            	Integer color_set = entry.getKey();
							            	Triple value = entry.getValue();
							            	
							            	// use key and value
							                //System.out.println("------------ For color set " + color_set + " of weight " + value.getWeight() + " :");
							                first = value.getWeight() + graph.getWeight_outneighbors(current_node,u);
							                
							                // if c does not belong to X
											if (!intwo.contains(c)) {
												var_size = current_size;
												var_col = color_set;
											}
											else {
												var_size = current_size + 1;
												var_col = color_set + graph.getNode(num_outneighbor).getColor();
											}
							                
											second = new_hmap.getElement(Integer.valueOf(graph.getNode(current_node).getNumero()), Integer.valueOf(var_size), 
						                			Integer.valueOf(var_col));
											
											//System.out.println("------------ first = " + first + " and second = " + second);
							                
							                if ( ( (second == null) || (first > second.getWeight()) ) && ( first > 0 ) ) {
							                	toAdd.setLength(0);
							                	toAdd.append(String.valueOf(graph.getNode(num_outneighbor).getColor()));
							                	
							                	pred.setLength(0);
							                	pred.append(String.valueOf(current_node) + '-' + String.valueOf(num_outneighbor));
							                	if (value.getPred() != "") {
							                		pred.append('_' + value.getPred());
							                	}
							                	
							                	
							                	new_hmap.addElement(Integer.valueOf(graph.getNode(current_node).getNumero()), Integer.valueOf(var_size), 
							                			Integer.valueOf(var_col), new Triple(toAdd.toString(),first, pred.toString()));
								                
								                /*System.out.print("New entry A (new_hmap): ");
								                System.out.print("node = " + current_node);
								                System.out.print(", size = " + var_size);
								                System.out.print(", color_set = " + var_col);
								                System.out.print(", Triple.out : " + toAdd);
								                System.out.print(", Triple.weight : " + first);
								                System.out.println(", Triple.pred = " + pred);
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
							} // end for each size
						}// end if neighbor has color c
					} // end for each outneighbor
					
					
					// Second part of the equation
					
					/************** WE SEARCH BETWEEN NEW ENTRY AND OLD ONE => S_ONE IS A NEW ENTRY ************************/
					
					// Searching subsets s_one
					//System.out.println("--- SECOND PART");
					
					// Get part of the table for node current_node
					innermap_node = new_hmap.getPartSize(Integer.valueOf(graph.getNode(current_node).getNumero()));
					innermap_node2 = hmap.getPartSize(Integer.valueOf(graph.getNode(current_node).getNumero()));
					
					// if both subparts of hmaps are not empty
					if ((innermap_node != null) && (innermap_node2 != null)) {
						
						// For each size between 0 and |X|
						for (int size_one = 0; size_one <= intwo.size(); size_one++) {
							
							//System.out.println("------ size_one = " + size_one);
							
							// Get part of the table for color sets of size size_one
							innermap_size = innermap_node.getHashMap(Integer.valueOf(size_one));
							
							if (innermap_size != null) {
								
								// For all color sets s_one of size size_one of node current_node
								for (Map.Entry<Integer, Triple> entry_one : innermap_size.entrySet()) {
					            	Integer s_one = entry_one.getKey();
					            	Triple value_s_one = entry_one.getValue();
					            	
					            	//System.out.println("--------- s_one = " + s_one + " de Triple " + value_s_one);
					            	
					            	for (int size_two = 0; size_two <= intwo.size() - size_one; size_two++) {
										
										//System.out.println("------------ size_two = " + size_two );
					            		
					            		// We are now searching for s_two
						            	
						            	innermap_size2 = innermap_node2.getHashMap(Integer.valueOf(size_two));
						            	
						            	if (innermap_size2 != null) {
											
											// For all color sets s_two of size (size_two) of node current_node
											for (Map.Entry<Integer, Triple> entry_two : innermap_size2.entrySet()) {
								            	Integer s_two = entry_two.getKey();
								            	Triple value_s_two = entry_two.getValue();
								            	
								            	//System.out.println("--------------- s_two = " + s_two + " de poids " + value_s_two);
								            	
								            	if ( (are_disjoint(s_one, s_two) && ( disjoint_strings(value_s_one.getOut(), value_s_two.getOut()) ) ) ) {
								            		
								            		first = value_s_one.getWeight() + value_s_two.getWeight();
								            		
								            		second = final_hmap.getElement(Integer.valueOf(graph.getNode(current_node).getNumero()),
								            				Integer.valueOf(size_one+size_two), (s_one+s_two));
								            		
								            		if (second == null) {
								            			second = new_hmap.getElement(Integer.valueOf(graph.getNode(current_node).getNumero()),
									            				Integer.valueOf(size_one+size_two), (s_one+s_two));
								            		}
								            		
									                //System.out.println("--------------- first = " + first + " and second = " + second);
									                
									                if ( ( (second == null) || (first > second.getWeight()) ) && (first > 0) ) {
									                	toAdd.setLength(0);
									                	toAdd.append(value_s_one.getOut() + '_' + value_s_two.getOut());
									                	
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
									                	
									                	final_hmap.addElement(Integer.valueOf(graph.getNode(current_node).getNumero()),
									            				Integer.valueOf(size_one+size_two), (s_one+s_two), new Triple(toAdd.toString(), first, pred.toString()));
										                
										                /*System.out.print("New entry B (final_hmap): ");
										                System.out.print("node = " + Integer.valueOf(graph.getNode(current_node).getNumero()));
										                System.out.print(", size = " + Integer.valueOf(size_one+size_two));
										                System.out.print(", color_set = " + (s_one+s_two));
										                System.out.print(", Triple.out : " + toAdd);
										                System.out.print(", Triple.weight : " + first);
										                System.out.println(", Triple.pred = " + pred);
										                System.out.println();*/
									                }
									                
								            	} // end if disjoint out colors and colors
								                
								            	
											} // end for all s_two
											
										} // end innermap_size2 != null
					            		
							        } // end for all size_two
					            	
								} // end for all s_one
								
							}// end innermap_size != null
								
							
						} // end for all size_one
					
					} // end if part hmap node is ok
					//System.out.println();
					
					/****** AJOUTER TOUTES LES NOUVELLES ENTREES A HMAP ET CLEAR LA NOUVELLE ******/
					hmap.complete_hmap(final_hmap);
					hmap.complete_hmap(new_hmap);
					//new_hmap.clear3D();
					//final_hmap.clear3D();
					
					/*System.out.println("Affichage hmap apres completion");
					hmap.display2();
					System.out.println("Affichage new_hmap");
					new_hmap.display2();
					System.out.println();
					System.out.println("Affichage final_hmap");
					final_hmap.display2();
					System.out.println();*/
					
					
					
				} // end for each color
				
			} // end if node has out
			
		} // end for each node
		
	} // end function
	
	// Search best weight for a vertex
	public Triple searchBest(Integer i) {
			
		double res = -1;
		Triple best = hmap.getOuterMap().get(i).getElement(0, 0);
		
		HashMap2DG<Integer, Integer, Triple> hmap2d = hmap.getOuterMap().get(i);
	       
	       
		for (Map.Entry<Integer,HashMap<Integer, Triple>> entry : hmap2d.getOuterMap().entrySet()) {
			
	           HashMap<Integer, Triple> value = entry.getValue();
	           
	           //System.out.println("For size " + key + " : ");
	           
	           for (Map.Entry<Integer, Triple> entrybis : value.entrySet()) {
	           	
	           	Triple valuebis = entrybis.getValue();
	           	
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
