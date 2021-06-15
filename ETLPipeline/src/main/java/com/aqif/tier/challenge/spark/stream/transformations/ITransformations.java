package com.aqif.tier.challenge.spark.stream.transformations;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface ITransformations {
    Dataset<Row> kafkaToDf(Dataset<Row> df);
    Dataset<Row> filterInvalidInputs(Dataset<Row> df);
    Dataset<Row> transformColumns(Dataset<Row> df);
    Dataset<Row> dropColumns(Dataset<Row> df);
}
