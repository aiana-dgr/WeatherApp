package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class WeatherForecastItem {
    @SerializedName("dt_txt")
    private String dateText;

    @SerializedName("main")
    private Map<String, Float> measurements;

    @SerializedName("weather")
    private List<WeatherDescription> descriptions;

    private Map<String, Float> wind;


    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public Map<String, Float> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Map<String, Float> measurements) {
        this.measurements = measurements;
    }

    public List<WeatherDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<WeatherDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public Map<String, Float> getWind() {
        return wind;
    }

    public void setWind(Map<String, Float> wind) {
        this.wind = wind;
    }
}
