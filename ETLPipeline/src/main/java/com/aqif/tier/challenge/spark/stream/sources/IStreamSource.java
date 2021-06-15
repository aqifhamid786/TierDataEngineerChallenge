package com.aqif.tier.challenge.spark.stream.sources;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface IStreamSource {
    Dataset<Row> connect(SparkSession sparkSession);
}
