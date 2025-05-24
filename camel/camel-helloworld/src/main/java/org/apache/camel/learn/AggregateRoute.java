package org.apache.camel.learn;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.builder.RouteBuilder;

public class AggregateRoute extends RouteBuilder {
    private static final String AGG_FILE = "target/studentresults.xml";

    public void configure() throws Exception {

        from("timer:init?repeatCount=1")
                .process(e -> {
                    Files.write(Paths.get(AGG_FILE),
                            "<studentresults>\n".getBytes(), StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING);
                });

        // Create a counter bean in the registry
        getContext().getRegistry().bind("fileCounter", new AtomicInteger(0));

        from("file:sampledata?noop=true&include=.*\\.xml&delay=1000")
                .routeId("fileAppender")
                .convertBodyTo(String.class)
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    body = body.replaceFirst("<\\?xml[^>]+\\?>", "").trim();
                    Files.write(Paths.get(AGG_FILE),
                            (body + "\n").getBytes(), StandardOpenOption.APPEND);
                })
                .process(exchange -> {
                    AtomicInteger counter = exchange.getContext().getRegistry().lookupByNameAndType("fileCounter",
                            AtomicInteger.class);
                    int current = counter.incrementAndGet();

                    if (current >= 3) {
                        // Close current file
                        Files.write(Paths.get(AGG_FILE),
                                "</studentresults>\n".getBytes(), StandardOpenOption.APPEND);

                        // Move to archive
                        String newName = "target/studentresults-" + System.currentTimeMillis() + ".xml";
                        Files.move(Paths.get(AGG_FILE), Paths.get(newName));

                        // Start new file
                        Files.write(Paths.get(AGG_FILE),
                                "<studentresults>\n".getBytes(), StandardOpenOption.CREATE);

                        // Reset counter
                        counter.set(0);
                    }
                });
    }
}
