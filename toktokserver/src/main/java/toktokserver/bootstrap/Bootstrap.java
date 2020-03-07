package toktokserver.bootstrap;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Bootstrap.class);
		ServerFactory serverFactory = new ServerFactory();
		Server server = serverFactory.create();
		
		ResourceHandler rsHandler = new ResourceHandler();
		File file = new File("");
		PathResource pathResource = new PathResource(file);
		
		rsHandler.setDirectoriesListed(false);
		rsHandler.setWelcomeFiles(new String[] {"index.html"});
		rsHandler.setBaseResource(pathResource);
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {rsHandler});
		
		server.setHandler(handlers);
		
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			logger.error("Fail to server start: ");
			logger.error("Fail to server start: ", e);
		}
	}
}
