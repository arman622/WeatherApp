package com.example.weatherapp_2.Model;

public class WeatherModel {

    private String time;
    private String temperature;
    private String icon;
    private String windSpeed;
    private String iconName;

    public WeatherModel(String time, String temperature, String icon, String windSpeed,String iconName) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
        this.windSpeed = windSpeed;
        this.iconName = iconName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIconName(){
        return iconName;
    }

    public void setIconName(String iconName){
        this.iconName= iconName;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "time='" + time + '\'' +
                ", temperature='" + temperature + '\'' +
                ", icon='" + icon + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                '}';
    }
}
