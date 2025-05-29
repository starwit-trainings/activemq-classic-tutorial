package org.apache.camel.learn;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class ReceiveRoute extends RouteBuilder {

    public void configure() throws Exception {

        // Configure Jackson to parse a List<StudentResult>
        JacksonDataFormat studentListFormat = new JacksonDataFormat(
                StudentResult.class);
        studentListFormat.setCollectionType(List.class); // Tell it it's a List
        studentListFormat.setUseList(true); // Important for JSON arrays

        from("activemq:queue:student-queue")
                .routeId("receiveFromActiveMQ")
                .log("Received JSON array: ${body}")
                .unmarshal(studentListFormat)
                .split(body()) // Split into individual list elements
                    .marshal().json(JsonLibrary.Jackson) // Convert each item back to JSON
                    .convertBodyTo(String.class) //Text Message
                    .log("Received new student list and sent smaller package to queue")
                    .to("activemq:queue:inbox");
    }
}
