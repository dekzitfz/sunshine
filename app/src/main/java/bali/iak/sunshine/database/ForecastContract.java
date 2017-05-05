package bali.iak.sunshine.database;

import android.provider.BaseColumns;

/**
 * Created by DEKZ on 5/5/2017.
 */

public class ForecastContract {

    public static final class ForecastEntry implements BaseColumns {

        //table name
        public static final String TABLE_NAME = "forecast";

        //city detail
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_CITY_LATITUDE = "city_latitude";
        public static final String COLUMN_CITY_LONGITUDE = "city_longitude";

        //forecast detail
        public static final String COLUMN_EPOCH_TIME = "epoch_time";
        public static final String COLUMN_MAX_TEMP = "max_temperature";
        public static final String COLUMN_MIN_TEMP = "min_temperature";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_WEATHER_MAIN = "weather_main";
        public static final String COLUMN_WEATHER_DESCRIPTION = "weather_description";
        public static final String COLUMN_WEATHER_ICON = "weather_icon";
        public static final String COLUMN_WIND_SPEED = "wind_speed";


        //additional info
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
