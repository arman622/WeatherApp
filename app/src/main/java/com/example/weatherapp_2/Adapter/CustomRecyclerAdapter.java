package com.example.weatherapp_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp_2.Model.WeatherModel;
import com.example.weatherapp_2.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<WeatherModel> weatherModelList;

    public CustomRecyclerAdapter(Context context, List<WeatherModel> weatherModelList) {
        this.context = context;
        this.weatherModelList = weatherModelList;
    }

    @NonNull
    @Override
    public CustomRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapter.ViewHolder holder, int position) {

        WeatherModel weatherModel = weatherModelList.get(position);

        holder.tempHour_TextView.setText(weatherModel.getTemperature()+"ºF");
        holder.windSpeed_TextView.setText(weatherModel.getWindSpeed()+" mi/h");
        Picasso.get().load("https:".concat(weatherModel.getIcon())).into(holder.weatherStateIconHour_ImageView);
        holder.weatherStateNameHour_ImageView.setText(weatherModel.getIconName());

        SimpleDateFormat input= new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output= new SimpleDateFormat("hh:mm aa");
        try {
            Date currDate = input.parse(weatherModel.getTime());
            holder.hourly_TextView.setText(output.format(currDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return weatherModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView hourly_TextView, tempHour_TextView, windSpeed_TextView, weatherStateNameHour_ImageView;
        private ImageView weatherStateIconHour_ImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hourly_TextView = itemView.findViewById(R.id.hourly_TextView);
            tempHour_TextView = itemView.findViewById(R.id.tempHour_TextView);
            windSpeed_TextView = itemView.findViewById(R.id.windSpeed_TextView);
            weatherStateIconHour_ImageView = itemView.findViewById(R.id.weatherStateIconHour_ImageView);
            weatherStateNameHour_ImageView = itemView.findViewById(R.id.weatherStateNameHour_ImageView);


        }
    }
}
