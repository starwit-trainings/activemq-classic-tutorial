## Camel Example Spring Boot

This example shows how to work with a simple Apache Camel application using Spring Boot.

The example generates messages using timer trigger, writes them to standard output.

### Camel routes

The Camel route is located in the `MyCamelRouter` class. In this class the route
starts from a timer, that triggers every 2nd second and calls a Spring Bean `MyBean`
which returns a message, that is routed to a stream endpoint which writes to standard output.

### Using Camel components

Apache Camel provides 200+ components which you can use to integrate and route messages between many systems
and data formats. To use any of these Camel components, add the component as a dependency to your project.

### How to run

You can run this example using

    mvn spring-boot:run

### To get health check

To show a summary of spring boot health check

```bash
curl -XGET -s http://localhost:8080/actuator/health
```

And you can see some info details as well

```bash
curl -XGET -s http://localhost:8080/actuator/info
```

See the `application.properties` to control what information to present in actuator.

### Camel Developer Console

This application includes `camel-console-starter` which is a basic developer console
that is accessible as a Spring Boot actuator endpoint via:

```bash
curl -XGET -s http://localhost:8080/actuator/camel
```

For example to get route information:

```bash
curl -XGET -s http://localhost:8080/actuator/camel/routes
```

### Camel CLI

This application is integrated with the Camel CLI via the `camel-cli-connector-starter` dependency (see `pom.xml`).
This allows to use the Camel CLI to manage this application, such as:

    $mvn spring-boot:run

And then use the CLI to see status:

    $camel get
      PID   NAME                          CAMEL   PLATFORM            READY  STATUS   AGE  TOTAL  FAILED  INFLIGHT  SINCE-LAST
     86000  sample.camel.MyCamelApplica…  3.19.0  Spring Boot v2.7.3   1/1   Running  11s      5       0         0          0s

### Help and contributions

If you hit any problem using Camel or have some feedback, then please
<https://camel.apache.org/support.html[let> us know].

We also love contributors, so
<https://camel.apache.org/contributing.html[get> involved] :-)

The Camel riders!
