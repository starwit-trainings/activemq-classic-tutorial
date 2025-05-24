# Camel Introduction
**Apache Camel** is an open-source integration framework to implement integration patterns.

## Key Features

- **Enterprise Integration Patterns (EIP)**: Built-in support for over 70 EIPs such as routing, transformation, filtering, and more.
- **DSL Support**: Routes can be defined using Java, XML, Kotlin, or YAML DSLs.
- **Component-Based Architecture**: Includes 300+ components to connect with databases, queues (e.g., JMS), APIs (REST/SOAP), file systems, cloud platforms, and more.
- **Lightweight and Embeddable**: Easily embedded into any Java application, Spring Boot app, or containerized service.
- **Integration with Frameworks**: Works well with Spring, CDI, Quarkus, and others.

## Core Concepts

- **Route**: A processing path that defines how messages flow between endpoints.
- **Endpoint**: The source or destination of a message (e.g., a file, queue, or HTTP endpoint).
- **Processor**: A custom piece of logic that can be executed within a route.
- **Exchange**: The message container that travels through routes, carrying both request and response data.

## Further Resources

Project Docu: https://camel.apache.org/camel-core/working-with-camel-core/index.html

Integration Patterns: 
https://camel.apache.org/components/4.10.x/eips/enterprise-integration-patterns.html

# Examples

## Hello World
A simple Java app, that runs an instance of Camel router. It watches for new files in folder datasets and process all XML files found.

```bash
cd camel/camel-helloworld
mvn clean package
java -jar target/camel-helloworld.jar
```

### Tasks
* Run app
* While running, copy one of the sample data files and observe what happens.
* Let's collect data about all participating schools
 * Create a number of XML files that contains info like school name and address
 * Create a new route, that is reading these files and add them to a list of all schools
* __Bonus Challenge:__ Rebuild example using JSON files.

## Camel and ActiveMQ
Camel framework has connectors for a huge number of messaging systems. In this example Camel connects to ActiveMQ and sends/receives messages. Before running this example, run [single broker instance](activemq-examples.md#simple-broker---explore-admin-console).

```bash
cd camel/camel-activemq
mvn clean package
java -jar target/camel-activemq.jar
```

### Tasks
* Run app
* Add a second processing step to receiver and write content to file.
* Add field schoolId to the [example snippets](camel/camel-activemq/sampledata/). Write a Java function, that gets details like school name, street name, ... and adds it to forwarded message.
* Switch messaging to topics and connect [simple listener](java-examples.md#simple-listenerproducer)

## Camel and Spring Boot
Camel can also used in conjunction with the Spring Boot framework. In this example Camel is started and a Spring bean is invoked for a fixed period. XML file sorting from hello world example is also executed.

### Tasks
* Run app
* Make file folder configurable via application.properties
* Add files to sample data folder and observe output
* __Bonus Challenge:__ Read all XML snippets into a datastructure in memory.

## Camel, Spring Boot & ActiveMQ