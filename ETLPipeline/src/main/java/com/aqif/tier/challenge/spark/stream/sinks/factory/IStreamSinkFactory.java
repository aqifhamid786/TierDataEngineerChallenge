package com.aqif.tier.challenge.spark.stream.sinks.factory;

import com.aqif.tier.challenge.spark.stream.sinks.IStreamSink;

public interface IStreamSinkFactory {

    IStreamSink getStreamSink(StreamSinkType sinkType);
}
