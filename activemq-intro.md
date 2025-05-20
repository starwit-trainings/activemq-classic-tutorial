# Introduction to ActiveMQ Administration
This document shall provide a brief introduction to the management of ActiveMQ. Please refer to the project's documentation for more details.

Topics
- Admin Console
- Management API / Jolokia
- Client connection options
- Replication
- Master/Slave
- Persistence
- Security
- Instrumentation

## Admin Console
ActiveMQ ships with an administration console that offers information and allows to send test messages. This section very quickly introduces most important parts. Compared to other message systems, ActiveMQ admin console offers only basic functions. This section will introduce most important dialogues.

Please note, that admin console is a Java webapplication that is hosted by the integrated Jetty app server. That means every broker can run its own console.

### List of Queues
![Admin Console](img/admin_console_queues.png)
### List of Topics
![Admin Console](img/admin_console_topics.png)
### Send Test Messages
![Admin Console](img/admin_console_send.png)
### List connected clients
![Admin Console](img/admin_console_subscribers.png)
### Broker Protocols - which connections offers your instance
![Admin Console](img/admin_console_network.png)
### Broker Network - all running & connected instances
![Admin Console](img/admin_console_replicas.png)

### Tasks
* Create a queue _sample-queue_ and send a message
* Use simple-listener to receive message
* Create a topic _sample-topic_ and send a message
* Use simple-listener to receive message
* Connect simple listener/producer and observe results in Admin console

## Jolokia API
Jolokia is a management API, with which all configuration and functions of ActiveMQ can be observed and manipulated. In fact admin console is build using this API.

In order to run the following examples, a running broker instance is necessary. See [Hello world example](Readme.md#hello-world) how to run an instance. If you use another instance responses may vary.

First example returns a list of everything, that is accessible via Jolokia.
```bash
curl -XGET -u admin:admin -H "Origin:http://localhost"  http://localhost:8161/api/jolokia/list
```
Note that it is necessary to set origin header. Without it ActiveMQ will refuse to answer - see section [security](#security).

Next example lists all available destinations. Note that broker name needs to be set according to your instance.
```bash
curl -XGET -u admin:admin -H "Origin:http://localhost" "http://localhost:8161/api/jolokia/search/org.apache.activemq:type=Broker,brokerName=single,destinationType=Queue,destinationName=*"
```
Response will look like this. Note, if no queue exists yet, response will be empty.
```JSON
{
  "request": {
    "mbean": "org.apache.activemq:brokerName=single,destinationName=*,destinationType=Queue,type=Broker",
    "type": "search"
  },
  "value": [
    "org.apache.activemq:brokerName=single,destinationName=test01,destinationType=Queue,type=Broker",
    "org.apache.activemq:brokerName=single,destinationName=test,destinationType=Queue,type=Broker"
  ],
  "status": 200
}
```

The following two example query an MBean. The first one collects all attributes if broker object.

```bash
curl -XGET -u admin:admin -H "Origin:http://localhost" "http://localhost:8161/api/jolokia/read/org.apache.activemq:type=Broker,brokerName=single/*"
```

This request will result in a response like this.
```JSON
{
  "request": {
    "mbean": "org.apache.activemq:brokerName=single,type=Broker",
    "type": "read"
  },
  "value": {
    "StatisticsEnabled": true,
    "TemporaryQueueSubscribers": [],
    "TotalConnectionsCount": 1,
    "TotalMessageCount": 1,
    "TempPercentUsage": 0,
    "MemoryPercentUsage": 0,
    "TransportConnectors": {
      "openwire": "tcp://markus-workstation-starwit:61616?maximumConnections=1000&wireFormat.maxFrameSize=104857600",
      "http": "http://markus-workstation-starwit:8080?transport.maximumConnections=1000&transport.wireFormat.maxFrameSize=104857600&transport.keepAlive=true&transport.trace=true&transport.useKeepAlive=true&transport.connectionTimeout=30000",
      "ws": "ws://markus-workstation-starwit:61614?maximumConnections=1000&wireFormat.maxFrameSize=104857600"
    },
....
```

If you want to query a single field - very handy to build a simple monitoring script - the following example gets a single field.
```bash
curl -XGET -u admin:admin -H "Origin:http://localhost" "http://localhost:8161/api/jolokia/read/org.apache.activemq:type=Broker,brokerName=single/TotalMessageCount"
```

There is a lot more this API can be used for. For an overview of all Mbeans see documention here:
https://activemq.apache.org/components/classic/documentation/jmx
An other way to get a full list can be found in section [Java Mission Control](#java-mission-control).

### Tasks
* Add a queue on admin console and observe output of queue listing.
* Modify destination request to topics. Add topics on admin console and observe output.
* Add an additional protocol to single instance (see next section), restart broker and query broker object.

## Protocols

ActiveMQ offers a number of communication protocols. For each protocol a connector needs to be configured. Following configuration extract is from _activemq.xml_. It shows connectors for the [single](activemq-examples.md#simple-broker---explore-admin-console) instance.

```xml
        <transportConnectors>
            <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
            <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="http" uri="http://0.0.0.0:8080?transport.maximumConnections=1000&amp;transport.wireFormat.maxFrameSize=104857600&amp;transport.keepAlive=true&amp;transport.trace=true&amp;transport.useKeepAlive=true&amp;transport.connectionTimeout=30000" />            
        </transportConnectors>
```

For obvious reasons your instances should only offer protocols, that your clients need. So the example shows only a subset of the supported protocols. A complete list can be found here: https://activemq.apache.org/components/classic/documentation/protocols

### Tasks
* Select a protocol and add a connector to single instance. Start broker and check via admin console, that new connector is available.
* __Bonus challenge:__ Enable MQTT and send/receive messages using a MQTT messaging tool

## Client connection options
https://activemq.apache.org/components/classic/documentation/uri-protocols

## Replication
One of the core features of message brokers, is the possibility to run multiple instances. This way setups that are able to deal with with high loads become possible as well as minimizing outages via replication. This section is a (brief) introduction into this topic. 

__Please note__ that high-availability is very hard to achieve, as many, many more aspects than message broker config needs to be configured and set up properly. So if you aim at HA start by familiarizing yourself with the general concept. A good starting point is [Wikipedia](https://en.wikipedia.org/wiki/High_availability).

### Static

```xml
    <networkConnectors>
        <networkConnector uri="static:(tcp://localhost:61616)" />
    </networkConnectors>  
```

### Discovery/Multicast
```xml
    <networkConnectors>
      <networkConnector uri="multicast://default"/>
    </networkConnectors>

    <transportConnectors>
      <transportConnector uri="tcp://localhost:0" discoveryUri="multicast://default"/>
    </transportConnectors>
```

Reference: https://activemq.apache.org/components/classic/documentation/networks-of-brokers

### Tasks
* 

## Security
* CORS/CSRF
* Authentication

## Persistence

## Instrument ActiveMQ

As a Java application ActiveMQ can be instrumented and monitored using many standard tools. In this section, we will look into some of them. If you are interested in more sophisticated monitoring solutions, have a look into [Open Telemetry example](https://github.com/starwit-trainings/otel-demo).

### Java Mission Control
ActiveMQ makes use of Java's JMX interface and thus any JMX tool can be used, to observe a running broker. One of those tools is Java Mission Control. If you run in on the same machine as your broker instance, you can connect it right away. 

Once connected, you can browse all available MBeans (JMX' way to expose data and functions). See the following picture for an example:

![JMC](img/JMC-add-queue.png)

### HawtIO

### ActiveMQ, Prometheus, Grafana
Another way to get insights of a running ActiveMQ instance is by extracting data via a Java agent. 

Start instance _single-jmx_ like so:
<table>
<tr>
<td> Windows </td> <td> Linux </td>
</tr>
<tr>
<td> 

```powershell
cd single-jmx\bin
single.bat start
```

</td>
<td>

```bash
cd single-jmx/bin
./single start
```

</td>
</tr>
</table>

Metrics should now be available under http://localhost:7878