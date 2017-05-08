package bali.iak.sunshine;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bali.iak.sunshine.adapter.ForecastListAdapter;
import bali.iak.sunshine.adapter.OnclickListener;
import bali.iak.sunshine.database.ForecastDBHelper;
import bali.iak.sunshine.model.DailyForecast;
import bali.iak.sunshine.model.ListItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnclickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    // TES TES
    @BindView(R.id.rv_forecast)RecyclerView rvForecast;
    @BindView(R.id.pb_forecast)
    ProgressBar pb;
    @BindView(R.id.tv_error)
    TextView errorView;

    private ForecastListAdapter forecastListAdapter;
    private List<ListItem> forecastData = new ArrayList<>();
    private Gson gson = new Gson();
    private SQLiteDatabase db;
    private DailyForecast dailyForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setElevation(0);
        }

        setupData();
    }

    private void setupData(){
        forecastListAdapter = new ForecastListAdapter(forecastData,MainActivity.this);
        rvForecast.setLayoutManager(new LinearLayoutManager(this));
        rvForecast.setAdapter(forecastListAdapter);

        getData();
    }

    private void getData(){
        updateView("loading");
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=-8.650000&lon=115.216667&cnt=16&appid=83003ca00bb8eec11d7976f5ee0282fd&units=metric";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateView("complete");
                        Log.d(TAG,"response RECEIVED!!!");
                        Log.i(TAG,response);
                        forecastData.clear();
                        try {
                            dailyForecast = gson.fromJson(response, DailyForecast.class);
                            for(ListItem item : dailyForecast.getList()){
                                forecastData.add(item);
                            }
                            forecastListAdapter.notifyDataSetChanged();

                            forecastListAdapter.setClickListener(MainActivity.this);
                            saveForecastToDB();
                        }catch (Exception e){
                            Log.e(TAG,e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        updateView("error");
                        if(error!=null){
                            Log.e(TAG,error.getMessage());
                        }else{
                            Log.e(TAG,"Something wrong happened");
                        }
                    }
                });

        requestQueue.add(request);
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

    @Override
    public void onItemClick(ListItem data, int position) {
        Intent detail = new Intent(MainActivity.this, DetailActivity.class);
        detail.putExtra("data", gson.toJson(data));
        detail.putExtra("position", position);
        startActivity(detail);
    }

    private void saveForecastToDB() {
        ForecastDBHelper dbHelper = new ForecastDBHelper(this);
        for (ListItem item : dailyForecast.getList()) {
            dbHelper.saveForecast(dailyForecast.getCity(), item);
        }
    }

    private void updateView(String state) {
        if (state.equals("loading")) {
            pb.setVisibility(View.VISIBLE);
            rvForecast.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        } else if (state.equals("error")) {
            pb.setVisibility(View.GONE);
            rvForecast.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
            rvForecast.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }
    }
}
