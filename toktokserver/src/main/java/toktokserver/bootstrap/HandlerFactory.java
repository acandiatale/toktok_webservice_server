package toktokserver.bootstrap;

import java.io.File;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.resource.PathResource;
import org.slf4j.LoggerFactory;

import toktok.eventhandler.DoWorkServlet;

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
		
		ServletContextHandler context = new ServletContextHandler();
		
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(DoWorkServlet.class, "/do");
		
		FilterHolder cors = new FilterHolder(CrossOriginFilter.class);
		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,POST,HEAD");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, "false");
		cors.setName("cross-origin");
		FilterMapping fm = new FilterMapping();
		fm.setFilterName("cross-origin");
		fm.setPathSpec("*");
		servletHandler.addFilter(cors, fm);
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new org.eclipse.jetty.server.Handler[] {rsHandler, servletHandler});
		return handlers;
	}
	
}