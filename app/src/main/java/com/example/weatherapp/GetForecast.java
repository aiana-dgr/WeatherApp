package com.example.weatherapp;


import java.lang.ref.WeakReference;

public class GetForecast implements Runnable {
    private WeakReference<MainActivity> activityRef;
    private String city;

    /**
     * Sets up the runnable to be called. It needs the MainActivity so it can run code on the
     * UI thread, and also the city so that it can get its weather conditions.
     *
     * @param activity
     * @param city
     */
    public GetForecast(MainActivity activity, String city) {
        this.activityRef = new WeakReference<MainActivity>(activity);
        this.city = city;
    }

    @Override
    public void run() {
        // Get the activity from the WeakReference
        final MainActivity activity = activityRef.get();

        // This is the function that will be run on the background thread.
        WeatherDataLoader loader = new WeatherDataLoader();
        if (activity != null) {
            loader.getForecastAndPostResults(city, (forecast) -> {
                activity.runOnUiThread(() -> {
                    // forecast contains the results of the API call
                    activity.handleWeatherForecastResult(forecast);
                });
            });
        }
    }
}
