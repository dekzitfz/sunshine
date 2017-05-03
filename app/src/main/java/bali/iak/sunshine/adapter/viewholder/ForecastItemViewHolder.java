package bali.iak.sunshine.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bali.iak.sunshine.R;
import bali.iak.sunshine.model.ListItem;
import bali.iak.sunshine.utilities.SunshineWeatherUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DEKZ on 5/3/2017.
 */

public class ForecastItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.weather_icon)ImageView weatherIcon;
    @BindView(R.id.date)TextView date;
    @BindView(R.id.weather_description)TextView weatherDescription;
    @BindView(R.id.high_temperature)TextView highTemp;
    @BindView(R.id.low_temperature)TextView lowTemp;

    public ForecastItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final ListItem data, Context context){
        weatherIcon.setImageResource(
                SunshineWeatherUtils
                        .getSmallArtResourceIdForWeatherCondition(
                                data.getWeather().get(0).getId()
                        )
        );

        date.setText(data.getReadableTime());
        weatherDescription.setText(data.getWeather().get(0).getDescription());
        highTemp.setText(
                SunshineWeatherUtils.formatTemperature(context,data.getTemp().getMax())
        );
        lowTemp.setText(
                SunshineWeatherUtils.formatTemperature(context,data.getTemp().getMin())
        );
    }
}
