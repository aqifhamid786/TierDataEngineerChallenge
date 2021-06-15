package com.aqif.tier.challenge.spark.stream.sources.factory;

import com.aqif.tier.challenge.spark.stream.sources.IStreamSource;

public interface IStreamSourceFactory {

    IStreamSource getStreamSource(StreamSourceType streamSourceType);
}
