import java.util.HashMap;
import java.util.Map;


public class HashMap3D {
    private HashMap <Integer, HashMap2D> outerMap;

    public HashMap3D() {
        outerMap = new HashMap <Integer, HashMap2D>();
    }

    public void addElement(Integer node_numero, Integer size_subset, Integer color, Double weight) {
    	HashMap2D innerMap = outerMap.get(node_numero);
        if (innerMap==null) {
            innerMap = new HashMap2D();
            outerMap.put(node_numero,innerMap);
        }
        innerMap.addElement(size_subset, color, weight);
    }
    
    public HashMap2D getPartSize(Integer node_numero) {
    	HashMap2D innerMap = outerMap.get(node_numero);
        if (innerMap==null) {
            return null;
        }
        return innerMap;
    }

    public Double getElement(Integer node_numero, Integer size_subset, Integer color) {
    	HashMap2D innerMap = outerMap.get(node_numero);
        if (innerMap==null) {
            return null;
        }
        return innerMap.getElement(size_subset, color);
    }
    
    public void display() {
    	
    	for (Map.Entry<Integer,HashMap2D> entry : outerMap.entrySet()) {
            Integer key = entry.getKey();
            HashMap2D value = entry.getValue();
            
            // use key and value
            System.out.println("For node number " + key + " : ");
            value.display();
            System.out.println();
            
        }
    }
    
    public void display(Integer i) {
    	
    	HashMap2D value = outerMap.get(i);
        
        // use key and value
        System.out.println("For node number " + i + " : ");
        value.display();
        System.out.println();
        
    } 

	public HashMap<Integer, HashMap2D> getOuterMap() {
		return outerMap;
	}

	public void setOuterMap(HashMap<Integer, HashMap2D> outerMap) {
		this.outerMap = outerMap;
	}
	
	
	// Search best weight for a vertex
	public double searchBest(Integer i) {
		
		double res = 0;
		
		HashMap2D hmap2d = outerMap.get(i);
        
        
		for (Map.Entry<Integer,HashMap<Integer, Double>> entry : hmap2d.getOuterMap().entrySet()) {
			
            HashMap<Integer, Double> value = entry.getValue();
            
            //System.out.println("For size " + key + " : ");
            
            for (Map.Entry<Integer, Double> entrybis : value.entrySet()) {
            	
            	Double valuebis = entrybis.getValue();
            	
            	if (valuebis > res) {
            		res = valuebis;
            	}
            	
            }
            
        }
		return res;
		
	}
    
    
    
    
    
    
    
}