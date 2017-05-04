package bali.iak.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import bali.iak.sunshine.adapter.ForecastListAdapter;
import bali.iak.sunshine.model.DummyForecast;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_forecast)RecyclerView rvForecast;

    private ForecastListAdapter forecastListAdapter;
    private List<DummyForecast> forecastData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupData();
    }

    private void setupData(){
        forecastListAdapter = new ForecastListAdapter(forecastData, MainActivity.this);
        rvForecast.setLayoutManager(new LinearLayoutManager(this));
        rvForecast.setAdapter(forecastListAdapter);
        getData();
    }

    private void getData(){
        DummyForecast dummyForecast1 = new DummyForecast("Sunday, 7 May", "Sunny", "47", "37", 955);
        DummyForecast dummyForecast2 = new DummyForecast("Monday, 8 May", "Foggy", "33", "39", 702);
        DummyForecast dummyForecast3 = new DummyForecast("Tuesday, 9 May", "Foggy", "48", "40", 702);
        DummyForecast dummyForecast4 = new DummyForecast("Wednesday, 10 May", "Sunny", "35", "27", 955);
        DummyForecast dummyForecast5 = new DummyForecast("Thursday, 11 May", "Rainy", "40", "34", 505);
        DummyForecast dummyForecast6 = new DummyForecast("Friday, 12 May", "Sunny", "47", "35", 955);
        DummyForecast dummyForecast7 = new DummyForecast("Saturday, 13 May", "Sunny", "40", "32", 955);

        forecastData.add(dummyForecast1);
        forecastData.add(dummyForecast2);
        forecastData.add(dummyForecast3);
        forecastData.add(dummyForecast4);
        forecastData.add(dummyForecast5);
        forecastData.add(dummyForecast6);
        forecastData.add(dummyForecast7);

        forecastListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
