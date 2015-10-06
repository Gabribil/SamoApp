package nb.samoa.gestion_pedidos.model.web_distributed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;

import nb.samoa.gestion_pedidos.model.Drinking;
import nb.samoa.gestion_pedidos.model.Model;
import nb.samoa.gestion_pedidos.model.Product;
import nb.samoa.gestion_pedidos.model.Table;
import nb.samoa.gestion_pedidos.persistence.PersistenceManager;
import nb.samoa.gestion_pedidos.persistence.PersistenceManager.PersistenceUnitNames;
import nb.samoa.gestion_pedidos.persistence.dao.DAODrinking;
import nb.samoa.gestion_pedidos.persistence.dao.DAOProduct;
import nb.samoa.gestion_pedidos.persistence.dao.DAOTable;

public class WebDistributedModel implements Model {
	
	private Logger logger;
	private org.hsqldb.server.Server hsqldbInstance;
	private org.eclipse.jetty.server.Server jettyInstance;
	
	public WebDistributedModel() throws Exception {
		
		logger = LogManager.getLogger();
		
		
		logger.trace("Starting connection to database");
		
		hsqldbInstance = new org.hsqldb.server.Server();
		try {

			File[] initialDatabaseFiles = new File(this.getClass().getClassLoader().getResource("initialDatabase").toURI()).listFiles();

			Path destDir = Files.createTempDirectory("samoapp");

			for(File f:initialDatabaseFiles){
				InputStream is = new FileInputStream(f);
				OutputStream os = new FileOutputStream(new File(destDir+File.separator+f.getName()));

				while(is.available()>0){
					os.write(is.read());
				}
				os.flush();
				os.close();
				is.close();
			}

			String databaseName = initialDatabaseFiles[0].getName();
			databaseName = databaseName.substring(0, databaseName.lastIndexOf('.'));

			hsqldbInstance.setDatabaseName(0, databaseName);
			hsqldbInstance.setDatabasePath(0, destDir.toUri().toString()+databaseName);
			hsqldbInstance.setAddress("localhost");
			hsqldbInstance.start();

			PersistenceManager.connectToDB(PersistenceUnitNames.HSQLDB);

		} catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		logger.trace("Starting Jetty web server");
		jettyInstance = new Server(8800);
		jettyInstance.setHandler(new MainJettyHandler(this));
		jettyInstance.start();
		jettyInstance.dumpStdErr();
	}
	
	@Override
	public void initialize() {
		try {
			jettyInstance.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Table> getAllTables(){
		return DAOTable.getInstance().getAllTables();
	}

	@Override
	public Table getTableById(int tableId) {
		return DAOTable.getInstance().getTableById(tableId);
	}

	@Override
	public List<Product> getAllProducts() {
		return DAOProduct.getInstance().getAllProducts();
	}

	@Override
	public List<Drinking> getAllDrinkings(int tableId) {
		return DAODrinking.getInstance().getAllDrinkings(tableId);
	}

	@Override
	public void createDrinking(int tableId, int productId, int quantity) {
		JSONAnnotatedDrinking actualDrinking = DAODrinking.getInstance().getDrinking(this.getTableById(tableId), this.getProductById(productId));
		if(actualDrinking == null) DAODrinking.getInstance().create(this.getTableById(tableId), this.getProductById(productId), quantity);
		else {
			actualDrinking.setQuantity(actualDrinking.getQuantity()+quantity);
			DAODrinking.getInstance().update(actualDrinking);
		}
	}

	private Product getProductById(int productId) {
		return DAOProduct.getInstance().getProductById(productId);
	}

	@Override
	public void cleanTable(int tableId) {
		DAODrinking.getInstance().deleteTableDrinkings(tableId);
	}	
	
}
