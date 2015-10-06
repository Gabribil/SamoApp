package nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import nb.samoa.gestion_pedidos.model.Model;

public class APIGetTables extends AbstractHandler {

	private Model model;
	private APIGetList listHandler;
	
	public APIGetTables(Model model) {
		super();
		this.model = model;
		this.listHandler = new APIGetList();
	}



	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		listHandler.setDataList(model.getAllTables());
		listHandler.handle(target, baseRequest, request, response);

	}

}
