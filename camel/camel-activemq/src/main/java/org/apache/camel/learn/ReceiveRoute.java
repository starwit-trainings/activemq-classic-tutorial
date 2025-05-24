package org.apache.camel.learn;

import java.time.Instant;

import org.apache.camel.builder.RouteBuilder;

public class ReceiveRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("activemq:queue:student-queue")
                .routeId("receiveFromActiveMQ")
                .convertBodyTo(String.class)
                .process(exchange -> {
                        String body = exchange.getIn().getBody(String.class);
                        String timestamp = Instant.now().toString();

                        // Insert timestamp before </studentresult>
                        String enriched = body.replace("</studentresult>",
                                "<timestamp>" + timestamp + "</timestamp></studentresult>");

                        exchange.getIn().setBody(enriched);
                    })
                .log("Received new message and sent to queue")
                .to("activemq:queue:broker-test");
    }

}
