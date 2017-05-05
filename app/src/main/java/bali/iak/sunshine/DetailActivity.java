package bali.iak.sunshine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import bali.iak.sunshine.model.ListItem;
import bali.iak.sunshine.utilities.SunshineWeatherUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DEKZ on 5/5/2017.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_date)
    TextView date;
    @BindView(R.id.detail_high_temperature)
    TextView highTemp;
    @BindView(R.id.detail_low_temperature)
    TextView lowTemp;
    @BindView(R.id.detail_weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.detail_weather_description)
    TextView weatherDesc;
    @BindView(R.id.detail_humidity)
    TextView humidity;
    @BindView(R.id.detail_pressure)
    TextView pressure;
    @BindView(R.id.detail_wind)
    TextView wind;

    private Gson gson = new Gson();
    private ListItem detailData = new ListItem();
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forecast);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getStringExtra("data") != null) {
            String jsonData = getIntent().getStringExtra("data");
            position = getIntent().getIntExtra("position", 0);
            detailData = gson.fromJson(jsonData, ListItem.class);
            bindData();
        }
    }

    private void bindData() {
        if (position == 0) {
            //today
            date.setText(detailData.getTodayReadableTime());
        } else {
            date.setText(detailData.getReadableTime(position));
        }

        weatherIcon.setImageResource(
                SunshineWeatherUtils
                        .getSmallArtResourceIdForWeatherCondition(
                                detailData.getWeather().get(0).getId()
                        )
        );

        highTemp.setText(
                SunshineWeatherUtils.formatTemperature(DetailActivity.this, detailData.getTemp().getMax())
        );
        lowTemp.setText(
                SunshineWeatherUtils.formatTemperature(DetailActivity.this, detailData.getTemp().getMin())
        );

        weatherDesc.setText(detailData.getWeather().get(0).getDescription());
        humidity.setText(detailData.getReadableHumidity());
        wind.setText(detailData.getReadableWindSpeed());
        pressure.setText(detailData.getReadablePressure());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }
}
