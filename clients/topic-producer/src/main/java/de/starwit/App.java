package de.starwit;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
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
        String topicName = config.getProperty("client.target");

        factory = new ActiveMQConnectionFactory(user, pw, url);
        factory.setClientID("sample-producer-" + factory.toString());

        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(session.createTopic(topicName));

            for (int i = 0; i < 500; i++) {
                TextMessage msg = session.createTextMessage("Test " + i);
                producer.send(msg);
                System.out.println("Message send " + i + " " + msg);
                Thread.sleep(1000);
            }
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("JMS error - exit " + e.getMessage());
        } catch (InterruptedException e) {
            log.error("Thread problem " + e.getMessage());
        }
    }

    private static void loadProperties() {
        try (InputStream in = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
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
}
