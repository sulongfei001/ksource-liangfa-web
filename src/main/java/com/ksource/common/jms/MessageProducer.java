package com.ksource.common.jms;
 
import java.util.HashMap;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
 
@Component
public class MessageProducer {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
	
	@Resource
    private JmsTemplate jmsTemplate;
    
	@Resource(name="queueDestination")
	private Destination queueDestination;
    
    public void sendMessage(final HashMap<String, String> msgMap) {
    	try{
            jmsTemplate.send(queueDestination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                	ObjectMessage objectMessage = session.createObjectMessage(msgMap);
                	objectMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000*60);
                	return objectMessage;
                }
            });
    	}catch(Exception e){
    		LOGGER.error("JMS服务器连接异常，文档转换失败，请检查文档转化服务器并手动进行转换", e);
    	}

    }

}
