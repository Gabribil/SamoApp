package nb.samoa.gestion_pedidos.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import nb.samoa.gestion_pedidos.model.Product;
import nb.samoa.gestion_pedidos.model.web_distributed.JSONAnnotatedProduct;
import nb.samoa.gestion_pedidos.persistence.PersistenceManager;

public class DAOProduct {

	private static DAOProduct instance = null;
	private DAOProduct(){}
	public static DAOProduct getInstance(){return instance==null?(instance = new DAOProduct()):instance;}
	
	
	public List<Product> getAllProducts() {
		EntityManager em = PersistenceManager.getEntityManager();
		List<Product> toRet = new ArrayList<>();
		
		for(Integer id:(List<Integer>)em.createNativeQuery("select productId FROM Product").getResultList()){
			toRet.add(em.find(JSONAnnotatedProduct.class, id));
		}
		
		return toRet;
	}
	
	public Product getProductById(int productId) {
		EntityManager em = PersistenceManager.getEntityManager();
		return em.find(JSONAnnotatedProduct.class, productId);
	}

}
