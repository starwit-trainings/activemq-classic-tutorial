package org.apache.camel.learn;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    public void configure() throws Exception {

        from("file:sampledata?noop=true&include=.*\\.xml")
                .routeId("sendToActiveMQ")
                .convertBodyTo(String.class)
                .to("activemq:queue:broker-test")
                .log("Sent to ActiveMQ: ${file:name}");
    }

}
