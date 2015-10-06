package nb.samoa.gestion_pedidos.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import nb.samoa.gestion_pedidos.model.Table;
import nb.samoa.gestion_pedidos.model.web_distributed.JSONAnnotatedTable;
import nb.samoa.gestion_pedidos.persistence.PersistenceManager;

public class DAOTable {
	private static DAOTable instance = null;
	private DAOTable(){}
	public static DAOTable getInstance(){return instance==null?(instance = new DAOTable()):instance;}
	
	public Table getTableById(int id){
		EntityManager em = PersistenceManager.getEntityManager();
		
		return em.find(JSONAnnotatedTable.class, id);
	}
	
	public List<Table> getAllTables() {
		EntityManager em = PersistenceManager.getEntityManager();
		List<Table> toRet = new ArrayList<>();
		
		for(Integer id:(List<Integer>)em.createNativeQuery("select tableId FROM tables").getResultList()){
			toRet.add(em.find(JSONAnnotatedTable.class, id));
		}
		
		return toRet;
	}
}
