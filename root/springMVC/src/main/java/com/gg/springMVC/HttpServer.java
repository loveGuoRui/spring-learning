package com.gg.springMVC;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class HttpServer implements ApplicationContextAware{

	private ApplicationContext applicationContext;

	private int serverPort = 5200;

	private String webRoot = "gg";
	
	public void start() throws Exception {

		Server server = new Server(serverPort);

		WebAppContext webAppContext = new WebAppContext();

		webAppContext.setContextPath("/" + this.webRoot);
		webAppContext.setDescriptor("webapp/WEB-INF/web.xml");
		webAppContext.setResourceBase("webapp");
		webAppContext.setDefaultsDescriptor("webapp/webdefault.xml");
		webAppContext.setConfigurationDiscovered(true);
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);

		// 以下代码是关键
		webAppContext.setClassLoader(applicationContext.getClassLoader());

		XmlWebApplicationContext xmlWebAppContext = new XmlWebApplicationContext();
		xmlWebAppContext.setParent(applicationContext);
		xmlWebAppContext.setConfigLocation("");
		xmlWebAppContext.setServletContext(webAppContext.getServletContext());
		xmlWebAppContext.refresh();

		webAppContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, xmlWebAppContext);
		
		server.start();
	}
	
	
	
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

}
