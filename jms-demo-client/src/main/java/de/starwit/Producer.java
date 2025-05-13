package de.starwit;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = "tcp://127.0.0.1:61616";
    
    private static String user = "admin";
    private static String pw = "admin";

    private static ActiveMQConnectionFactory factory;
    public static void main( String[] args ) throws JMSException, InterruptedException {

        url = "failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61626)";

        factory = new ActiveMQConnectionFactory(user, pw, url);

        Connection connection = factory.createConnection();
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(session.createQueue("broker-test"));

        for (int i = 0; i<500; i++) {
            TextMessage msg = session.createTextMessage("Test " + i);
            producer.send(msg);
            System.out.println("Message send " + i + " " + msg);
            Thread.sleep(1000);
        }
        producer.close();
        session.close();
        connection.close();
    }

}
