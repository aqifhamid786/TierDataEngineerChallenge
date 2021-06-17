# Tier DE Challenge.

This repository contains a simple end-to-end ETL pipeline to process weather and scooter tracking events data. 

## Default Setup

### Message Broker

We deploy only 1 Kafka and 1 zookeeper node.

We use Kafka as a Data Broker to reliably store data from multiple data streams, before it is processed by the Data Stream processor. It allows us to elegantly and reliably handle high velocity and high volume data streams. 

We create one topic for each of the data stream. In our case, we have 1 topic for the weather data stream and 1 topic for the segment tracks data stream. For simplicity, we configured Kafka to maintain only a single partition per topic.

##### Durability and Scalability

It is easy to scale up the current Kafka setup by adding more data broker nodes, creating multiple topic partitions, and partition replication across these nodes. It will allow us to quickly ingest high velocity and high volume data make it instantly available for processing by multiple data stream processing executors.  

### Stream processing cluster

We use Spark Structured Streaming to ingest events from Kafka topics and to define our ETL jobs. Our cluster is configured to deploy 1 master and 2 worker nodes. Where each worker node occupies a single core and 1 GB of Main Memory. Since we have two Kafka topics, we employ one core to process data from the single partition of the Kafka topic. Note that our setup is linearly, but the choice of tools and technologies allow us to massively scale it to handle large volume and high-velocity data.

##### Durability and Scalability
Together with multi-partitioned topics, we can add more cores to existing worker nodes for vertical scalability. Further, we can add more nodes for horizontal scalability. 

### Data Warehouse 

We deploy a single 1 MySQL master node to represent our Dataware house for the results generated by the ETL jobs. 

##### Durability and Scalability

We can configure a MySQL data nodes cluster for tables sharding (partitioning) and data replication for scalability and reliability.

### ETL Job Driver nodes and Kafka Data Producer node

We deploy two Spark Driver nodes, one to host the weather ETL job and another one to host the segment tracks ETL job. Further, we deploy a node to execute our Kafka data producer.  

## Code

### Choice of programming language

We define our ETL Jobs using Spark Java library and a Kafka data producer using simple java and Kafka's java connector.

### ETL Jobs Design

The codebase is modularized. We use the `ETLProcessor` class to express ETL pipelines. We use it to define two ETL jobs, one for each of the data sources. We supply concrete objects for `IStreamSource`, `IStreamSink`, and `ITransformations` to the `ETLProcessor` to define our stream source, sink, and the desired transformations that we will like to perform on the streaming events.

### Transformations

We kept our data transformations simple for now. We transform a JSON formatted input event into a data frame with appropriate data types. 

### Kafka Producer

We wrote a simple Kafka producer to read an event from both of the supplied JSON files and publishes them on Kafka every 50ms.

## Deployment 

### Pre-requisite

- Maven is required for compiling ETL Jobs and Kafka producer code.
- Docker-compose and docker environment to host containers of our services.
- MySQL Workbench to inspect the status of loaded data.
- Copy static JSON data files into the following resource folder,`./KafkaProducer/src/main/resources/`, before deployment

### Deployment script

Once you are in the root directory of the project, execute the following command to compile and deploy the code, and to launch the Docker Container services.

`cd start_app.sh`

### Recommended Local Hardware Setup

- 4 Core CPU
- Up to 4GB Main Memory
