import java.util.HashMap;


public class HashMap2DG<A,B,C> {
    private HashMap <A,HashMap<B,C>> outerMap;

    public HashMap2DG() {
        outerMap = new HashMap<A, HashMap<B,C>>();
    }

    public void addElement(A a, B b, C c) {
    	HashMap<B, C> innerMap = outerMap.get(a);
        if (innerMap==null) {
            innerMap = new HashMap<B, C>();
            outerMap.put(a,innerMap);
        }
        innerMap.put(b, c);
    }

    public C getElement(A a, B b) {
        HashMap<B, C> innerMap = outerMap.get(a);
        if (innerMap==null) {
            return null;
        }
        return innerMap.get(b);
    }
    
    public void display() {
    	
    	for (HashMap.Entry<A,HashMap<B, C>> entry : outerMap.entrySet()) {
            A key = entry.getKey();
            HashMap<B, C> value = entry.getValue();
            
            // use key and value
            System.out.println("For A " + key + " : ");
            
            for (HashMap.Entry<B, C> entrybis : value.entrySet()) {
            	B keybis = entrybis.getKey();
            	C valuebis = entrybis.getValue();
            	
            	// use key and value
                System.out.println("Value for key " + keybis + " : " + valuebis);
            	
            }
            
        }
    }
    
    public HashMap<B,C> getHashMap(A a) {
    	HashMap<B,C> innerMap = outerMap.get(a);
        if (innerMap==null) {
            return null;
        }
        return innerMap;
    	
    }

	public HashMap<A, HashMap<B, C>> getOuterMap() {
		return outerMap;
	}
    
	public void clear2D() {
    	for (HashMap.Entry<A,HashMap<B, C>> entry : outerMap.entrySet()) {
            entry.getValue().clear();
        }
    }
    
    
    
}