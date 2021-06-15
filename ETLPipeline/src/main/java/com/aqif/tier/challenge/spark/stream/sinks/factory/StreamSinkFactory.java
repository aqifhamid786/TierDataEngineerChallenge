package com.aqif.tier.challenge.spark.stream.sinks.factory;

import com.aqif.tier.challenge.spark.Constants;
import com.aqif.tier.challenge.spark.stream.sinks.ConsoleStreamSink;
import com.aqif.tier.challenge.spark.stream.sinks.IStreamSink;
import com.aqif.tier.challenge.spark.stream.sinks.JDBCStreamSink;
import com.aqif.tier.challenge.spark.tables.SegmentTrack;
import com.aqif.tier.challenge.spark.tables.Weather;
import org.apache.spark.sql.SaveMode;

public class StreamSinkFactory implements IStreamSinkFactory {

    @Override
    public IStreamSink getStreamSink(StreamSinkType sinkType) {
        IStreamSink sink = null;
        switch (sinkType) {
            case ConsoleSink:
                sink = new ConsoleStreamSink();
                break;

            case JDBCWeatherSink:
                sink = new JDBCStreamSink(Constants.JDBC_CONNECTOR_URI, Constants.JDBC_USER, Constants.JDBC_PASSWORD, Weather.DB_TABLE, SaveMode.Append);
                break;

            case JDBCSegmentTrackSink:
                sink = new JDBCStreamSink(Constants.JDBC_CONNECTOR_URI, Constants.JDBC_USER, Constants.JDBC_PASSWORD, SegmentTrack.DB_TABLE, SaveMode.Append);
                break;

        }
        return sink;
    }
}
