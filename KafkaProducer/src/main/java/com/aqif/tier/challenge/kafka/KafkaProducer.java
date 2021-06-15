package com.aqif.tier.challenge.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class KafkaProducer {

    public static void main(String[] args) {
        new KafkaProducer().Produce();
    }

    public void Produce() {

        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer(props);
        try {
            BufferedReader weatherReader = getDataReader("weather.json");
            BufferedReader trackEventReader = getDataReader("track_events.json");

            String weatherLine = weatherReader.readLine();
            String trackEventLine = trackEventReader.readLine();

            int recordId=0;
            while (weatherLine!=null || trackEventLine!=null) {

                if(weatherLine!=null) {
                    producer.send(new ProducerRecord<>("weather", Integer.toString(recordId), weatherLine), (recordMetadata, e) -> {
                        if(e!=null)
                            e.printStackTrace();

                    });
                    weatherLine = weatherReader.readLine();
                }

                if(trackEventLine!=null) {
                    producer.send(new ProducerRecord<>("track_events", Integer.toString(recordId+1), trackEventLine), (recordMetadata, e) -> {
                        if(e!=null)
                            e.printStackTrace();
                    });
                    trackEventLine = trackEventReader.readLine();
                }

                try {
                    Thread.currentThread().sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recordId+=2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    private BufferedReader getDataReader(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        return reader;
    }
}
