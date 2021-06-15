package com.aqif.tier.challenge.spark.stream.sources.factory;

import com.aqif.tier.challenge.spark.Constants;
import com.aqif.tier.challenge.spark.stream.sources.IStreamSource;
import com.aqif.tier.challenge.spark.stream.sources.KafkaStreamSource;
import com.aqif.tier.challenge.spark.tables.SegmentTrack;
import com.aqif.tier.challenge.spark.tables.Weather;

public class StreamSourceFactory implements IStreamSourceFactory{

    public IStreamSource getStreamSource(StreamSourceType streamSourceType) {

        IStreamSource streamSource = null;
        switch (streamSourceType) {
            case KafkaWeatherStreamSource:
                streamSource = new KafkaStreamSource(Constants.KAFKA_URI, Weather.KAFKA_TOPIC);
                break;
            case KafkaSegmentStreamSource:
                streamSource = new KafkaStreamSource(Constants.KAFKA_URI, SegmentTrack.KAFKA_TOPIC);
                break;
        }
        return streamSource;
    }

}
