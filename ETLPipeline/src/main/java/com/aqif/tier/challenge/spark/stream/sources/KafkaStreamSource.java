package com.aqif.tier.challenge.spark.stream.sources;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class KafkaStreamSource implements IStreamSource {

    private String kafkaUri;
    private String kafkaTopics;

    public KafkaStreamSource(String uri, String topics) {
        this.kafkaUri = uri;
        this.kafkaTopics = topics;
    }

    @Override
    public Dataset<Row> connect(SparkSession sparkSession) {
        return sparkSession
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", kafkaUri)
                .option("subscribe", kafkaTopics)
                .load();

    }

}
