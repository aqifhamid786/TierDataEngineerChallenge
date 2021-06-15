package com.aqif.tier.challenge.spark.tables;


import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public interface SegmentTrack {

    String DB_TABLE = "Segment_Track";
    String KAFKA_TOPIC = "track_events";

    String COL_ORIGNAL_TIMESTAMP = "original_timestamp_time";//:"2019-05-13 14:23:59";
    String COL_EVENT_NAME = "event_name";//:"Signup Started";
    String COL_CONTEXT_TIME_ZONE = "context_timezone";//:"Europe/Copenhagen";
    String COL_CONTEXT_OS = "context_os_version";//:"12.2";
    String COL_CONTEXT_OS_NAME = "context_os_name";//:"iOS";
    String COL_CONTEXT_DEVICE_TYPE = "context_device_type";//:"ios";
    String COL_CONTEXT_DEVICE_MODEL = "context_device_model";//:"iPhone7,2";
    String COL_CONTEXT_DEVICE_MANUFACTURER = "context_device_manufacturer";//:"Apple";
    String COL_CONTEXT_DEVICE_ID = "context_device_id";//:"3932FD8C-BD82-4240-8AD6-3F2369705EF5";
    String COL_CONTEXT_APP_VERSION = "context_app_version";//:"3.0.4";
    String COL_CONTEXT_APP_NAME = "context_app_name";//:"Tier";
    String COL_CONTEXT_APP_BUILD = "context_app_build";//:"3.0.4.1";
    String COL_ANONYMOUS_ID = "anonymous_id";//:"476D8356-AEC5-40D6-96FE-1D5E06D22ACB";
    String COL_RECEIVED_TIME = "received_time";//:"2019-05-13 14:24:00";
    String COL_SENT_TIME = "sent_time";//:"2019-05-13 14:24:00";
    String COL_PROPERTIES_RATING = "properties_rating";//:null;

    StructType df_schema = new StructType()
            .add(COL_ORIGNAL_TIMESTAMP, DataTypes.TimestampType)
            .add(COL_EVENT_NAME, DataTypes.StringType)
            .add(COL_CONTEXT_TIME_ZONE, DataTypes.StringType)
            .add(COL_CONTEXT_OS, DataTypes.StringType)
            .add(COL_CONTEXT_OS_NAME, DataTypes.StringType)
            .add(COL_CONTEXT_DEVICE_TYPE, DataTypes.StringType)
            .add(COL_CONTEXT_DEVICE_MODEL, DataTypes.StringType)
            .add(COL_CONTEXT_DEVICE_MANUFACTURER, DataTypes.StringType)
            .add(COL_CONTEXT_DEVICE_ID, DataTypes.StringType)
            .add(COL_CONTEXT_APP_VERSION, DataTypes.StringType)
            .add(COL_CONTEXT_APP_NAME, DataTypes.StringType)
            .add(COL_CONTEXT_APP_BUILD, DataTypes.StringType)
            .add(COL_ANONYMOUS_ID, DataTypes.StringType)
            .add(COL_RECEIVED_TIME, DataTypes.TimestampType)
            .add(COL_SENT_TIME, DataTypes.TimestampType)
            .add(COL_PROPERTIES_RATING, DataTypes.StringType);


    String[] columns = {COL_ORIGNAL_TIMESTAMP, COL_EVENT_NAME, COL_CONTEXT_TIME_ZONE, COL_CONTEXT_OS, COL_CONTEXT_OS_NAME,
            COL_CONTEXT_DEVICE_TYPE, COL_CONTEXT_DEVICE_MODEL, COL_CONTEXT_DEVICE_MANUFACTURER, COL_CONTEXT_DEVICE_ID, COL_CONTEXT_APP_VERSION,
            COL_CONTEXT_APP_NAME, COL_CONTEXT_APP_BUILD, COL_ANONYMOUS_ID, COL_RECEIVED_TIME, COL_SENT_TIME, COL_PROPERTIES_RATING };

}

