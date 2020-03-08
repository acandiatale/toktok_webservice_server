package toktokserver.bootstrap;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Bootstrap.class);
		ServerFactory serverFactory = new ServerFactory();
		Server server = serverFactory.create();
	
		HandlerFactory handlerFactory = new HandlerFactory();
		
		server.setHandler(handlerFactory.setHandlerList());
		
		try {
			System.out.println("ready to start");
			server.start();
			System.out.println("server start");
			server.join();
		} catch (Exception e) {
			logger.error("Fail to server start: ", e);
		}
	}
}
