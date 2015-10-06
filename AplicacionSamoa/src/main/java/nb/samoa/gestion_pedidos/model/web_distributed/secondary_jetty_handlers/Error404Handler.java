package nb.samoa.gestion_pedidos.model.web_distributed.secondary_jetty_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class Error404Handler extends AbstractHandler {

	private static Error404Handler instance;
	
	private Error404Handler() {}
	
	
	
	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse arg3)
			throws IOException, ServletException {
//		arg3.setContentType("text/html; charset=utf-8");
//		arg3.setStatus(HttpServletResponse.SC_NOT_FOUND);
//		arg3.getWriter().println("<h3>Error 404: Requested resource ("+arg0+") not found</h3>");
		arg3.sendError(404);
		arg1.setHandled(true);

	}

	public static AbstractHandler getInstance() {
		if(instance == null) instance = new Error404Handler();
		return instance;
	}

}
