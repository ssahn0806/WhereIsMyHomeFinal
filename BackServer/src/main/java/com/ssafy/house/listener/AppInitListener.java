package com.ssafy.house.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener{
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		application.setAttribute("root",application.getContextPath());
		System.out.println("root set...");
	}
}
