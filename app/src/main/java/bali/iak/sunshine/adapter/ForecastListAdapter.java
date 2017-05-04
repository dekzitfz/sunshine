package bali.iak.sunshine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bali.iak.sunshine.R;
import bali.iak.sunshine.adapter.viewholder.ForecastItemViewHolder;
import bali.iak.sunshine.model.DummyForecast;

/**
 * Created by DEKZ on 5/3/2017.
 */

public class ForecastListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DummyForecast> data = new ArrayList<>();
    private Context context;

    public ForecastListAdapter(List<DummyForecast> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForecastItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ForecastItemViewHolder)holder).bind(data.get(position),context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
