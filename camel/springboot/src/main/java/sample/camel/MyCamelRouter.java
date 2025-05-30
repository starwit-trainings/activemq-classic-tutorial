/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints
 * to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto-detect this route when starting.
 */
@Component
public class MyCamelRouter extends RouteBuilder {

    // we can use spring dependency injection
    @Autowired
    MyBean myBean;

    @Override
    public void configure() throws Exception {
        // start from a timer
        from("timer:hello?period={{myPeriod}}").routeId("hello")
                // and call the bean
                .bean(myBean, "saySomething")
                // and print it to system out via stream component
                .to("stream:out");

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
