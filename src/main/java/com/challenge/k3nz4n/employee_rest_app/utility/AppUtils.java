package com.challenge.k3nz4n.employee_rest_app.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * This class is used as a helper class for other classes, in this case it mainly provide the app URI
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 7, 2019
 */
public class AppUtils {
	
	@Autowired
	Environment environment;
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(AppUtils.class);
	
	public String getAppURI() {
		String port     = environment.getProperty("server.proxy-port");
		String context  = environment.getProperty("server.servlet.contextPath");
		String hostName = "localhost";
		try {
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
			
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage());
		}
		
		String appURI = MessageFormat.format("http://{0}:{1}{2}", hostName, port, context);
		
		LOGGER.info("\n\nThe App URI is {}\n\n", appURI);
		
		return appURI;
	}
}
