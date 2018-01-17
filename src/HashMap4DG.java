import java.util.HashMap;
import java.util.Map;


public class HashMap4DG<A,B,C,D,E> {
    private HashMap <A, HashMap3DG<B,C,D,E> > outerMap;

    public HashMap4DG() {
        outerMap = new HashMap <A, HashMap3DG<B,C,D,E> >();
    }

    public void addElement(A a, B b, C c, D d, E e) {
    	HashMap3DG<B,C,D,E> innerMap = outerMap.get(a);
        if (innerMap==null) {
            innerMap = new HashMap3DG<B,C,D,E>();
            outerMap.put(a,innerMap);
        }
        innerMap.addElement(b, c, d, e);
    }
    
    public HashMap3DG<B,C,D,E> getPartSize(A a) {
    	HashMap3DG<B,C,D,E> innerMap = outerMap.get(a);
        if (innerMap==null) {
            return null;
        }
        return innerMap;
    }

    public E getElement(A a, B b, C c, D d) {
    	HashMap3DG<B,C,D,E> innerMap = outerMap.get(a);
        if (innerMap==null) {
            return null;
        }
        return innerMap.getElement(b, c, d);
    }
    
    public void display() {
    	
    	for (Map.Entry<A,HashMap3DG<B,C,D,E>> entry : outerMap.entrySet()) {
            A key = entry.getKey();
            HashMap3DG<B,C,D,E> value = entry.getValue();
            
            // use key and value
            System.out.println("For key " + key + " : ");
            value.display();
            System.out.println();
            
        }
    }

	public HashMap<A, HashMap3DG<B,C,D,E>> getOuterMap() {
		return outerMap;
	}

	public void display2() {
		for (Map.Entry<A,HashMap3DG<B,C,D,E>> entry4D : outerMap.entrySet()) {
			A key4D = entry4D.getKey();
			HashMap3DG<B,C,D,E> value4D = entry4D.getValue();
	        
	        System.out.println("For A " + key4D + " : ");
	        
	        for (Map.Entry<B,HashMap2DG<C,D,E>> entry3D : value4D.getOuterMap().entrySet()) {
	            B key3D = entry3D.getKey();
	            HashMap2DG<C,D,E> value3D = entry3D.getValue();
	            
	            // use key and value
	            System.out.println("For B " + key3D + " : ");
	            
	            for (Map.Entry<C,HashMap<D,E>> entry2D : value3D.getOuterMap().entrySet()) {
	            	C key2D = entry2D.getKey();
	            	HashMap<D,E> value2D = entry2D.getValue();
	            	
	            	// use key and value
	                System.out.println("Value for key " + key2D + " : ");
	                
	                for (Map.Entry<D,E> entry1D : value2D.entrySet()) {
		            	D key1D = entry1D.getKey();
		            	E value1D = entry1D.getValue();
		            	
		            	// use key and value
		                System.out.println("Value for key " + key1D + " : " + value1D);
		                
		            	
		            }
	            }
	            
	        }
	        System.out.println();
		}
	}
	
	
    
    
    
    
    
    
    
}