# Rest message sender
This project demonstrates how to use forward messages from REST services to a JMS queue.

## Topics
* REST service + Swagger UI
* Messaging with JMS
* Spring Boot scheduled tasks

## How to Run
```bash
mvn clean package
java -jar target/messaging-demo-0.0.1-SNAPSHOT.jar
``` 

## Tasks
* Send a heartbeat message every 20 seconds
* Create new REST service, that gets object as input and forward this object to queue
* Bonus challenge: build rest service, that returns number of messages in queue. Use Jolokia API.