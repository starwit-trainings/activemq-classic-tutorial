package de.starwit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;

@Service
public class MessageService {

    private Logger log = LoggerFactory.getLogger(MessageService.class); 

    @Value("${client.target}")
    String queueName;

    @Autowired
    JmsTemplate jmsTemplate;

    public boolean forwardMessage(String message) {
        log.info("Send message " + message);

        jmsTemplate.send(queueName,new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
        return true;
    }
}
