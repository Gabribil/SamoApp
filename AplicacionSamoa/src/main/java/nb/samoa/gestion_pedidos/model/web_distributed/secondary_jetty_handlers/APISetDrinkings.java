package nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import nb.samoa.gestion_pedidos.model.Model;

public class APISetDrinkings extends AbstractHandler {

	private Model model;
	
	
	public APISetDrinkings(Model model) {
		super();
		this.model = model;
	}
	

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		JSONObject jsonRequest = new JSONObject(request.getReader().readLine());
		
		int tableId = jsonRequest.getInt("tableId");
		
		JSONArray drinkings = jsonRequest.getJSONArray("newDrinkings");
		
		for(int i = 0; i<drinkings.length(); i++){
			model.createDrinking(tableId, drinkings.getJSONObject(i).getInt("productId"), drinkings.getJSONObject(i).getInt("quantity"));
		}
		
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		JSONObject jsonResponse = new JSONObject();
		
		jsonResponse.accumulate("status", "OK");
		
		response.getWriter().print(jsonResponse);
		
		baseRequest.setHandled(true);
	}

}
