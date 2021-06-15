package com.aqif.tier.challenge.spark;

public interface Constants {
//    String JDBC_CONNECTOR_URI = "jdbc:mysql://localhost/tier_warehouse";
    String JDBC_CONNECTOR_URI = "jdbc:mysql://mysql:3306/tier_warehouse";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "root";

//    String KAFKA_URI = "localhost:9092";
    String KAFKA_URI = "kafka:9092";

    String SPARK_APP_NAME = "TierChallenge";
    String SPARK_LOG_LEVEL = "Error";
}

