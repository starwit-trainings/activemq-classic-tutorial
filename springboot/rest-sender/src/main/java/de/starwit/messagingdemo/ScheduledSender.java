package de.starwit.messagingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledSender {

    private Logger log = LoggerFactory.getLogger(ScheduledSender.class); 

    @Value(value = "{client.target}")
    String queueName;
    
    @Autowired
    JmsTemplate jmsTemplate;

    private int number = 0;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        log.info("sending message number " + number);
        jmsTemplate.convertAndSend("users", new MyUser("TestUser-" + number, "hans@meiser.com"));        
        jmsTemplate.send(queueName, s->s.createObjectMessage(new MyUserSerializable("TestUser-" + number, "hans@meiser.com")));
        number++;
    }
}
