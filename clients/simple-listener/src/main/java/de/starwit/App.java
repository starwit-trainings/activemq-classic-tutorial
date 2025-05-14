package de.starwit;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    private static ActiveMQConnectionFactory factory;
    private static Connection connection;
    private static Session session;

    static Properties config = new Properties();

    public static void main(String[] args) {
        loadProperties();

        String url = config.getProperty("broker.url");
        String user = config.getProperty("broker.username");
        String pw = config.getProperty("broker.password");
        String queueName = config.getProperty("client.target");

        factory = new ActiveMQConnectionFactory(user, pw, url);
        factory.setClientID("sample-client-"+factory.toString());

        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(session.createQueue(queueName));
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

    private static void loadProperties() {
        try (InputStream in = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if(in != null) {
                config.load(in);
            } else {
                log.error("Can't find property file");
                System.exit(1);
            }
            
        } catch (IOException e) {
            log.error("Can't load property file, exiting " + e.getMessage());
            System.exit(1); // exit with error status
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
