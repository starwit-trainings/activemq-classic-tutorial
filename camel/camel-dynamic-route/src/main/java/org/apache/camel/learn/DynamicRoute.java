package org.apache.camel.learn;

import org.apache.camel.builder.RouteBuilder;

public class DynamicRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("activemq:queue:inbox")
                .routeId("schoolIdDynamicRouter")
                .log("Received message: ${body}")
                // optional: convert from JSON to a Map or POJO
                .unmarshal().json() // converts to Map by default

                // Use toD() for dynamic routing
                .toD("activemq:queue:${body[schoolId]}")
                .log("Routed to queue: ${body[schoolId]}");
    }

}
