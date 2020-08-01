package toktokserver.bootstrap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewritePatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.resource.Resource;
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
		RewriteHandler rewrite = new RewriteHandler();
		rewrite.setRewriteRequestURI(true);
		rewrite.setRewritePathInfo(false);
		rewrite.setOriginalPathAttribute("requestedPath");
		
		RewriteRegexRule rule = new RewriteRegexRule();
		rule.setRegex("\\/main|\\/introduction|\\/introduction02|\\/introduction03|\\/introduction04|\\/introduction05");
		rule.setReplacement("/index.html");
		
//		RewritePatternRule rule = new RewritePatternRule();
//		rule.setPattern("/main");
//		rule.setReplacement("/index.html");
		rewrite.addRule(rule);
		
		
		
		URL url = this.getClass().getResource("/dist/index.html");
		System.out.println(url);
		URI uri = null;
		uri = URI.create(resourceFile.toURI().toASCIIString().replaceFirst("/index.html$", "/"));
//			uri = URI.create(url.toURI().toASCIIString().replaceFirst("/index.html$", "/"));
		System.out.println(uri.toString());
		
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		handler.setContextPath("/");
		handler.setWelcomeFiles(new String[] {"index.html"});
		try {
			handler.setBaseResource(Resource.newResource(uri));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	
//		System.out.println(resourceFile.getAbsolutePath());
//		
//		PathResource pathResource = new PathResource(resourceFile);
//		ResourceHandler rsHandler = new ResourceHandler();
//		rsHandler.setDirectoriesListed(false);
//		rsHandler.setWelcomeFiles(new String[] {"index.html"});
//		rsHandler.setBaseResource(pathResource);
		
		rewrite.setHandler(handler);
		
		handler.addServlet(DefaultServlet.class, "/");
		
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
		
		
		handlers.setHandlers(new org.eclipse.jetty.server.Handler[] {rewrite, servletHandler});
		return handlers;
	}
	
}