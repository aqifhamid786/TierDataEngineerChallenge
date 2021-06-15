package com.aqif.tier.challenge.spark.stream.transformations;

import com.aqif.tier.challenge.spark.tables.SegmentTrack;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;

public class SegmentTrackTransformations implements ITransformations{

    @Override
    public Dataset<Row> kafkaToDf(Dataset<Row> df) {
        df = extractJsonValue(df);
        df = removeDbPrefix(df);
        df = jsonToDf(df);
        return df;
    }

    public Dataset<Row> extractJsonValue(Dataset<Row> df) {
        df = df.selectExpr("CAST(value AS STRING)");
        return df;
    }

    public Dataset<Row> removeDbPrefix(Dataset<Row> df) {
        df = df.map((MapFunction<Row, String>) row -> row
                .getString(row.fieldIndex("value"))
                .replace("segment_tracks.", ""), Encoders.STRING())
                .toDF();
        return df;
    }

    public Dataset<Row> jsonToDf(Dataset<Row> df) {
        df = df.select(functions.from_json(df.col("value"), SegmentTrack.df_schema).as("data")).select("data.*");
        return df;
    }

    @Override
    public Dataset<Row> filterInvalidInputs(Dataset<Row> df) {
        return df;
    }

    @Override
    public Dataset<Row> transformColumns(Dataset<Row> df) {
        return df;
    }

    @Override
    public Dataset<Row> dropColumns(Dataset<Row> df) {
        return df;
    }

}
