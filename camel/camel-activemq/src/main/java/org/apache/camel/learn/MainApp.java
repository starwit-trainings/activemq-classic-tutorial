package org.apache.camel.learn;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.jms.ConnectionFactory;

public class MainApp {

    private static final Logger log = LogManager.getLogger(MainApp.class);

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {

        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.addRoutes(new MyRouteBuilder());
        context.addRoutes(new ReceiveRoute());

        // Register shutdown hook for graceful stop
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("Shutting down Camel context...");
                context.stop();
                context.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));        

        context.start();

        log.info("Camel context started. Press Ctrl+C to exit.");

        // Block the main thread until interrupted
        Thread.currentThread().join();
    }

}

