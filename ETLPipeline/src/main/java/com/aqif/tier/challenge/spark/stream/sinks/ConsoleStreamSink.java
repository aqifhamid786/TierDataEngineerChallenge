package com.aqif.tier.challenge.spark.stream.sinks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.concurrent.TimeoutException;


public class ConsoleStreamSink implements IStreamSink {

    @Override
    public void sink(Dataset<Row> df)  throws TimeoutException, StreamingQueryException {

        StreamingQuery streamingQuery = df
                .writeStream()
                .outputMode(OutputMode.Append())
                .format("console")
                .option("truncate", "false")
                .start();
        streamingQuery.awaitTermination();

    }
}
