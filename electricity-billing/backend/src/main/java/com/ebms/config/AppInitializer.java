package com.ebms.config;

import org.apache.derby.drda.NetworkServerControl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.PrintWriter;
import java.net.InetAddress;

@WebListener
public class AppInitializer implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			NetworkServerControl server = new NetworkServerControl(InetAddress.getByName("0.0.0.0"), 1527);
			try {
				server.ping();
			} catch (Exception e) {
				server.start(new PrintWriter(System.out));
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(250);
						server.ping();
						break;
					} catch (Exception ignored) { }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}