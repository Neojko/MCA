import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.lang.StringBuffer;
import java.util.BitSet;

public class MCA_instance_FPT_in2 {
	
	// Variables
	HashMap3D_spe_in2 hmap;
	protected Graph graph;
	protected int[][] col_out_nei; // list of colors in the outneighborhood of each node
	protected int[] nb_Col_out_nei; // |col(N^+(v))| for each node
	protected ArrayList<Node> nodes_sol;
	protected ArrayList<Arc> arcs_sol;
	
	public MCA_instance_FPT_in2 (Graph example) {
		hmap = new HashMap3D_spe_in2();
		graph = example;
		col_out_nei = new int[graph.getN()][graph.getC()];
		nb_Col_out_nei = new int[graph.getN()];
		graph.compute_col_out_nei(col_out_nei,nb_Col_out_nei);
	}
	
	public HashMap3D_spe_in2 getOldHmap() {
		return hmap;
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public void short_display() {
		System.out.println("Graph containing " + graph.getN() + " vertices, " + graph.getM() + " arcs and " + graph.getC() + " colors (in2 = " + graph.getIntwo() + ").");
		System.out.println();
		
	}
	
	
	
	public void initialise_compute() {
		
		for (int i = 0; i < graph.getN(); i++) {
			BitSet bitset_col = new BitSet(graph.getC());
			BitSet bitset_out = new BitSet(graph.getC());
			hmap.addElement(Integer.valueOf(i), Integer.valueOf(0), bitset_col, new Triple(bitset_out, 0, ""));
		}
		
		//hmap.display();
	}
	
	
	
	
	public void compute() {
		
		initialise_compute();
		
		int num_outneighbor, c, color_outneighbor;
		Double first;
		Triple second;
		int current_node;
		
		StringBuffer pred = new StringBuffer("");
		int var_size;
		BitSet out_cur_node = new BitSet(graph.getC());
		BitSet merged_color_set = new BitSet(graph.getC());
		
		HashMap2DG<Integer, BitSet, Triple> innermap_node;
		HashMap2DG<Integer, BitSet, Triple> innermap_node2;
		HashMap<BitSet, Triple> innermap_size;
		HashMap<BitSet, Triple> innermap_size2;
		HashMap3D_spe_in2 new_hmap = new HashMap3D_spe_in2();
		HashMap3D_spe_in2 final_hmap = new HashMap3D_spe_in2();
		
		
		// Going from node n-1 to node 0 ensures that all outneighbors of current_node have already been computed thanks to color hierarchy
		// Indeed we can start at node n - number_leaves
		for (current_node = graph.getN()-1; current_node > -1; current_node--) {
			
			/************* For respecting time limit *************/
			if (Thread.interrupted()) {
    			Thread.currentThread().interrupt();
    			break;
    		}
			/************* For respecting time limit *************/
			
			
			if (graph.getNbOutneighbors(current_node) > 0) {
				
				// Neighbors are sorted by color
				int u = 0; // cpt outneighbors
				num_outneighbor = graph.getOutneighbors(current_node,u);
				color_outneighbor = graph.getNode(num_outneighbor).getColor();
				
				
				// For each color in the outneighborhood of current_node
				for (int index_c = 0; index_c < nb_Col_out_nei[current_node]; index_c ++) {
					
					/************* For respecting time limit *************/
					if (Thread.interrupted()) {
		    			Thread.currentThread().interrupt();
		    			break;
		    		}
					/************* For respecting time limit *************/
					
					c = col_out_nei[current_node][index_c];
					//System.out.println("--- Working on color " + c + " (not in X)");
					//System.out.println("--- FIRST PART");
					
					
					// First part
					
					// For each outneighbor of color c
					while ( (u < graph.getNbOutneighbors(current_node)) && (color_outneighbor == c) ) {
						
						/************* For respecting time limit *************/
						if (Thread.interrupted()) {
			    			Thread.currentThread().interrupt();
			    			break;
			    		}
						/************* For respecting time limit *************/
						
						//System.out.println("------" + current_node + " has outneighbor " + num_outneighbor + " of color " + c);
						
						// For each size between 0 and |X|
						for (int current_size = 0; current_size <= graph.getIntwo(); current_size++) {
							
							/************* For respecting time limit *************/
							if (Thread.interrupted()) {
				    			Thread.currentThread().interrupt();
				    			break;
				    		}
							/************* For respecting time limit *************/
							
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
									for (Map.Entry<BitSet, Triple> entry : innermap_size.entrySet()) {
										
										/************* For respecting time limit *************/
										if (Thread.interrupted()) {
							    			Thread.currentThread().interrupt();
							    			break;
							    		}
										/************* For respecting time limit *************/
										
										BitSet color_set = entry.getKey();
						            	Triple value = entry.getValue();
						            	
						            	// use key and value
						                //System.out.println("------------ For color set " + color_set + " of weight " + value.getWeight() + " :");
						                first = value.getWeight() + graph.getWeight_outneighbors(current_node,u);
						                
						                
						                // Update merged_color_set
						            	merged_color_set.clear();
						            	merged_color_set.or(color_set);
						            	
						            	if (!graph.getListIntwo().contains(color_outneighbor)) {
											var_size = current_size;
										}
										else {
											var_size = current_size + 1;
											merged_color_set.set(color_outneighbor, true);
										}
						            	
						            	
										second = new_hmap.getElement(Integer.valueOf(current_node), Integer.valueOf(var_size), 
					                			merged_color_set);
										
										//System.out.println("------------ first = " + first + " and second = " + second);
						                
						                if ( ( (second == null) || (first > second.getWeight()) ) && ( first > 0 ) ) {
						                	
						                	// Update color set of outneighbors of current_node
						                	out_cur_node.clear();
						                	out_cur_node.set(color_outneighbor, true);
						                	
						                	// Update predecessors
						                	pred.setLength(0);
						                	pred.append(String.valueOf(current_node) + '-' + String.valueOf(num_outneighbor));
						                	if ( !(value.getPred().equals("")) ) {
						                		pred.append('_' + value.getPred());
						                	}
						                	
						                	// Add new element in new_hmap (and not in hmap)
						                	new_hmap.addElement(Integer.valueOf(current_node), Integer.valueOf(var_size), 
						                			merged_color_set.get(0, merged_color_set.size()), 
						                			new Triple(out_cur_node.get(0, out_cur_node.size()),first, pred.toString()));
							                
							                /*System.out.print("New entry A (new_hmap): ");
							                System.out.print("node = " + current_node);
							                System.out.print(", size = " + var_size);
							                System.out.print(", color_set = " + merged_color_set.toString());
							                System.out.print(", Triple.out : " + out_cur_node);
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
						
						
						
						// Next outneighbor
						u++;
						num_outneighbor = graph.getOutneighbors(current_node,u);
						color_outneighbor = graph.getNode(num_outneighbor).getColor();
						
					}
					
					
					
					// Second part of the equation
					
					/************** WE SEARCH BETWEEN NEW ENTRY AND OLD ONE => S_ONE IS A NEW ENTRY ************************/
					
					// Searching subsets s_one
					//System.out.println("--- SECOND PART");
					
					// Get part of the table for node current_node
					innermap_node = new_hmap.getPartSize(Integer.valueOf(current_node));
					innermap_node2 = hmap.getPartSize(Integer.valueOf(current_node));
					
					// if both subparts of hmaps are not empty
					if ((innermap_node != null) && (innermap_node2 != null)) {
						
						// For each size between 0 and |X|
						for (int size_one = 0; size_one <= graph.getIntwo(); size_one++) {
							
							/************* For respecting time limit *************/
							if (Thread.interrupted()) {
				    			Thread.currentThread().interrupt();
				    			break;
				    		}
							/************* For respecting time limit *************/
							
							//System.out.println("------ size_one = " + size_one);
							
							// Get part of the table for color sets of size size_one
							innermap_size = innermap_node.getHashMap(Integer.valueOf(size_one));
							
							if (innermap_size != null) {
								
								// For all color sets s_one of size size_one of node current_node
								for (Map.Entry<BitSet, Triple> entry_one : innermap_size.entrySet()) {
									
									/************* For respecting time limit *************/
									if (Thread.interrupted()) {
						    			Thread.currentThread().interrupt();
						    			break;
						    		}
									/************* For respecting time limit *************/
									
									
									BitSet s_one = entry_one.getKey();
					            	Triple value_s_one = entry_one.getValue();
					            	
					            	//System.out.println("--------- s_one = " + s_one + " de Triple " + value_s_one);
					            	
					            	for (int size_two = 0; size_two <= graph.getIntwo() - size_one; size_two++) {
					            		
					            		
					            		/************* For respecting time limit *************/
										if (Thread.interrupted()) {
							    			Thread.currentThread().interrupt();
							    			break;
							    		}
										/************* For respecting time limit *************/
										
										
										//System.out.println("------------ size_two = " + size_two );
					            		
					            		// We are now searching for s_two
						            	
						            	innermap_size2 = innermap_node2.getHashMap(Integer.valueOf(size_two));
						            	
						            	if (innermap_size2 != null) {
											
											// For all color sets s_two of size (size_two) of node current_node
											for (Map.Entry<BitSet, Triple> entry_two : innermap_size2.entrySet()) {
												
												/************* For respecting time limit *************/
												if (Thread.interrupted()) {
									    			Thread.currentThread().interrupt();
									    			break;
									    		}
												/************* For respecting time limit *************/
												
												BitSet s_two = entry_two.getKey();
								            	Triple value_s_two = entry_two.getValue();
								            	
								            	//System.out.println("--------------- s_two = " + s_two + " de poids " + value_s_two);
								            	
								            	
								            	
								            	if ( ( !(s_one.intersects(s_two)) ) && ( !(value_s_one.getOut().intersects(value_s_two.getOut())) ) ) {
								            		
								            		// Update merged_color_set
								            		merged_color_set.clear();
									                merged_color_set.or(s_one);
									                merged_color_set.or(s_two);
								            		
								            		first = value_s_one.getWeight() + value_s_two.getWeight();
								            		
								            		second = final_hmap.getElement(Integer.valueOf(current_node),
								            				Integer.valueOf(size_one+size_two), merged_color_set);
								            		
								            		if (second == null) {
								            			second = new_hmap.getElement(Integer.valueOf(current_node),
									            				Integer.valueOf(size_one+size_two), merged_color_set);
								            		}
								            		
									                //System.out.println("--------------- first = " + first + " and second = " + second);
									                
									                if ( ( (second == null) || (first > second.getWeight()) ) && (first > 0) ) {
									                	
									                	// Update color set of outneighbors of current_node
									                	out_cur_node.clear();
									                	out_cur_node.or(value_s_one.getOut());
									                	out_cur_node.or(value_s_two.getOut());
									                	
									                	// Update predecessors
									                	pred.setLength(0);
									                	if ( !(value_s_one.getPred().equals("")) ) {
									                		pred.append(value_s_one.getPred());
									                		if ( !(value_s_two.getPred().equals("")) ) {
									                			pred.append("_" + value_s_two.getPred());
									                		}		
									                	}
									                	else {
									                		pred.append(value_s_two.getPred());
									                	}
									                	
									                	// Add in final_hmap
									                	final_hmap.addElement(Integer.valueOf(current_node),
									            				Integer.valueOf(size_one+size_two), merged_color_set.get(0, merged_color_set.size()), 
									            				new Triple(out_cur_node.get(0, out_cur_node.size()), first, pred.toString()));
										                
										                /*System.out.print("New entry B (final_hmap): ");
										                System.out.print("node = " + Integer.valueOf(current_node));
										                System.out.print(", size = " + Integer.valueOf(size_one+size_two));
										                System.out.print(", color set = " + merged_color_set.toString());
										                System.out.print(", Triple.out : " + out_cur_node);
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
					new_hmap.clear3D();
					final_hmap.clear3D();
					
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
		Triple best = new Triple();
		
		HashMap2DG<Integer, BitSet, Triple> hmap2d = hmap.getOuterMap().get(i);
	       
	       
		for (Map.Entry<Integer,HashMap<BitSet, Triple>> entry : hmap2d.getOuterMap().entrySet()) {
			
	           HashMap<BitSet, Triple> value = entry.getValue();
	           
	           //System.out.println("For size " + key + " : ");
	           
	           for (Map.Entry<BitSet, Triple> entrybis : value.entrySet()) {
	           	
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
