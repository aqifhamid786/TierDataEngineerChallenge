# !/bin/sh
echo "Building project jar..."
# mvn dependency:resolve
# mvn verify
# mvn clean package
# cp ./ETLPipeline/target/ETLPipeline-1.0-SNAPSHOT.jar ./volumes/spark_app/ETLPipeline-1.0-SNAPSHOT.jar
# cp ./KafkaProducer/target/KafkaProducer-1.0-SNAPSHOT-jar-with-dependencies.jar ./volumes/spark_app/KafkaProducer-1.0-SNAPSHOT-jar-with-dependencies.jar
echo "Launching services..."
docker-compose -f docker-compose.yml up -d --build