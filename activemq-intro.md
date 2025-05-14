# Introduction to ActiveMQ Administration
This document shall provide a brief introduction to the management of ActiveMQ.

## Admin Console
ActiveMQ ships with an administration console that offers information and allows to send test messages. 


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