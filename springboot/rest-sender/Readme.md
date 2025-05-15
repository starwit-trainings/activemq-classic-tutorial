# Messaging Example
This project demonstrates how to use JMS messaging with Spring Boot. It starts an integrated Artemis instance and reveices from two queues.  

## Topics
* Messaging with JMS
* Sending/Receiving
* De/Serialize Java objects vs JSON
* Run integrated JMS server
* Receiving from multiple queues
* Spring Boot scheduled tasks

## How to Run

    mvn clean package
    java -jar target/messaging-demo-0.0.1-SNAPSHOT.jar 

## Exercises
* Save received users to database
* Connect to external Artemis instance & receive/send messages