package nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import nb.samoa.gestion_pedidos.model.Model;

public class APICleanTable extends AbstractHandler {
	
	private Model model;

	public APICleanTable(Model model) {
		this.model = model;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		int tableId = Integer.parseInt(request.getParameter("tableId"));
		
		model.cleanTable(tableId);
		
		JSONObject jsonResponse = new JSONObject();
		
		jsonResponse.accumulate("status", "OK");
		
		response.getWriter().print(jsonResponse);
		
		baseRequest.setHandled(true);

	}

}
