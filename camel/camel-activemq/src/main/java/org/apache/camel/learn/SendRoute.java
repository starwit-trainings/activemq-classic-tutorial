package org.apache.camel.learn;

import org.apache.camel.builder.RouteBuilder;

public class SendRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("file:sampledata?noop=true&include=.*\\.xml")
                .routeId("sendToActiveMQ")
                .convertBodyTo(String.class)
                .to("activemq:queue:student-queue")
                .log("Sent to ActiveMQ: ${file:name}");
    }
}
