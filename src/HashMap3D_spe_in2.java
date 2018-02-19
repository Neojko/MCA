import java.util.HashMap;
import java.util.Map;
import java.util.BitSet;

public class HashMap3D_spe_in2 extends HashMap3DG<Integer, Integer, BitSet, Triple>{

	public HashMap3D_spe_in2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void complete_hmap( HashMap3DG<Integer, Integer, BitSet, Triple> other) {
		
		Triple old;
		
		for (Map.Entry<Integer,HashMap2DG<Integer, BitSet, Triple>> entry3D : other.getOuterMap().entrySet()) {
			Integer key3D = entry3D.getKey();
			HashMap2DG<Integer, BitSet, Triple> value3D = entry3D.getValue();
	        
	        for (Map.Entry<Integer,HashMap<BitSet, Triple>> entry2D : value3D.getOuterMap().entrySet()) {
	        	Integer key2D = entry2D.getKey();
	            HashMap<BitSet, Triple> value2D = entry2D.getValue();
	            
	            for (Map.Entry<BitSet,Triple> entry1D : value2D.entrySet()) {
	            	BitSet key1D = entry1D.getKey();
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
