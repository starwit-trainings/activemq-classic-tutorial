# Jolokia Client
ActiveMQ has a powerful API, that allows manipulation of almost everything everything. This example will show:
* how to list components
* how to send messages to a queue

## How to run
```bash
mvn clean package
java -jar target/jolokia-client.jar
```

Configure broker settings in [application.properties](src/main/resources/application.properties). 
