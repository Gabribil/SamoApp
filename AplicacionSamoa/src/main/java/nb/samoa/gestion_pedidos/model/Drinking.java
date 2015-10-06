package nb.samoa.gestion_pedidos.model;

/**
 * Drinking keeps track of how many drinks has requested a given table 
 */
public interface Drinking {

	Product getProduct();

	int getProductId();

	int getQuantity();
	void setQuantity(int quantity);
	
	boolean isPreparedByBarman();
	void setPreparedByBarman(boolean prepared);

}