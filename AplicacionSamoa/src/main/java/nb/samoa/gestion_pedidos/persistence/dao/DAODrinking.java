package nb.samoa.gestion_pedidos.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import nb.samoa.gestion_pedidos.model.Drinking;
import nb.samoa.gestion_pedidos.model.Product;
import nb.samoa.gestion_pedidos.model.Table;
import nb.samoa.gestion_pedidos.model.web_distributed.JSONAnnotatedDrinking;
import nb.samoa.gestion_pedidos.persistence.PersistenceManager;

public class DAODrinking {

	private static DAODrinking instance = null;
	private DAODrinking(){}
	public static DAODrinking getInstance(){return instance==null?(instance = new DAODrinking()):instance;}
	
	
	public List<Drinking> getAllDrinkings(int tableId) {
		EntityManager em = PersistenceManager.getEntityManager();
		List<Drinking> toRet = new ArrayList<>();
		
		for(Integer id:(List<Integer>)em.createNativeQuery("SELECT drinkingId FROM Drinking WHERE table_tableId = "+tableId).getResultList()){
			toRet.add(em.find(JSONAnnotatedDrinking.class, id));
		}
		
		return toRet;
	}
	
	public void create(Table jSONAnnotatedTable, Product jSONAnnotatedProduct, int quantity) {
		EntityManager em = PersistenceManager.getEntityManager();
		
		em.getTransaction().begin();
		em.persist(new JSONAnnotatedDrinking(jSONAnnotatedTable, jSONAnnotatedProduct, quantity));
		em.getTransaction().commit();
	}
	
	public JSONAnnotatedDrinking getDrinking(Table tableById, Product productById) {
		EntityManager em = PersistenceManager.getEntityManager();
		
		try {
			return em.find(JSONAnnotatedDrinking.class, em.createNativeQuery("SELECT drinkingId FROM Drinking WHERE table_tableId= "+tableById.getTableId()+" AND product_productId="+productById.getProductId()).getSingleResult());
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public void update(JSONAnnotatedDrinking actualDrinking) {
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		em.merge(actualDrinking);
		em.getTransaction().commit();
	}
	
	public void deleteTableDrinkings(int tableId) {
		EntityManager em = PersistenceManager.getEntityManager();
		
		em.getTransaction().begin();
		em.createNativeQuery("DELETE FROM Drinking WHERE table_tableId = "+tableId).executeUpdate();
		em.getTransaction().commit();
	}

}
