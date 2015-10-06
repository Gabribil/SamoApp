package nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;

import nb.samoa.gestion_pedidos.model.web_distributed.JSONSerializator;

public class APIGetList extends AbstractHandler {

	List<? extends Object> dataList;
	
	
	
	public APIGetList() {
		super();
	}
	
	public void setDataList(List<? extends Object> dataList) {
		this.dataList = dataList;
	}



	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		//JSONEncodeBuilder
		JSONArray jsonResponse = new JSONArray();
		
		for(Object obj: dataList){
			jsonResponse.put(JSONSerializator.serializeToJSON(obj));
		}
		
		response.getWriter().print(jsonResponse);
		
		baseRequest.setHandled(true);
	}

}
