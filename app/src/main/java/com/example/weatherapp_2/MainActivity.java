package com.example.weatherapp_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp_2.Adapter.CustomRecyclerAdapter;
import com.example.weatherapp_2.Model.WeatherModel;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loading_PB;
    private RelativeLayout home_RelativeLayout;
    private TextView cityName_TextView, temp_TextView, weatherStateName_TextView;
    private ImageView search_ImageView, weatherStateIcon_ImageView, background_ImageView;
    private RecyclerView weather_RecyclerView;
    private TextInputEditText textInputEditText;
    private List<WeatherModel> weatherModelList;
    private CustomRecyclerAdapter customRecyclerAdapter;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //find id for each variable
        home_RelativeLayout = findViewById(R.id.home_RelativeLayout);
        cityName_TextView = findViewById(R.id.cityName_TextView);
        temp_TextView = findViewById(R.id.temp_TextView);
        weatherStateName_TextView = findViewById(R.id.weatherStateName_TextView);
        search_ImageView = findViewById(R.id.search_ImageView);
        weatherStateIcon_ImageView = findViewById(R.id.weatherStateIcon_ImageView);
        background_ImageView = findViewById(R.id.background_ImageView);
        weather_RecyclerView = findViewById(R.id.weather_RecyclerView);
        textInputEditText = findViewById(R.id.textInputEditText);

        //queue
        queue = Volley.newRequestQueue(MainActivity.this);


        //ArrayList
        weatherModelList = new ArrayList<>();

        //Recycler Adapter
        customRecyclerAdapter = new CustomRecyclerAdapter(MainActivity.this, weatherModelList);
        weather_RecyclerView.setAdapter(customRecyclerAdapter);

        String cityName = "new york";

        getWeatherInfo(cityName);

        search_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = textInputEditText.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter city name in Input Text", Toast.LENGTH_SHORT).show();
                } else {
                    cityName_TextView.setText(city);
                    getWeatherInfo(city);
                }
            }
        });
    }


    private void getWeatherInfo(String cityName){
        String URL = "https://api.weatherapi.com/v1/forecast.json?key=c4fcd42f8462484a948110513221502&q="+ cityName +"&days=1&aqi=no&alerts=no";
//        String URL = "https://api.weatherapi.com/v1/forecast.json?key=c4fcd42f8462484a948110513221502&q=dhaka&days=1&aqi=no&alerts=no";
        cityName_TextView.setText(cityName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        weatherModelList.clear();

                        try {
                            String temperature =  response.getJSONObject("current").getString("temp_f");
                            temp_TextView.setText(temperature + "ºF");
                            int isDay =  response.getJSONObject("current").getInt("is_day");
                            String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                            String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");

                            Log.d("ICON", "onResponse: " + conditionIcon);

                            Picasso.get().load("https:".concat(conditionIcon)).into(weatherStateIcon_ImageView);
                            weatherStateName_TextView.setText(condition);

                            if(isDay == 1){
                                //morning
                                Picasso.get().load("https://wallpaperaccess.com/full/4025575.jpg").into(background_ImageView);
                            } else{
                                //nighttime
                                Picasso.get().load("https://images.unsplash.com/photo-1435224654926-ecc9f7fa028c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8OHx8fGVufDB8fHx8&w=1000&q=80").into(background_ImageView);
                            }

                            JSONObject forecastObject = response.getJSONObject("forecast");
                            JSONObject forecast0 = forecastObject.getJSONArray("forecastday").getJSONObject(0);
                            JSONArray hoursArray = forecast0.getJSONArray("hour");

                            for(int i = 0; i< hoursArray.length(); i++){
                                JSONObject hourObject = hoursArray.getJSONObject(i);

                                String time = hourObject.getString("time");
                                String temperatureHourly = hourObject.getString("temp_f");
                                String iconHourly = hourObject.getJSONObject("condition").getString("icon");
                                String iconNameHourly = hourObject.getJSONObject("condition").getString("text");
                                String windSpeedHourly = hourObject.getString("wind_mph");


                                WeatherModel weatherModel = new WeatherModel(time, temperatureHourly,iconHourly,windSpeedHourly,iconNameHourly);

                                weatherModelList.add(weatherModel);
                            }
                            customRecyclerAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: Please enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

}