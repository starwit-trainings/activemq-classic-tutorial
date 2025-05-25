# Spring Boot Examples
Messaging rarely happens in a vacuum and thus embedding communication into other frameworks is an every day task. This section introduces how Spring Boot applications can connect to ActiveMQ. Spring Boot is a popular and powerful framework to build business applications.

For all examples obviously a running broker is necessary. See section [simple](activemq-examples.md#simple-broker---explore-admin-console) how to start a simple broker.

## Rest Converter
Forwarding REST calls to a message queue.

```bash
cd springboot/rest-sender
mvn clean package
java -jar target/rest-sender-0.0.1-SNAPSHOT.jar 
```

Once application runs, swagger-ui can be accessed at http://localhost:8080. 

### Tasks
* Run app
* Connect [simple listener](java-examples.md#simple-listenerproducer)
* Send some messages and observe output
* Implement rest call, that takes destination (queue/topic) as input
* See [JSON example](java-examples.md#message-content-send-json) - build a REST service, that accepts studentresults object and forwards JSON to ActiveMQ

## Scheduled Sender
Send heartbeat message to a message queue.