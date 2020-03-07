package toktokserver.bootstrap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class ServerFactory {
	private String localhost = "";
	Logger logger = Logger.getLogger(ServerFactory.class);
	
	public Server create() {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		if(checkIp(getLocalHost())) {
			http.setHost(localhost);
			http.setPort(15732);
			http.setIdleTimeout(30000);
		}
		
		server.addConnector(http);
		
		return server;
	}
	
	private String getLocalHost() {
		try {
			InetAddress local = InetAddress.getLocalHost();
			localhost = local.getHostAddress();
			System.out.println(localhost);
		} catch (UnknownHostException e) {
			logger.info("Fail to get LocalHost");
			e.printStackTrace();
		}
		return localhost;
	}
	
	private boolean checkIp(String localhost) {
		if (localhost.isEmpty()) {
			logger.error("LocalHost is empty");
			logger.error("server shutdown");
			System.exit(1);
			return false;
		}
		return true;

	}
}
