package com.aqif.tier.challenge.spark.stream.sinks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.concurrent.TimeoutException;

public interface IStreamSink {
    void sink(Dataset<Row> df) throws TimeoutException, StreamingQueryException;
}
