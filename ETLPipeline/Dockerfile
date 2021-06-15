FROM bitnami/spark:3.1.1

ENV SPARK_VERSION=3.1.1

ENV SPARK_MASTER_URL=spark://spark-master:7077
ENV SPARK_JOB_MAX_MEMORY=512M
ENV SPARK_JOB_MAX_CORES=2

# ENV ETL_PIPELINE=WEATHER
ENV SPARK_JOB_DEPENDENCIES=org.apache.spark:spark-sql-kafka-0-10_2.12:3.1.1,mysql:mysql-connector-java:8.0.25
ENV SPARK_JOB_MAIN_CLASS=com.aqif.tier.challenge.spark.TierETLJobRunner
ENV SPARK_JOB_JAR=ETLPipeline-1.0-SNAPSHOT.jar

RUN mkdir -p /opt/bitnami/app/builds
COPY ./target/$SPARK_JOB_JAR /opt/bitnami/app/builds
COPY ./start_etl_pipeline.sh /opt/bitnami/app/builds

CMD ["sh", "/opt/bitnami/app/builds/start_etl_pipeline.sh"] 