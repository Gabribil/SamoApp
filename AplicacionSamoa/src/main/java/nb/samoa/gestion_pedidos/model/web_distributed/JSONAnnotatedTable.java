package nb.samoa.gestion_pedidos.model.web_distributed;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import nb.samoa.gestion_pedidos.model.Drinking;
import nb.samoa.gestion_pedidos.model.Table;
import nb.samoa.gestion_pedidos.model.web_distributed.JSONSerializator.JSONSerializableMember;

@Entity
@javax.persistence.Table(name="tables")
public class JSONAnnotatedTable implements Serializable, Table {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 897888541059001837L;

	private static int numberOfTables = 0;
	
	@Id
	@JSONSerializableMember
	private int tableId;
	
	@JSONSerializableMember
	private String tableName;
	
	@OneToMany(mappedBy="table", targetEntity=JSONAnnotatedDrinking.class)
	private List<Drinking> jSONAnnotatedDrinkings;
	
	public JSONAnnotatedTable(String tableName) {
		this.tableId = JSONAnnotatedTable.numberOfTables;
		JSONAnnotatedTable.numberOfTables++;
		this.tableName = tableName;
	}
	
	public JSONAnnotatedTable(){
		this("Mesa "+JSONAnnotatedTable.numberOfTables);
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public int getTableId() {
		return tableId;
	}
	
}
