package toktokserver.bootstrap;

import java.io.File;
import java.net.URI;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.slf4j.LoggerFactory;

public class HandlerFactory {
	org.slf4j.Logger logger = LoggerFactory.getLogger(HandlerFactory.class);
	private File resourceFile;
	
	public HandlerFactory() {
		resourceFile = new File("dist/");
	}
	
	public HandlerList setHandlerList() {
		if(resourceFile == null) {
			logger.error("resourceFile is empty(or can't find it)");
			System.exit(10);
		}
		PathResource pathResource = new PathResource(resourceFile);
		ResourceHandler rsHandler = new ResourceHandler();
		rsHandler.setDirectoriesListed(false);
		rsHandler.setWelcomeFiles(new String[] {"index.html"});
		rsHandler.setBaseResource(pathResource);
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new org.eclipse.jetty.server.Handler[] {rsHandler});
		return handlers;
	}
}
