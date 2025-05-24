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
                    String enriched = enrichStudentResultXml(body);
                    exchange.getIn().setBody(enriched);
                })
                .log("Received new message and sent to queue")
                .to("activemq:queue:broker-test");
    }

    private static String enrichStudentResultXml(String xml) {
        String timestamp = Instant.now().toString();
        return xml.replace("</studentresult>", "<timestamp>" + timestamp + "</timestamp></studentresult>");
    }
}
