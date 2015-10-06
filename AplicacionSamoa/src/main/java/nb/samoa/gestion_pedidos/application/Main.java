package nb.samoa.gestion_pedidos.application;

import java.io.File;

import nb.samoa.gestion_pedidos.model.Model;
import nb.samoa.gestion_pedidos.model.web_distributed.WebDistributedModel;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.setProperty("log4j.configurationFile", new File(Main.class.getResource("/META-INF/log4j.xml").toURI()).getAbsolutePath());
					
			Model model = new WebDistributedModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
