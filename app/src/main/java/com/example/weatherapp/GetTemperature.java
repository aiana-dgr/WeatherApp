package com.example.weatherapp;

import android.util.Log;

import java.lang.ref.WeakReference;

public class GetTemperature implements Runnable {
    private WeakReference<MainActivity> activityRef;
    private String city;

    /**
     * Sets up the runnable to be called. It needs the MainActivity so it can run code on the
     * UI thread, and also the city so that it can get its weather conditions.
     *
     * @param activity
     * @param city
     */
    public GetTemperature(MainActivity activity, String city) {
        this.activityRef = new WeakReference<MainActivity>(activity);
        this.city = city;
    }

    @Override
    public void run() {
        // This is the function that will be run on the background thread.
        WeatherDataLoader loader = new WeatherDataLoader();

        // Now, call the function that will get the results from the API and then when it is done,
        // it will call the "handleResult" function on this new WeatherConditionsResultHandler
        // object that we are giving it.

        final WeatherConditions conditions = loader.getWeatherAndPostResults(city);

        // Get the activity from the WeakReference
        final MainActivity activity = activityRef.get();

        // Check that activity is not destroyed
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // This is code that will now run on the UI thread. Call the function in
                    // MainActivity that will update the UI correctly.
                    activity.handleWeatherConditionsResult(conditions);
                }
            });
        }
    }
}