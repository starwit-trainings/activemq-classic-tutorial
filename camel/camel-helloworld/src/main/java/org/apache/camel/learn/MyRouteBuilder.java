package org.apache.camel.learn;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    public void configure() throws Exception {

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
        from("file:sampledata?noop=true")
            .choice()
                .when(xpath("/studentresult/grade = '1'"))
                    .log("Awesome result!")
                    .to("file:target/awesome")
                .otherwise()
                    .log("Other results")
                    .to("file:target/other");
    }

}
