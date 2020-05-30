package com.example.weatherapp;

import android.util.Log;

public class GetForecast implements Runnable {
    private MainActivity activity;
    private String city;

    /**
     * Sets up the runnable to be called. It needs the MainActivity so it can run code on the
     * UI thread, and also the city so that it can get its weather conditions.
     * @param activity
     * @param city
     */
    public GetForecast(MainActivity activity, String city) {
        this.activity = activity;
        this.city = city;
    }
    @Override
    public void run() {
        // This is the function that will be run on the background thread.
        WeatherDataLoader loader = new WeatherDataLoader();
        loader.getForecastAndPostResults(city, (forecast) -> {
            activity.runOnUiThread(() -> {
                // forecast contains the results of the API call
                activity.handleWeatherForecastResult(forecast);
            });
        });
    }
}
