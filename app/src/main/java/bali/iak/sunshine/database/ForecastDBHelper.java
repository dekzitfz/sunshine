package bali.iak.sunshine.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bali.iak.sunshine.model.City;
import bali.iak.sunshine.model.DailyForecast;
import bali.iak.sunshine.model.ListItem;
import bali.iak.sunshine.model.Temp;
import bali.iak.sunshine.model.WeatherItem;

/**
 * Created by DEKZ on 5/5/2017.
 */

public class ForecastDBHelper extends SQLiteOpenHelper {

    private static final String TAG = ForecastDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "sunshine.db";
    private static final int DATABASE_VERSION = 1;

    public ForecastDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DATABASE_SQL = "CREATE TABLE " + ForecastContract.ForecastEntry.TABLE_NAME + " ("
                + ForecastContract.ForecastEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ForecastContract.ForecastEntry.COLUMN_CITY_ID + " INTEGER, "
                + ForecastContract.ForecastEntry.COLUMN_CITY_NAME + " TEXT, "
                + ForecastContract.ForecastEntry.COLUMN_CITY_LATITUDE + " TEXT, "
                + ForecastContract.ForecastEntry.COLUMN_CITY_LONGITUDE + " TEXT, "
                + ForecastContract.ForecastEntry.COLUMN_EPOCH_TIME + " INTEGER, "
                + ForecastContract.ForecastEntry.COLUMN_MAX_TEMP + " REAL, "
                + ForecastContract.ForecastEntry.COLUMN_MIN_TEMP + " REAL, "
                + ForecastContract.ForecastEntry.COLUMN_HUMIDITY + " INTEGER, "
                + ForecastContract.ForecastEntry.COLUMN_PRESSURE + " REAL, "
                + ForecastContract.ForecastEntry.COLUMN_WEATHER_ID + " INTEGER, "
                + ForecastContract.ForecastEntry.COLUMN_WEATHER_MAIN + " TEXT, "
                + ForecastContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION + " TEXT, "
                + ForecastContract.ForecastEntry.COLUMN_WEATHER_ICON + " TEXT, "
                + ForecastContract.ForecastEntry.COLUMN_WIND_SPEED + " REAL, "
                + ForecastContract.ForecastEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')));";

        db.execSQL(CREATE_DATABASE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ForecastContract.ForecastEntry.TABLE_NAME);
        onCreate(db);
    }

    public void saveForecast(City city, ListItem data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_ID, city.getId());
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_NAME, city.getName());
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_LATITUDE, city.getCoord().getLat());
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_LONGITUDE, city.getCoord().getLon());
        cv.put(ForecastContract.ForecastEntry.COLUMN_EPOCH_TIME, data.getDt());
        cv.put(ForecastContract.ForecastEntry.COLUMN_MAX_TEMP, data.getTemp().getMax());
        cv.put(ForecastContract.ForecastEntry.COLUMN_MIN_TEMP, data.getTemp().getMin());
        cv.put(ForecastContract.ForecastEntry.COLUMN_PRESSURE, data.getPressure());
        cv.put(ForecastContract.ForecastEntry.COLUMN_HUMIDITY, data.getHumidity());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_ID, data.getWeather().get(0).getId());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_MAIN, data.getWeather().get(0).getMain());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION, data.getWeather().get(0).getDescription());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_ICON, data.getWeather().get(0).getIcon());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WIND_SPEED, data.getSpeed());

        long result = db.insert(ForecastContract.ForecastEntry.TABLE_NAME, null, cv);
        Log.i(TAG, "saveForecast result -> " + result);
        db.close();
    }

    public DailyForecast getSavedForecast(String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ListItem> listItems = new ArrayList<>();

        City resultCity = new City();
        resultCity.setName(city);

        DailyForecast result = new DailyForecast();
        result.setCity(resultCity);
        result.setList(listItems);

        Cursor cursor = db.query(ForecastContract.ForecastEntry.TABLE_NAME,
                null,
                ForecastContract.ForecastEntry.COLUMN_CITY_NAME + "=?",
                new String[]{city},
                null,
                null,
                null,
                null);
        int total = cursor.getCount();
        if (total > 0) {
            if (cursor.moveToFirst()) {
                do {
                    List<WeatherItem> listWeatherItems = new ArrayList<>();
                    WeatherItem weatherItem = new WeatherItem();
                    listWeatherItems.add(weatherItem);

                    Temp temp = new Temp();

                    ListItem item = new ListItem();
                    //item.getWeather().add(weatherItem);
                    item.setWeather(listWeatherItems);
                    item.setTemp(temp);

                    item.setDt(cursor.getInt(cursor.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_EPOCH_TIME)));
                    item.getWeather().get(0).setId(cursor.getInt(cursor.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_WEATHER_ID)));
                    item.getWeather().get(0).setDescription(cursor.getString(cursor.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION)));
                    item.getTemp().setMax(cursor.getDouble(cursor.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_MAX_TEMP)));
                    item.getTemp().setMin(cursor.getDouble(cursor.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_MIN_TEMP)));
                    result.getList().add(item);
                } while (cursor.moveToNext());
            }
        } else {
            //data not found
            Log.w(TAG, "getSavedForecast not found any data!");
        }
        cursor.close();
        db.close();

        Log.d(TAG, "result -> " + result.toString());
        return result;
    }

    public boolean isDataAlreadyExist(String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ForecastContract.ForecastEntry.TABLE_NAME,
                null,
                ForecastContract.ForecastEntry.COLUMN_CITY_NAME + "=?",
                new String[]{city},
                null,
                null,
                null,
                null);
        int total = cursor.getCount();
        cursor.close();
        db.close();
        return total > 0;
    }

    public void updateData(City city, ListItem data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_ID, city.getId());
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_NAME, city.getName());
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_LATITUDE, city.getCoord().getLat());
        cv.put(ForecastContract.ForecastEntry.COLUMN_CITY_LONGITUDE, city.getCoord().getLon());
        cv.put(ForecastContract.ForecastEntry.COLUMN_EPOCH_TIME, data.getDt());
        cv.put(ForecastContract.ForecastEntry.COLUMN_MAX_TEMP, data.getTemp().getMax());
        cv.put(ForecastContract.ForecastEntry.COLUMN_MIN_TEMP, data.getTemp().getMin());
        cv.put(ForecastContract.ForecastEntry.COLUMN_PRESSURE, data.getPressure());
        cv.put(ForecastContract.ForecastEntry.COLUMN_HUMIDITY, data.getHumidity());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_ID, data.getWeather().get(0).getId());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_MAIN, data.getWeather().get(0).getMain());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION, data.getWeather().get(0).getDescription());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WEATHER_ICON, data.getWeather().get(0).getIcon());
        cv.put(ForecastContract.ForecastEntry.COLUMN_WIND_SPEED, data.getSpeed());

        long result = db.update(ForecastContract.ForecastEntry.TABLE_NAME,
                cv,
                ForecastContract.ForecastEntry.COLUMN_CITY_NAME + " = ?",
                new String[]{city.getName()}
        );

        db.execSQL("UPDATE " + ForecastContract.ForecastEntry.TABLE_NAME +
                        " SET " + ForecastContract.ForecastEntry.COLUMN_TIMESTAMP + "=(DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME'))" +
                        " WHERE " + ForecastContract.ForecastEntry.COLUMN_CITY_NAME + "=?",
                new String[]{city.getName()});

        Log.i(TAG, "update result -> " + result);

        db.close();
    }
}
