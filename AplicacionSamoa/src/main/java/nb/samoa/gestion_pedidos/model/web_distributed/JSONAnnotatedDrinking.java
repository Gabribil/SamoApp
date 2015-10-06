package nb.samoa.gestion_pedidos.model.web_distributed;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import nb.samoa.gestion_pedidos.model.Drinking;
import nb.samoa.gestion_pedidos.model.Product;
import nb.samoa.gestion_pedidos.model.Table;
import nb.samoa.gestion_pedidos.model.web_distributed.JSONSerializator.JSONSerializableMember;

@Entity
@javax.persistence.Table(name="Drinking")
public class JSONAnnotatedDrinking implements Serializable, Drinking {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -869160124039538620L;
	
	@Id
	@GeneratedValue
	private int drinkingId;
	
	@JSONSerializableMember
	private int quantity;
	
	@JSONSerializableMember
	private boolean preparedByBarman;
	
	@OneToOne(targetEntity=JSONAnnotatedProduct.class)
	private Product product;
	
	@ManyToOne(targetEntity=JSONAnnotatedTable.class)
	private Table table;

	public JSONAnnotatedDrinking() {
		super();
	}
	
	public JSONAnnotatedDrinking(Table jSONAnnotatedTable, Product jSONAnnotatedProduct, int quantity) {
		super();
		this.table = jSONAnnotatedTable;
		this.product = jSONAnnotatedProduct;
		this.quantity = quantity;
		this.preparedByBarman = false;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public Product getProduct() {
		return product;
	}
	
	@Override
	@JSONSerializableMember
	public int getProductId(){
		return product.getProductId();
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean isPreparedByBarman() {
		return preparedByBarman;
	}

	@Override
	public void setPreparedByBarman(boolean prepared) {
		this.preparedByBarman = prepared;
	}
	
}
