package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    List<String> forecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getTemperature(View view) {

        // Get the name of the city
        EditText textCity = findViewById(R.id.textCity);
        String city = textCity.getText().toString();

        Log.i("MA", "Getting temperature for " + city);

        // Set up a new instance of our runnable object that will be run on the background thread
        GetTemperature getTemp = new GetTemperature(this, city);

        // Set up the thread that will use our runnable object
        Thread tempThread = new Thread(getTemp);

        // starts the thread in the background. It will automatically call the run method of
        // the getTemp object we gave it earlier
        tempThread.start();
    }

    public void getForecast(View view) {

        // Get the name of the city
        EditText textCity = findViewById(R.id.textCity);
        String city = textCity.getText().toString();

        Log.i("MA", "Getting weather forecast for " + city);

        // Set up a new instance of our runnable object that will be run on the background thread
        GetForecast getForecast = new GetForecast(this, city);

        // Set up the thread that will use our runnable object
        Thread forecastThread = new Thread(getForecast);

        // starts the thread in the background. It will automatically call the run method of
        // the getTemp object we gave it earlier
        forecastThread.start();
    }

    /**
     * This function will get called (on the main UI thread) once we have successfully returned
     * from the API with results.
     *
     * @param conditions The results of the API call. If an error occurred, this will be null.
     */
    void handleWeatherConditionsResult(WeatherConditions conditions) {
        Log.d("MA", "Back from API on the UI thread with the weather results!");

        // Check for an error
        if (conditions == null) {
            Log.d("MA", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the weather",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d("MA", "Conditions: " + conditions.getMeasurements().toString());

            // Get the current temperature
            Float temp = conditions.getMeasurements().get("temp");

            // Show the temperature to the user
            Toast.makeText(this, "It is currently " + temp + " degrees.",
                    Toast.LENGTH_LONG).show();
        }
    }

    void handleWeatherForecastResult(WeatherForecast forecast) {
        Log.d("MA", "Back from API on the UI thread with the weather forecast results!");

        // Check for an error
        if (forecast == null) {
            Log.d("MA", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the weather forecast",
                    Toast.LENGTH_LONG).show();
        } else {
            for (WeatherForecastItem item : forecast.getForecastItems()) {
                String time = item.getDateText();
                float temp = item.getMeasurements().get("temp");

                String conditions = "";
                if (item.getDescriptions().size() > 0) {
                    conditions = item.getDescriptions().get(0).getDescription();
                }

                float wind = item.getWind().get("speed");

                // Show the forecast items
                ListView wListView = (ListView) findViewById(R.id.listview);

                ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_main, forecastList);
                wListView.setAdapter(adapter);
            }
        }
    }
}