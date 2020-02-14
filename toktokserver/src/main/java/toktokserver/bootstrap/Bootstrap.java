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
		Server server = new Server();

		
		logger.info("server initiate");
		ServerConnector http = new ServerConnector(server);
		String localhost = "";
		
		try {
			InetAddress local = InetAddress.getLocalHost();
			localhost = local.getHostAddress();
			System.out.println(localhost);
		} catch (UnknownHostException e) {
			logger.info("Fail to get LocalHost");
			e.printStackTrace();
		}
		
		if (!localhost.isEmpty()) {
			http.setHost(localhost);
			http.setPort(15732);
			http.setIdleTimeout(30000);
		} else {
			logger.error("LocalHost is empty");
			logger.error("server shutdown");
			System.exit(1);
		}
		
		server.addConnector(http);
		
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
