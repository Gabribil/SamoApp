package nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import nb.samoa.gestion_pedidos.model.Model;

public class APIGetDrinkings extends AbstractHandler {

	private Model model;
	private APIGetList listHandler;
	
	public APIGetDrinkings(Model model) {
		super();
		this.model = model;
		this.listHandler = new APIGetList();
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int tableId = Integer.parseInt(baseRequest.getParameter("tableId"));
		
		listHandler.setDataList(model.getAllDrinkings(tableId));
		listHandler.handle(target, baseRequest, request, response);
	}

}
