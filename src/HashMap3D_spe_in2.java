import java.util.HashMap;
import java.util.Map;

public class HashMap3D_spe_in2 extends HashMap3DG<Integer, Integer, Integer, Triple>{

	public HashMap3D_spe_in2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void complete_hmap( HashMap3DG<Integer, Integer, Integer, Triple> other) {
		
		Triple old;
		
		for (Map.Entry<Integer,HashMap2DG<Integer, Integer, Triple>> entry3D : other.getOuterMap().entrySet()) {
			Integer key3D = entry3D.getKey();
			HashMap2DG<Integer, Integer, Triple> value3D = entry3D.getValue();
	        
	        for (Map.Entry<Integer,HashMap<Integer, Triple>> entry2D : value3D.getOuterMap().entrySet()) {
	        	Integer key2D = entry2D.getKey();
	            HashMap<Integer, Triple> value2D = entry2D.getValue();
	            
	            for (Map.Entry<Integer,Triple> entry1D : value2D.entrySet()) {
	            	Integer key1D = entry1D.getKey();
	            	Triple value1D = entry1D.getValue();
	            	
	            	old = getElement(key3D, key2D, key1D);
	            	
	            	if ( (old == null) || ( (old != null) && (old.getWeight() < value1D.getWeight() ) ) ) {
	            		addElement(key3D, key2D, key1D, value1D);
	            	}
	            	
	            }
	            
	        }
		}
		
	}

}
