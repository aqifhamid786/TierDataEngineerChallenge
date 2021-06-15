package com.aqif.tier.challenge.spark.session;

import com.aqif.tier.challenge.spark.Constants;
import org.apache.spark.sql.SparkSession;

public class SparkSessionFactory implements ISparkSessionFactory {

    @Override
    public SparkSession getOrCreateSparkSession(SparkSessionType sessionType) {

        SparkSession spark = null;

        switch (sessionType) {
            case ClusterSession:
                spark = SparkSession
                        .builder()
                        .appName(Constants.SPARK_APP_NAME)
                        .getOrCreate();
                break;
            case DevelopmentSession:
            case TestingSession:
                spark = SparkSession
                        .builder()
                        .appName(Constants.SPARK_APP_NAME)
                        .master("local")
                        .getOrCreate();
                break;
        }
        spark.sparkContext().setLogLevel(Constants.SPARK_LOG_LEVEL);
        return spark;
    }

}
