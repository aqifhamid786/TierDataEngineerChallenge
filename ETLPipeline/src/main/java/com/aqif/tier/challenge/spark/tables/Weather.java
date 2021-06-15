package com.aqif.tier.challenge.spark.tables;


import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public interface Weather {

    String DB_TABLE = "Weather";
    String KAFKA_TOPIC = "weather";

    String COL_DATA_TIME = "date_time";
    String COL_CITY = "city";
    String COL_CURRENT_APPARENT_TEMPERATURE = "currently_apparenttemperature";
    String COL_CURRENT_HUMIDITY = "currently_humidity";
    String COL_CURRENT_PRECIP_INTENSITY = "currently_precipintensity";
    String COL_CURRENT_PRECIP_PROBABILITY = "currently_precipprobability";
    String COL_CURRENT_PRECIP_TYPE = "currently_preciptype";
    String COL_CURRENT_TEMPERATURE = "currently_temperature";
    String COL_CURRENT_VISIBILITY = "currently_visibility";
    String COL_CURRENT_WIND_SPEED = "currently_windspeed";
    String COL_CURRENT_IS_RAINING = "currently_is_raining";

    StructType df_schema = new StructType()
            .add(COL_DATA_TIME, DataTypes.TimestampType)
            .add(COL_CITY, DataTypes.StringType)
            .add(COL_CURRENT_APPARENT_TEMPERATURE, DataTypes.FloatType)
            .add(COL_CURRENT_HUMIDITY, DataTypes.FloatType)
            .add(COL_CURRENT_PRECIP_INTENSITY , DataTypes.FloatType)
            .add(COL_CURRENT_PRECIP_PROBABILITY, DataTypes.FloatType)
            .add(COL_CURRENT_PRECIP_TYPE, DataTypes.StringType)
            .add(COL_CURRENT_TEMPERATURE, DataTypes.FloatType)
            .add(COL_CURRENT_VISIBILITY, DataTypes.FloatType)
            .add(COL_CURRENT_WIND_SPEED, DataTypes.FloatType);

    String[] non_null_columns = {COL_DATA_TIME, COL_CITY, COL_CURRENT_APPARENT_TEMPERATURE, COL_CURRENT_HUMIDITY, COL_CURRENT_PRECIP_INTENSITY,
            COL_CURRENT_PRECIP_PROBABILITY, COL_CURRENT_TEMPERATURE, COL_CURRENT_VISIBILITY, COL_CURRENT_WIND_SPEED};

}
