# Tier DE Challenge.

This repository contains a simple end-to-end ETL pipeline to process weather and scooter tracking events data. 

## Default Setup

### Message Broker

We deploy only 1 kafka and 1 zookeeper node.

We use Kafka as a Data Broker to reliably hold data from multiple data streams before it is processed by the Data Stream processors. This allows us to elegantly and reliably handle high velocity and high volume data streams. 

We create one topic for each of the data stream. In our case, we have 1 topic for weather data stream and 1 topic for segment tracks data stream. For simplicity, kafka is configured to maintain only a single partition per topic.

##### Durability and Scalability

It is very easy to scale up current Kafka setup by adding more data broker nodes, creating multiple topic partitions, and partition replication across these nodes. This will allow us to quickly ingest high velocity and high volume data make it instantly available for processing by multiple data stream processing executors.  

### Stream processing cluster

We use Spark Structured Streaming to ingest events from Kafka topics and to define our ETL jobs. Our cluster is configured to deploy 1 master and 2 worker nodes. Where each worker node occupies a single core and 1 GiB of Main Memory. Since we have two kafka topics, we can employ each core in a node to process data from the single partition of a topic. Note that our setup is lineary, but the choice of tools and technologies allow us to massively scale it to handle large volume and high velocity data.

##### Durability and Scalability

Together with multi-partition topics, we can add more cores to existing worker nodes vertical scalability and more nodes for horizontal scalability. 

### Data Warehouse 

We deploy a single 1 MySQL master node to represent our dataware house for the results generted by the ETL jobs. 

##### Durability and Scalability

We can conigure a MySQL data nodes cluster for tables scharding (partitioning) and data replication for scalaibility and reliability.

### ETL Job Driver nodes and Kafka Data Producer node

We deploy two Spark Driver nodes, one to host weather ETL job and other one to host segment tracks ETL job. Further, we deploy a node to execute our Kafka data producer.  

## Code

### Choice of language

We define our ETL Jobs using Spark Java library and a Kafka data producer using simple java and kafkas' java connector.

### ETL Jobs Design

The code base is modularized. It allows us to easily degins `ETL Processors`, where each processor represents an ETL job. Specifically we design two ETL jobs, one for each of the data source in hand. The job reads events from Kafka broker, do some transformations to prepare them, and finally loads them in our data warehouse (MySQL).

### Transformations

We kept our data transformations simple for now. We transform a json formatted input event in to a data frame with appropriate data types. 

### Kafka Producer

We wrote a simple kafka producer, we reach an event from both of the supplied json files and publishes them on kafka every 100ms.

## Deployment 

### Deployment script

Make sure you are in the root directory of the project and then execute following command to compile and deploy code and launch our container services.

`cd start_app.sh`

### Pre-requisite

- Maven is required for compiling ETL Job and Kafka producer code.
- Docker compose and docker enviorment to host containers of our services.
- MySQL Workbench to inspect the status of loaded data.

### Recommeded Local Hardware Setup

- 4 Core CPU
- Upto 4gb Main Memory



