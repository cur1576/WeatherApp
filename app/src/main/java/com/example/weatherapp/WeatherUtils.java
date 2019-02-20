package com.example.weatherapp;

import android.graphics.Bitmap;

class WeatherUtils {

    private static final String TAG = WeatherUtils.class.getSimpleName();
    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q={0}&lang=de&appid={1}";
    private static final String KEY = "8c6ab864c65279e8cd83a86dcf415737";

    private static final String NAME = "name";
    private static final String WEATHER = "weather";
    private static final String DESCRIPTION = "description";
    private static final String ICON = "icon";
    private static final String TEMP = "temp";


    public static WeatherData getWeather(String toString) {
        return null;
    }

    public static Bitmap getImage(WeatherData weather) {
        return null;
    }
}
