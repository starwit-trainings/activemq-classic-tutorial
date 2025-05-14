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
In this section all examples in this repository are introduced. You can work with these examples in any order you like.

## ActiveMQ Setups
This list of examples deal with running and configuring ActiveMQ broker.

### Simple Broker
This example runs a single node ActiveMQ broker and serves as first introduction. 

__Windows__

Open a command prompt (not Powershell) and run the following commands

```powershell
cd single\bin
single.bat start
```

__Linux__
```bash
cd single\bin
./single start
```

Once broker is started, adminstration console can be accessed via http://localhost:8161/ using admin/admin as credentials.

#### Concepts

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