/**
 * 
 */
package com.ksource.syscontext;


import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author zhaoxy
 * 
 */
public class SpringContext {
	static ApplicationContext applicationContext = null;

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		SpringContext.applicationContext = applicationContext;

	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void closeContext() {
		if (applicationContext != null) {
			SpringContext.applicationContext
					.publishEvent(new ContextClosedEvent(
							SpringContext.applicationContext));
			if (applicationContext instanceof ConfigurableWebApplicationContext) {
				((ConfigurableWebApplicationContext) applicationContext)
						.close();
				((ConfigurableWebApplicationContext) applicationContext)
						.getServletContext()
						.removeAttribute(
								WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			}
		}

	}


	/*public static Scheduler getScheduler() {
		return (Scheduler) SpringContext.getBeanOfType(Scheduler.class);
	}

	public static void rollbackTrans() {
		try {
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly())
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
		} catch (Exception e) {

		}
	}*/
}
