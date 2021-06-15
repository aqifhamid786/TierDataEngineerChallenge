package com.aqif.tier.challenge.spark.session;


import org.apache.spark.sql.SparkSession;

public interface ISparkSessionFactory {
    SparkSession getOrCreateSparkSession(SparkSessionType sessionType);
}
