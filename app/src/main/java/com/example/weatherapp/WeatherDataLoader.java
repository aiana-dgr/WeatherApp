package com.example.weatherapp;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherDataLoader {
    private static final String API_KEY = "e4f789342932fba8addecc1a65c7bea5";
    private static final String URL_ENDPOINT_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    private static final String URL_ENDPOINT_FORECAST = "https://api.openweathermap.org/data/2.5/forecast";

    private String apiCharset;

    public WeatherDataLoader() {
        // The encoding methods below require an encoding, which for the web is UTF-8 by default.
        apiCharset = StandardCharsets.UTF_8.name();
    }

    /**
     * This function does a generic web service GET HTTP request and returns the result.
     * @param url
     * @return
     * @throws IOException
     */
    private String getHttpResults(String url) throws IOException {
        System.out.println("Making call to URL: " + url);

        // Make a connection to the provided URL
        URLConnection connection = new URL(url).openConnection();

        // Open the response stream and get a reader for it.
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));

        // If the API was written well, there will be only one line,
        // but just in case, I will go through each line in the response.

        // Because this could happen multiple times, a StringBuilder is much more efficient.
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    /**
     * This function will prepare the URL for the current Weather call
     * and return the JSON result.
     * @param city
     * @return
     * @throws IOException
     */
    private String getWeatherJson(String city) throws IOException {
        String url = String.format("%s?q=%s&apiKey=%s&units=imperial",
                URL_ENDPOINT_WEATHER,
                URLEncoder.encode(city, apiCharset),
                URLEncoder.encode(API_KEY, apiCharset));

        return getHttpResults(url);
    }

    /**
     * This function will prepare the URL for the forecast call
     * and return the JSON result.
     * @param city
     * @return
     * @throws IOException
     */
    private String getForecastJson(String city) throws IOException {
        String url = String.format("%s?q=%s&apiKey=%s&units=imperial",
                URL_ENDPOINT_FORECAST,
                URLEncoder.encode(city, apiCharset),
                URLEncoder.encode(API_KEY, apiCharset));

        System.out.println(url);

        String result = getHttpResults(url);

        System.out.println(result);

        return result;
    }

    /**
     * Calls the weather api for the current weather of the provided city.
     * @param city
     * @return
     * @throws IOException
     */
    public WeatherConditions getWeather(String city) throws IOException {
        // Call the API
        String results = getWeatherJson(city);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        WeatherConditions conditions = gson.fromJson(results, WeatherConditions.class);

        return conditions;
    }

    public void getWeatherAndPostResults(String city, WeatherConditionsResultHandler handler) {
        try {
            WeatherConditions conditions = getWeather(city);
            handler.handleResult(conditions);
        } catch (IOException e) {
            e.printStackTrace();
            handler.handleResult(null);
        }
    }

    public WeatherConditions getWeatherAndPostResults(String city) {
        try {
            WeatherConditions conditions = getWeather(city);
            return conditions;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Calls the weather api for the forecast of the provided city.
     * @param city
     * @return
     * @throws IOException
     */
    public WeatherForecast getForecast(String city) throws IOException {
        // Call the API
        String results = getForecastJson(city);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        WeatherForecast forecast = gson.fromJson(results, WeatherForecast.class);

        return forecast;
    }

    public void getForecastAndPostResults(String city, WeatherForecastResultHandler handler) {
        try {
            WeatherForecast forecast = getForecast(city);
            handler.handleResult(forecast);
        } catch (IOException e) {
            e.printStackTrace();
            handler.handleResult(null);
        }
    }
}