package com.ksource.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ksource.common.docconvert.OpenOfficePDFConverter;

public class ServerListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//OpenOfficePDFConverter.stopService();
	}

}
