# ActiveMQ Classic Tutorial

This repository contains examples how to setup ActiveMQ Classic and how to send/receive messages using java. Please note, that code in this repo does not work with ActiveMQ Artemis. See [Artemis](https://github.com/starwit-trainings/apachemq-introduction) repo for according examples.

## Setup
You need to download and unzip ActiveMQ for your operating system. Find fitting package here: https://activemq.apache.org/components/classic/download/

All examples in this repo expect, that ActiveMQ is unzipped next to repo main repository. Resulting folder structure should look like so: 

```bash
.
..
activemq-classic-tutorial
apache-activemq-6.1.6
```

# Examples
## ActiveMQ Setup

### Simple Broker

### Replica Setup
In this example two broker instances will be started and configured as each other's replicas. 

__Windows__

Open two command prompts and start each instance like so:

```powershell
cd replica01\bin
replica01.bat start
```
If you started both instances, you should see log output like follows:
![Replica Setup](img/setup_replica.png)

## Java examples