package com.aqif.tier.challenge.spark.stream.transformations;

import com.aqif.tier.challenge.spark.tables.Weather;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;

public class WeatherTransformations implements ITransformations{


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
                .replace("weather_data.", ""), Encoders.STRING())
                .toDF();
        return df;
    }

    public Dataset<Row> jsonToDf(Dataset<Row> df) {
        df = df.select(functions.from_json(df.col("value"), Weather.df_schema).as("data")).select("data.*");
        return df;
    }

    @Override
    public Dataset<Row> filterInvalidInputs(Dataset<Row> df) {
        return df.na().drop(Weather.non_null_columns);
    }

    @Override
    public Dataset<Row> transformColumns(Dataset<Row> df) {
        return df.withColumn(Weather.COL_CURRENT_IS_RAINING, df.col(Weather.COL_CURRENT_PRECIP_TYPE).isNotNull());
    }

    @Override
    public Dataset<Row> dropColumns(Dataset<Row> df) {
        return df.drop(df.col(Weather.COL_CURRENT_PRECIP_TYPE));
    }

}
