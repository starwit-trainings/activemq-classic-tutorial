# Java Examples
This section covers how to connect to ActiveMQ using the Java programming language.

## Simple Listener/Producer

## Using Jolokia API
ActiveMQ ships with a powerful API called Jolokia. With that API almost everything in a running broker instance can be manipulated. This example shows how to connect to this API and some usage examples.

Start [hello world](#hello-world) example omitting producer client. Then execute following commands:
```bash
cd clients/jolokia-client
mvn clean package
java -jar target/jolokia-client.jar
```

## Using HTTP as transport
This example shows you, how to send messages using HTTP(s) as transport protocol- __NOTE__: There are many _much better_ ways to achieve this. See sections [Jolokia API](#using-jolokia-api) or [Camel](#camel-examples) for better approaches.

Start [hello world](#hello-world) example omitting producer client. Then execute following commands:
```bash
cd clients/http-producer
mvn clean package
java -jar target/http-producer.jar
```

This example sends messages via a http tunnel. Next to the drawbacks of this protocol, note, that some pretty outdated libraries are necessary to run this example.