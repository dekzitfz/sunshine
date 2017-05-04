package bali.iak.sunshine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bali.iak.sunshine.R;
import bali.iak.sunshine.adapter.viewholder.ForecastItemViewHolder;
import bali.iak.sunshine.adapter.viewholder.TodayForecastViewHolder;
import bali.iak.sunshine.model.ListItem;

/**
 * Created by DEKZ on 5/3/2017.
 */

public class ForecastListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> data = new ArrayList<>();
    private Context context;
    private static final int VIEW_TODAY = 0;
    private static final int VIEW_NEXTDAY = 1;

    public ForecastListAdapter(List<ListItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TODAY){
            return new TodayForecastViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_today_forecast, parent, false));
        }else{
            return new ForecastItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_forecast, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int type = getItemViewType(position);
        if(type == VIEW_TODAY){
            ((TodayForecastViewHolder)holder).bind(data.get(position),context);
        }else{
            ((ForecastItemViewHolder)holder).bind(data.get(position),context);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            //top position aka Today
            return VIEW_TODAY;
        }else{
            return VIEW_NEXTDAY;
        }
    }
}
