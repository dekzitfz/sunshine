package bali.iak.sunshine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DEKZ on 5/5/2017.
 */

public class ForecastDBHelper extends SQLiteOpenHelper {

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
                + ForecastContract.ForecastEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(CREATE_DATABASE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ForecastContract.ForecastEntry.TABLE_NAME);
        onCreate(db);
    }
}
