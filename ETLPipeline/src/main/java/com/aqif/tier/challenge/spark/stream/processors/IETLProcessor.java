package com.aqif.tier.challenge.spark.stream.processors;

import com.aqif.tier.challenge.spark.stream.sinks.factory.StreamSinkType;
import com.aqif.tier.challenge.spark.stream.sources.factory.StreamSourceType;
import com.aqif.tier.challenge.spark.stream.transformations.factory.TransformationsType;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.concurrent.TimeoutException;

public interface IETLProcessor {
    Dataset<Row> extract(StreamSourceType sourceType);
    Dataset<Row> transform(Dataset<Row> df, TransformationsType transformationsType);
    void load(Dataset<Row> df, StreamSinkType sinkType) throws TimeoutException, StreamingQueryException;
}
