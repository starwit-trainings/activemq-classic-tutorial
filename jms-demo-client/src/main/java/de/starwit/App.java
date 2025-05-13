package de.starwit;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    private static ActiveMQConnectionFactory factory;
    private static Connection connection;
    private static Session session;

    public static void main(String[] args) {
        String url = "tcp://127.0.0.1:61616";
        url = "failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61626)";
        String user = "admin";
        String pw = "admin";

        factory = new ActiveMQConnectionFactory(user, pw, url);
        factory.setClientID("sample-client-"+factory.toString());

        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(session.createQueue("broker-test"));
            consumer.setMessageListener(new MyListener());
            log.info("Connected to broker");
        } catch (JMSException e) {
            log.error("couldn't connect to broker " + url + " " + e.getMessage());
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutting down and closing ressources.");
            try {
                session.close();
                connection.close();
            } catch (JMSException e) {
                log.error("Couldn't close connection " + e.getMessage());
            }
        }));
        
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.warn("Message consuming Thread got interrupted " + e.getMessage());
            }
        }
    }

    private static class MyListener implements MessageListener {
        private final Logger log = LogManager.getLogger(this.getClass());

        @Override
        public void onMessage(Message message) {
            TextMessage msg = (TextMessage) message;
            try {
                log.info("received message" + msg.getText());
            } catch (JMSException e) {
                log.warn("Can't parse text message " + e.getMessage());
            }
        }
    }
}
