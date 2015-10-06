package nb.samoa.gestion_pedidos.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	private EntityManagerFactory emf;
    private EntityManager em;
    
    public enum PersistenceUnitNames{
    	HSQLDB("PortableAplicacionSamoaPU"),
    	MYSQL("AplicacionSamoaPU");
    	
    	private String unitName;
    	
    	private PersistenceUnitNames(String unitName){
    		this.unitName = unitName;
    	}
    	
    	public String getName() {
    		return unitName;
    	}
    }
    
    private static PersistenceManager instance = null;
   
    private PersistenceManager(PersistenceUnitNames puName) {
    	emf = Persistence.createEntityManagerFactory(puName.getName());
        em = emf.createEntityManager();
    }
        
    public static void connectToDB(PersistenceUnitNames puName) {
        if (instance == null) {
            instance = new PersistenceManager(puName);
        }
    }
    
    public static EntityManager getEntityManager() {
        return instance.em;
    }
    
    public static void disconnect() {
        if (instance != null) {
            instance.em.close();
            instance.emf.close();
            instance = null;
        }
    }
}
