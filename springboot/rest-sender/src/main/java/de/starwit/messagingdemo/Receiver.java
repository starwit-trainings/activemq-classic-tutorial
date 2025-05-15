package de.starwit.messagingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

@Component
public class Receiver {

    private Logger log = LoggerFactory.getLogger(Receiver.class); 

    @JmsListener(destination = "users", containerFactory = "myFactory")
    public void receiveMessage(MyUser user) {
        log.info("received converted text message " + user.toString());
    }

    @JmsListener(destination = "serializableusers", containerFactory = "myFactory")
    public void receiveObjectMessage(Message msg) {
        try {
            MyUserSerializable user = msg.getBody(MyUserSerializable.class);
            log.info("Received object message with " + user.toString());
        } catch (JMSException e) {
            log.info("Couldn't read message " + e.getMessage());
        }
    }
}
