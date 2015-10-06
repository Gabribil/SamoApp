package nb.samoa.gestion_pedidos.model.web_distributed;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import nb.samoa.gestion_pedidos.model.Product;
import nb.samoa.gestion_pedidos.model.web_distributed.JSONSerializator.JSONSerializableMember;

@Entity
@javax.persistence.Table(name="Product")
public class JSONAnnotatedProduct implements Serializable, Product {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4860963177449237850L;


	@Id
	@JSONSerializableMember
	private int productId;

	@JSONSerializableMember
	private String name;

	@JSONSerializableMember
	private float price;
	
	
	public JSONAnnotatedProduct() {
		super();
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getProductId() {
		return productId;
	}

	@Override
	public float getPrice() {
		return price;
	}

}
