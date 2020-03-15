package toktokserver.bootstrap;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class ServerFactory {
	private String localhost = "";
	Logger logger = Logger.getLogger(ServerFactory.class);
	
	public Server create() {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
//		getHostAddress();
//		if(checkIp(localhost)) {
//			http.setHost(localhost);
//			http.setPort(12731);
//			http.setIdleTimeout(30000);
//		}
		http.setHost("localhost");
		http.setPort(12731);
		http.setIdleTimeout(30000);
		
		server.addConnector(http);
		
		return server;
	}
	
	private void getHostAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while(networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				String name = networkInterface.getName();
				String displayName = networkInterface.getDisplayName();
				if(!networkInterface.isLoopback()) {
					Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
					while(inetAddresses.hasMoreElements()) {
						InetAddress inetAddress = inetAddresses.nextElement();
						if(!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
							localhost = inetAddress.getHostAddress();
							System.out.println(localhost);
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
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
