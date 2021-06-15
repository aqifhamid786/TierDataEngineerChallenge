package com.aqif.tier.challenge.spark.stream.sinks;

import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JDBCStreamSink implements IStreamSink {

    String jdbcConnectorUir;
    String jdbcUsername;
    String jdbcPassword;
    String jdbcTable;

    SaveMode saveMode;

    public JDBCStreamSink(String connectorUri, String username, String password, String table, SaveMode saveMode) {
        this.jdbcConnectorUir = connectorUri;
        this.jdbcUsername = username;
        this.jdbcPassword = password;
        this.jdbcTable = table;
        this.saveMode = saveMode;
    }

    public void sink(Dataset<Row> df) throws TimeoutException, StreamingQueryException {
        StreamingQuery streamingQuery = df
                .writeStream()
                .trigger(Trigger.ProcessingTime(1, TimeUnit.SECONDS))
                .foreachBatch((VoidFunction2<Dataset<Row>, Long>) (rowDataset, aLong) ->
                        rowDataset.write().format("jdbc")
                        .option("url", jdbcConnectorUir)
                        .option("driver","com.mysql.cj.jdbc.Driver")
                        .option("user", jdbcUsername)
                        .option("password", jdbcPassword)
                        .option("dbtable", jdbcTable)
                        .mode(saveMode)
                        .save())
                .start();
        streamingQuery.awaitTermination();
    }

}
