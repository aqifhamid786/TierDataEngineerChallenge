package com.aqif.tier.challenge.spark.stream.transformations;


import com.aqif.tier.challenge.spark.session.SparkSessionFactory;
import com.aqif.tier.challenge.spark.session.SparkSessionType;
import com.aqif.tier.challenge.spark.tables.SegmentTrack;
import com.aqif.tier.challenge.spark.tables.Weather;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.streaming.MemoryStream;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scala.Option;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SegmentTrackTransformationsTest {

    private static SegmentTrackTransformations segmentTrackTransformations;
    private static SparkSession spark;

    private static ArrayList<byte[]> messages;
    private static MemoryStream<byte[]> memoryStream;

    @BeforeAll
    public static void initializeSession() {
        spark = new SparkSessionFactory().getOrCreateSparkSession(SparkSessionType.TestingSession);
        memoryStream = new MemoryStream<>(0, spark.sqlContext(), Option.empty(), Encoders.BINARY());
        segmentTrackTransformations = new SegmentTrackTransformations();

        messages = new ArrayList<>();
        try {
            messages.add("{\"segment_tracks.original_timestamp_time\":\"2019-05-13 14:23:41\",\"segment_tracks.event_name\":\"Signup Started\",\"segment_tracks.context_timezone\":\"Europe/Oslo\",\"segment_tracks.context_os_version\":\"12.2\",\"segment_tracks.context_os_name\":\"iOS\",\"segment_tracks.context_device_type\":\"ios\",\"segment_tracks.context_device_model\":\"iPhone11,6\",\"segment_tracks.context_device_manufacturer\":\"Apple\",\"segment_tracks.context_device_id\":\"11551203-2950-4157-B927-4488468B7086\",\"segment_tracks.context_app_version\":\"3.0.5\",\"segment_tracks.context_app_name\":\"Tier\",\"segment_tracks.context_app_build\":\"3.0.5.2\",\"segment_tracks.anonymous_id\":\"97311ADA-DF6E-4534-8741-01EF9CE8A1E6\",\"segment_tracks.received_time\":\"2019-05-13 14:23:46\",\"segment_tracks.sent_time\":\"2019-05-13 14:23:46\",\"segment_tracks.properties_rating\":null},".getBytes("UTF-8"));
            messages.add("{\"segment_tracks.original_timestamp_time\":\"2019-05-13 14:24:41\",\"segment_tracks.event_name\":\"Signup Started\",\"segment_tracks.context_timezone\":\"Europe/Oslo\",\"segment_tracks.context_os_version\":\"12.2\",\"segment_tracks.context_os_name\":\"iOS\",\"segment_tracks.context_device_type\":\"ios\",\"segment_tracks.context_device_model\":\"iPhone11,6\",\"segment_tracks.context_device_manufacturer\":\"Apple\",\"segment_tracks.context_device_id\":\"11551203-2950-4157-B927-4488468B7086\",\"segment_tracks.context_app_version\":\"3.0.5\",\"segment_tracks.context_app_name\":\"Tier\",\"segment_tracks.context_app_build\":\"3.0.5.2\",\"segment_tracks.anonymous_id\":\"97311ADA-DF6E-4534-8741-01EF9CE8A1E6\",\"segment_tracks.received_time\":\"2019-05-13 14:23:46\",\"segment_tracks.sent_time\":\"2019-05-13 14:23:46\",\"segment_tracks.properties_rating\":null},".getBytes("UTF-8"));
            messages.add("{\"segment_tracks.original_timestamp_time\":\"2019-05-13 14:25:41\",\"segment_tracks.event_name\":\"Signup Started\",\"segment_tracks.context_timezone\":\"Europe/Oslo\",\"segment_tracks.context_os_version\":\"12.2\",\"segment_tracks.context_os_name\":\"iOS\",\"segment_tracks.context_device_type\":\"ios\",\"segment_tracks.context_device_model\":\"iPhone11,6\",\"segment_tracks.context_device_manufacturer\":\"Apple\",\"segment_tracks.context_device_id\":\"11551203-2950-4157-B927-4488468B7086\",\"segment_tracks.context_app_version\":\"3.0.5\",\"segment_tracks.context_app_name\":\"Tier\",\"segment_tracks.context_app_build\":\"3.0.5.2\",\"segment_tracks.anonymous_id\":\"97311ADA-DF6E-4534-8741-01EF9CE8A1E6\",\"segment_tracks.received_time\":\"2019-05-13 14:23:46\",\"segment_tracks.sent_time\":\"2019-05-13 14:23:46\",\"segment_tracks.properties_rating\":null},".getBytes("UTF-8"));
            messages.add("{\"segment_tracks.original_timestamp_time\":\"2019-05-13 14:26:41\",\"segment_tracks.event_name\":\"Signup Started\",\"segment_tracks.context_timezone\":\"Europe/Oslo\",\"segment_tracks.context_os_version\":\"12.2\",\"segment_tracks.context_os_name\":\"iOS\",\"segment_tracks.context_device_type\":\"ios\",\"segment_tracks.context_device_model\":\"iPhone11,6\",\"segment_tracks.context_device_manufacturer\":\"Apple\",\"segment_tracks.context_device_id\":\"11551203-2950-4157-B927-4488468B7086\",\"segment_tracks.context_app_version\":\"3.0.5\",\"segment_tracks.context_app_name\":\"Tier\",\"segment_tracks.context_app_build\":\"3.0.5.2\",\"segment_tracks.anonymous_id\":\"97311ADA-DF6E-4534-8741-01EF9CE8A1E6\",\"segment_tracks.received_time\":\"2019-05-13 14:23:46\",\"segment_tracks.sent_time\":\"2019-05-13 14:23:46\",\"segment_tracks.properties_rating\":null},".getBytes("UTF-8"));
            messages.add("{\"segment_tracks.original_timestamp_time\":\"2019-05-13 14:27:41\",\"segment_tracks.event_name\":\"Signup Started\",\"segment_tracks.context_timezone\":\"Europe/Oslo\",\"segment_tracks.context_os_version\":\"12.2\",\"segment_tracks.context_os_name\":\"iOS\",\"segment_tracks.context_device_type\":\"ios\",\"segment_tracks.context_device_model\":\"iPhone11,6\",\"segment_tracks.context_device_manufacturer\":\"Apple\",\"segment_tracks.context_device_id\":\"11551203-2950-4157-B927-4488468B7086\",\"segment_tracks.context_app_version\":\"3.0.5\",\"segment_tracks.context_app_name\":\"Tier\",\"segment_tracks.context_app_build\":\"3.0.5.2\",\"segment_tracks.anonymous_id\":\"97311ADA-DF6E-4534-8741-01EF9CE8A1E6\",\"segment_tracks.received_time\":\"2019-05-13 14:23:46\",\"segment_tracks.sent_time\":\"2019-05-13 14:23:46\",\"segment_tracks.properties_rating\":null},".getBytes("UTF-8"));
            messages.add("{\"segment_tracks.original_timestamp_time\":\"2019-05-13 14:28:41\",\"segment_tracks.event_name\":\"Signup Started\",\"segment_tracks.context_timezone\":\"Europe/Oslo\",\"segment_tracks.context_os_version\":\"12.2\",\"segment_tracks.context_os_name\":\"iOS\",\"segment_tracks.context_device_type\":\"ios\",\"segment_tracks.context_device_model\":\"iPhone11,6\",\"segment_tracks.context_device_manufacturer\":\"Apple\",\"segment_tracks.context_device_id\":\"11551203-2950-4157-B927-4488468B7086\",\"segment_tracks.context_app_version\":\"3.0.5\",\"segment_tracks.context_app_name\":\"Tier\",\"segment_tracks.context_app_build\":\"3.0.5.2\",\"segment_tracks.anonymous_id\":\"97311ADA-DF6E-4534-8741-01EF9CE8A1E6\",\"segment_tracks.received_time\":\"2019-05-13 14:23:46\",\"segment_tracks.sent_time\":\"2019-05-13 14:23:46\",\"segment_tracks.properties_rating\":null},".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Seq<byte[]> set = JavaConverters.asScalaIteratorConverter(messages.iterator()).asScala().toSeq();
        memoryStream.addData(set);
    }

    @AfterAll
    public static void destroySession() {
        spark.close();
        memoryStream.stop();
        segmentTrackTransformations = null;
    }

    @Test
    public void verifyExtractJsonValue() throws TimeoutException {
        Dataset<Row> df = segmentTrackTransformations.extractJsonValue(memoryStream.toDF());

        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("SegmentTrack")
                .start();
        streamingQuery.processAllAvailable();

        List<Row> rows = spark.sql("Select * from SegmentTrack").collectAsList();
        assert (rows.size() == messages.size());
        for(int rowIdx=0; rowIdx<rows.size(); rowIdx++) {
            String row = rows.get(rowIdx).getString(0);
            String message = new String(messages.get(rowIdx), Charset.forName("UTF-8"));
            assert(row.equals(message));
        }
        streamingQuery.stop();
    }

    @Test
    public void verifyRemoveDbPrefix() throws TimeoutException {
        Dataset<Row> df = segmentTrackTransformations.extractJsonValue(memoryStream.toDF());
        df = segmentTrackTransformations.removeDbPrefix(df);
        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("SegmentTrack")
                .start();
        streamingQuery.processAllAvailable();

        List<Row> rows = spark.sql("Select * from SegmentTrack").collectAsList();
        System.out.println(rows.size());
        System.out.println(messages.size());
        assert (rows.size() == messages.size());
        for(int rowIdx=0; rowIdx<rows.size(); rowIdx++) {
            String row = rows.get(rowIdx).getString(0);
            String message = new String(messages.get(rowIdx), Charset.forName("UTF-8")).replace("segment_tracks.", "");
            assert(row.equals(message));
        }
        streamingQuery.stop();
    }

    @Test
    public void verifyJsonToDf() throws TimeoutException {
        Dataset<Row> df = segmentTrackTransformations.extractJsonValue(memoryStream.toDF());
        df = segmentTrackTransformations.removeDbPrefix(df);
        df = segmentTrackTransformations.jsonToDf(df);
        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("SegmentTrack")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from SegmentTrack");
        assert (results.collectAsList().size() == messages.size());
        assert(results.schema().equals(SegmentTrack.df_schema));
        streamingQuery.stop();

    }

    @Test
    public void verifyKafkaToDf() throws TimeoutException {
        Dataset<Row> df = segmentTrackTransformations.kafkaToDf(memoryStream.toDF());
        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("SegmentTrack")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from SegmentTrack");
        assert(results.collectAsList().size() == messages.size());
        assert(results.schema().equals(SegmentTrack.df_schema));
        streamingQuery.stop();
    }

    @Test
    public void verifyFilterInvalidInputs() throws TimeoutException {
    }

    @Test
    public void verifyTransformColumns() throws TimeoutException {
    }

    @Test
    public void verifyDropColumns() throws TimeoutException {
    }

}
