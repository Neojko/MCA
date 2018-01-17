import java.util.HashMap;


public class HashMap3DG<A,B,C,D> {
    private HashMap <A, HashMap2DG<B,C,D> > outerMap;

    public HashMap3DG() {
        outerMap = new HashMap <A, HashMap2DG<B,C,D> >();
    }

    public void addElement(A a, B b, C c, D d) {
    	HashMap2DG<B,C,D> innerMap = outerMap.get(a);
        if (innerMap==null) {
            innerMap = new HashMap2DG<B,C,D>();
            outerMap.put(a,innerMap);
        }
        innerMap.addElement(b, c, d);
    }
    
    public HashMap2DG<B,C,D> getPartSize(A a) {
    	HashMap2DG<B,C,D> innerMap = outerMap.get(a);
        if (innerMap==null) {
            return null;
        }
        return innerMap;
    }

    public D getElement(A a, B b, C c) {
    	HashMap2DG<B,C,D> innerMap = outerMap.get(a);
        if (innerMap==null) {
            return null;
        }
        return innerMap.getElement(b, c);
    }
    
    public void display() {
    	
    	for (HashMap.Entry<A,HashMap2DG<B,C,D>> entry : outerMap.entrySet()) {
            A key = entry.getKey();
            HashMap2DG<B,C,D> value = entry.getValue();
            
            // use key and value
            System.out.println("For key " + key + " : ");
            value.display();
            System.out.println();
            
        }
    }

	public HashMap<A, HashMap2DG<B,C,D>> getOuterMap() {
		return outerMap;
	}

	public void display2() {
		
		for (HashMap.Entry<A,HashMap2DG<B,C,D>> entry3D : outerMap.entrySet()) {
			A key3D = entry3D.getKey();
			HashMap2DG<B,C,D> value3D = entry3D.getValue();
	        
	        System.out.println("for node " + key3D + " : ");
	        
	        for (HashMap.Entry<B,HashMap<C,D>> entry2D : value3D.getOuterMap().entrySet()) {
	            B key2D = entry2D.getKey();
	            HashMap<C,D> value2D = entry2D.getValue();
	            
	            // use key and value
	            System.out.println("--- for size " + key2D + " : ");
	            
	            for (HashMap.Entry<C,D> entry1D : value2D.entrySet()) {
	            	C key1D = entry1D.getKey();
	            	D value1D = entry1D.getValue();
	            	
	            	// use key and value
	                System.out.println("------ for color_set " + key1D + " : " + value1D);
	            	
	            }
	            
	        }
	        System.out.println();
		}
	}
	
	public void clear3D( ) {
		for (HashMap.Entry<A,HashMap2DG<B,C,D>> entry3D : outerMap.entrySet()) {
			entry3D.getValue().clear2D();
		}
	}
	
	
    
    
    
    
    
    
    
}