FROM openjdk:latest

ENV PRODUCER_MAIN_CLASS=com.aqif.tier.challenge.kafka.KafkaProducer
ENV PRODUCER_JAR=KafkaProducer-1.0-SNAPSHOT-jar-with-dependencies.jar

COPY ./target/$PRODUCER_JAR /
COPY ./cluster_kafka_producer.sh /

CMD ["sh", "/cluster_kafka_producer.sh"]