public class Node {
	
	//Attributs
	public static int number_of_created_nodes = 0;
	protected int color;
	protected int numero;
	
	// Creating node with given color
	public Node(int given_color) {
		this.numero = number_of_created_nodes;
		this.color = given_color;
		number_of_created_nodes++;
	}

	//Getters et Setters
	public static int getNumber_of_created_nodes() {
		return number_of_created_nodes;
	}
	public int getNumero() {
		return numero;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int new_color) {
		this.color = new_color;
	}
	
	@Override
	public String toString() {
		return "Node [" + numero + ", (" + color + ")]";
	}
	
	

}
