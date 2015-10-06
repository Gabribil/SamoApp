package nb.samoa.gestion_pedidos.model.web_distributed;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import nb.samoa.gestion_pedidos.model.Model;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.Error404Handler;
import nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers.NoHandlerException;

public class MainJettyHandler extends AbstractHandler {

	private Model model;
	private ResourceHandler htmlFilesHandler;
	private ServerAPI api;
	private Logger logger;
	
	public MainJettyHandler(Model model) {
		super(); //Handlers won't mutate while running
		this.model = model;
		
		this.api = new ServerAPI(this.model);
		
		this.htmlFilesHandler = new ResourceHandler();
		htmlFilesHandler.setResourceBase(getClass().getResource("/webapp").toExternalForm());
		htmlFilesHandler.setWelcomeFiles(new String[]{"camarero.html"});
		
		logger = LogManager.getLogger();

		
	}
	
	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse response) throws IOException, ServletException {
		
		logger.trace("Incoming request {}",arg1);
		
		htmlFilesHandler.handle(arg0, arg1, arg2, response);
		if(!arg1.isHandled()){
			try{
				api.getHandler(arg0).handle(arg0, arg1, arg2, response);
			} catch(NoHandlerException e) {
				Error404Handler.getInstance().handle(arg0, arg1, arg2, response);
			}
		}
		
	}
}
