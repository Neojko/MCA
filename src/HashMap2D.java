import java.util.HashMap;
import java.util.Map;


public class HashMap2D {
    private HashMap <Integer,HashMap<Integer, Double>> outerMap;

    public HashMap2D() {
        outerMap = new HashMap<Integer,HashMap<Integer, Double>>();
    }

    public HashMap<Integer, HashMap<Integer, Double>> getOuterMap() {
		return outerMap;
	}

	public void addElement(Integer size_subset, Integer color, Double weight) {
    	HashMap<Integer, Double> innerMap = outerMap.get(size_subset);
        if (innerMap==null) {
            innerMap = new HashMap<Integer, Double>();
            outerMap.put(size_subset,innerMap);
        }
        innerMap.put(color, weight);
    }

    public Double getElement(Integer size_subset, Integer color) {
        HashMap<Integer, Double> innerMap = outerMap.get(size_subset);
        if (innerMap==null) {
            return null;
        }
        return innerMap.get(color);
    }
    
    public void display() {
    	
    	for (Map.Entry<Integer,HashMap<Integer, Double>> entry : outerMap.entrySet()) {
            Integer key = entry.getKey();
            HashMap<Integer, Double> value = entry.getValue();
            
            // use key and value
            System.out.println("For size " + key + " : ");
            
            for (Map.Entry<Integer, Double> entrybis : value.entrySet()) {
            	Integer keybis = entrybis.getKey();
            	Double valuebis = entrybis.getValue();
            	
            	// use key and value
                System.out.println("Best solution for color set " + keybis + " : " + valuebis);
            	
            }
            
        }
    }
    
    public HashMap<Integer, Double> getHashMap(Integer size) {
    	HashMap<Integer, Double> innerMap = outerMap.get(size);
        if (innerMap==null) {
            return null;
        }
        return innerMap;
    	
    }
    
    
}