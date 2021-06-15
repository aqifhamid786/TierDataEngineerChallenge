package com.aqif.tier.challenge.spark.stream.transformations;


import com.aqif.tier.challenge.spark.session.SparkSessionFactory;
import com.aqif.tier.challenge.spark.session.SparkSessionType;
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

public class WeatherTransformationsTest {

    private static WeatherTransformations weatherTransformation;
    private static SparkSession spark;

    private static ArrayList<byte[]> messages;
    private static MemoryStream<byte[]> memoryStream;

    @BeforeAll
    public static void initializeSession() {
        spark = new SparkSessionFactory().getOrCreateSparkSession(SparkSessionType.TestingSession);
        memoryStream = new MemoryStream<>(0, spark.sqlContext(), Option.empty(), Encoders.BINARY());
        weatherTransformation = new WeatherTransformations();

        messages = new ArrayList<>();
        try {
            messages.add("{\"weather_data.date_time\":null,\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:05:13\",\"weather_data.city\":null,\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:06:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":null,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:07:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":null,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:08:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":null,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:09:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":null,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:11:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":null,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:12:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":null,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:13:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":null},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:14:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":NaN,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":\"rain\",\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
            messages.add("{\"weather_data.date_time\":\"2019-06-04 14:10:13\",\"weather_data.city\":\"Zurich\",\"weather_data.currently_apparenttemperature\":29.09,\"weather_data.currently_humidity\":0.43,\"weather_data.currently_precipintensity\":0.0305,\"weather_data.currently_precipprobability\":0.01,\"weather_data.currently_preciptype\":null,\"weather_data.currently_temperature\":29.09,\"weather_data.currently_visibility\":10.12,\"weather_data.currently_windspeed\":1.94},".getBytes("UTF-8"));
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
        weatherTransformation = null;
    }

    @Test
    public void verifyExtractJsonValue() throws TimeoutException {
        Dataset<Row> df = weatherTransformation.extractJsonValue(memoryStream.toDF());

        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        List<Row> rows = spark.sql("Select * from weather").collectAsList();
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
        Dataset<Row> df = weatherTransformation.extractJsonValue(memoryStream.toDF());
        df = weatherTransformation.removeDbPrefix(df);
        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        List<Row> rows = spark.sql("Select * from weather").collectAsList();
        System.out.println(rows.size());
        System.out.println(messages.size());
        assert (rows.size() == messages.size());
        for(int rowIdx=0; rowIdx<rows.size(); rowIdx++) {
            String row = rows.get(rowIdx).getString(0);
            String message = new String(messages.get(rowIdx), Charset.forName("UTF-8")).replace("weather_data.", "");
            assert(row.equals(message));
        }
        streamingQuery.stop();
    }

    @Test
    public void verifyJsonToDf() throws TimeoutException {
        Dataset<Row> df = weatherTransformation.extractJsonValue(memoryStream.toDF());
        df = weatherTransformation.removeDbPrefix(df);
        df = weatherTransformation.jsonToDf(df);
        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from weather");
        assert (results.collectAsList().size() == messages.size());
        assert(results.schema().equals(Weather.df_schema));
        streamingQuery.stop();

    }


    @Test
    public void verifyKafkaToDf() throws TimeoutException {
        Dataset<Row> df = weatherTransformation.kafkaToDf(memoryStream.toDF());
        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from weather");
        assert (results.collectAsList().size() == messages.size());
        assert(results.schema().equals(Weather.df_schema));
        streamingQuery.stop();
    }

    @Test
    public void verifyFilterInvalidInputs() throws TimeoutException {
        Dataset<Row> df = weatherTransformation.kafkaToDf(memoryStream.toDF());
        df = weatherTransformation.filterInvalidInputs(df);

        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from weather");
        assert(results.count()==1); // only one record is valid.
        assert(results.collectAsList().get(0).json().contains("2019-06-04 14:10:13")); // The record should have following timestamp.
        streamingQuery.stop();
    }

    @Test
    public void verifyTransformColumns() throws TimeoutException {
        Dataset<Row> df = weatherTransformation.kafkaToDf(memoryStream.toDF());
        df = weatherTransformation.transformColumns(df);

        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from weather");
        List<Row> rows = results.collectAsList();
        for(int rowIdx=0; rowIdx<rows.size(); rowIdx++) {
            Row row = rows.get(rowIdx);
            String precipType = row.getString(row.fieldIndex(Weather.COL_CURRENT_PRECIP_TYPE));
            boolean isRaining = row.getBoolean(row.fieldIndex(Weather.COL_CURRENT_IS_RAINING));
            assert((precipType==null && !isRaining) || (precipType!=null && isRaining));
        }
        streamingQuery.stop();
    }

    @Test
    public void verifyDropColumns() throws TimeoutException {
        Dataset<Row> df = weatherTransformation.kafkaToDf(memoryStream.toDF());
        df = weatherTransformation.dropColumns(df);

        StreamingQuery streamingQuery = df.writeStream()
                .outputMode(OutputMode.Append())
                .format("memory")
                .queryName("weather")
                .start();
        streamingQuery.processAllAvailable();

        Dataset<Row> results = spark.sql("Select * from weather");
        assert(results.schema().toString().contains(Weather.COL_CURRENT_PRECIP_TYPE)==false);
        streamingQuery.stop();
    }

}
