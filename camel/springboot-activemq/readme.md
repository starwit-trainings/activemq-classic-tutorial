## Camel Example Spring Boot and ActiveMQ

This example shows how to work with a simple Apache Camel application using Spring Boot and Apache ActiveMQ.
In contrast to the amqp example in this repo, it uses ActiveMQs openwire protocol and the 
camel-activemq component.

### Preparing ActiveMQ brokers

From Apache ActiveMQ you can download the broker as a `.zip` or `.tar.gz` file.

Unzip/tar the archive, and start a terminal.

Change directory to the unzipped directory and start the broker.

    bin/activemq console

Which runs the broker in the foreground and logs to the console.

### How to run the example

You can run this example using

    mvn spring-boot:run

### Using Camel components

Apache Camel provides 200+ components which you can use to integrate and route messages between many systems
and data formats. To use any of these Camel components, add the component as a dependency to your project.

### Help and contributions

If you hit any problem using Camel or have some feedback, then please
https://camel.apache.org/support.html[let us know].

We also love contributors, so
https://camel.apache.org/contributing.html[get involved] :-)

The Camel riders!
