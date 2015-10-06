package nb.samoa.gestion_pedidos.model.web_distributed;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.handler.AbstractHandler;

import nb.samoa.gestion_pedidos.model.Model;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.APICleanTable;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.APIGetDrinkings;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.APIGetProducts;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.APIGetTables;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.APISetDrinkings;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.NoHandlerException;

public class ServerAPI {
	private Map<String, AbstractHandler> handlers;
	
	public ServerAPI(Model model) {
		super();
		
		this.handlers = new HashMap<>();
		
		this.handlers.put("/getTables", new APIGetTables(model));
		this.handlers.put("/getDrinkings", new APIGetDrinkings(model));
		this.handlers.put("/setDrinkings", new APISetDrinkings(model));
		this.handlers.put("/getProducts", new APIGetProducts(model));
		this.handlers.put("/cleanTable", new APICleanTable(model));
		
	}
	
	public AbstractHandler getHandler(String methodURL) throws NoHandlerException{
		AbstractHandler selectedHandler = handlers.get(methodURL);
		
		if(selectedHandler == null) { // Wrong URL passed 
			throw new NoHandlerException();
		}
		
		return selectedHandler;
	}
	
}
