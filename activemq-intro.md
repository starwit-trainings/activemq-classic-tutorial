# Introduction to ActiveMQ Administration
This document shall provide a brief introduction to the management of ActiveMQ. Please refer to the project's documentation for more details.

## Admin Console
ActiveMQ ships with an administration console that offers information and allows to send test messages. This section very quickly introduces most important parts.

![Admin Console](img/admin_console_queues.png)
![Admin Console](img/admin_console_topics.png)
![Admin Console](img/admin_console_send.png)
![Admin Console](img/admin_console_subscribers.png)
![Admin Console](img/admin_console_network.png)
![Admin Console](img/admin_console_network.png)

### Tasks
* Create a queue _sample-queue_ and send a message
* Use simple-listener to receive message
* Create a topic _sample-topi_ and send a message
* Use simple-listener to receive message

## Jolokia API

```bash
curl -XGET -u admin:admin -H "Origin:http://localhost"  http://localhost:8161/api/jolokia/list
```

## Replication

## Security

## Persistence

## Instrument ActiveMQ

As a Java application ActiveMQ can be instrumented and monitored using many standard tools. In this section, we will look into some of them. If you are interested in more sophisticated monitoring solutions, have a look into [Open Telemetry example](https://github.com/starwit-trainings/otel-demo).

### Java Mission Control
ActiveMQ makes use of Java's JMX interface and thus any JMX tool can be used, to observe a running broker. One of those tools is Java Mission Control. If you run in on the same machine as your broker instance, you can connect it right away. 

Once connected, you can browse all available MBeans (JMX' way to expose data and functions). See the following picture for an example:

![JMC](img/JMC-add-queue.png)

### HawtIO