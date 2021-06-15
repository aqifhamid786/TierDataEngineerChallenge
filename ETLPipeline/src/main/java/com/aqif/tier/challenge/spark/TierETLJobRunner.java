package com.aqif.tier.challenge.spark;

import com.aqif.tier.challenge.spark.stream.processors.ETLProcessor;
import com.aqif.tier.challenge.spark.stream.processors.IETLProcessor;
import com.aqif.tier.challenge.spark.stream.sinks.factory.StreamSinkType;
import com.aqif.tier.challenge.spark.stream.sources.factory.StreamSourceType;
import com.aqif.tier.challenge.spark.stream.transformations.factory.TransformationsType;
import org.apache.commons.cli.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.logging.Logger;

public class TierETLJobRunner {

    public static void main(String[] args) {
        try {

            ETLJobType jobType = getJobType(args);
            if(jobType==null) {
                System.exit(-1);
            }

            IETLProcessor processor = new ETLProcessor();
            Dataset<Row> df = null;
            switch (jobType) {
                case WEATHER: {
                    df = processor.extract(StreamSourceType.KafkaWeatherStreamSource);
                    df = processor.transform(df, TransformationsType.WeatherTransformations);
                    processor.load(df, StreamSinkType.JDBCWeatherSink);
                }
                break;
                case SEGMENT_TRACK: {
                    df = processor.extract(StreamSourceType.KafkaSegmentStreamSource);
                    df = processor.transform(df, TransformationsType.SegmentTrackTransformations);
                    processor.load(df, StreamSinkType.JDBCSegmentTrackSink);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ETLJobType getJobType(String[] args){
        Options options = new Options();
        Option input = new Option("j", "job", true, " weather 'or' segment_track");
        input.setRequired(true);
        options.addOption(input);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("ETLJob", options);
            System.exit(1);
        }

        ETLJobType jobType = null;
        try{
            jobType = ETLJobType.valueOf(cmd.getOptionValue("job"));
        } catch (Exception e) {
            // Silently return null if enum does not exist.
        }
        return jobType;

    }

}
