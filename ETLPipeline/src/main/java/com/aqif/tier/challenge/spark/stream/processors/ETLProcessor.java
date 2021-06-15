package com.aqif.tier.challenge.spark.stream.processors;

import com.aqif.tier.challenge.spark.session.SparkSessionFactory;
import com.aqif.tier.challenge.spark.session.SparkSessionType;
import com.aqif.tier.challenge.spark.stream.sinks.IStreamSink;
import com.aqif.tier.challenge.spark.stream.sinks.factory.StreamSinkFactory;
import com.aqif.tier.challenge.spark.stream.sinks.factory.StreamSinkType;
import com.aqif.tier.challenge.spark.stream.sources.IStreamSource;
import com.aqif.tier.challenge.spark.stream.sources.factory.StreamSourceFactory;
import com.aqif.tier.challenge.spark.stream.sources.factory.StreamSourceType;
import com.aqif.tier.challenge.spark.stream.transformations.ITransformations;
import com.aqif.tier.challenge.spark.stream.transformations.factory.TransformationsFactory;
import com.aqif.tier.challenge.spark.stream.transformations.factory.TransformationsType;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.concurrent.TimeoutException;

public class ETLProcessor implements IETLProcessor{

    private SparkSession constructSession() {
        SparkSessionType sessionType = SparkSessionType.DevelopmentSession;
        if(System.getenv("SPARK_MASTER_URL")!=null) // For now it is only defined if we launch app in dockerized cluster mode.
            sessionType = SparkSessionType.ClusterSession;
        SparkSession sparkSession = new SparkSessionFactory().getOrCreateSparkSession(sessionType);
        return sparkSession;

    }

    @Override
    public Dataset<Row> extract(StreamSourceType sourceType) {
        SparkSession sparkSession = constructSession();
        IStreamSource streamSource = new StreamSourceFactory().getStreamSource(sourceType);
        Dataset<Row> df = streamSource.connect(sparkSession);
        return df;
    }

    @Override
    public Dataset<Row> transform(Dataset<Row> df, TransformationsType transformationsType) {
        ITransformations transformations = new TransformationsFactory().getTransformations(transformationsType);
        df = transformations.kafkaToDf(df);
        df = transformations.filterInvalidInputs(df);
        df = transformations.transformColumns(df);
        df = transformations.dropColumns(df);
        return df;
    }

    @Override
    public void load(Dataset<Row> df, StreamSinkType sinkType) throws TimeoutException, StreamingQueryException {
        IStreamSink streamSink = new StreamSinkFactory().getStreamSink(sinkType);
        streamSink.sink(df);
    }
}
