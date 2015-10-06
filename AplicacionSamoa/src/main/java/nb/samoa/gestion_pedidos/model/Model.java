package nb.samoa.gestion_pedidos.model;

import java.util.List;

public interface Model {

	List<Table> getAllTables();

	Table getTableById(int tableId);

	List<Product> getAllProducts();

	List<Drinking> getAllDrinkings(int tableId);

	void createDrinking(int tableId, int int1, int int2);

	void cleanTable(int tableId);
	
	void initialize();

}
