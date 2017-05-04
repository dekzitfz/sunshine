package bali.iak.sunshine;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by DEKZ on 5/4/2017.
 */

public class Sunshine extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
