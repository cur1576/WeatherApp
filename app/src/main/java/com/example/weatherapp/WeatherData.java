package com.example.weatherapp;

import java.io.Serializable;

class WeatherData implements Serializable {
    public String description;
    public String name;
    public String icon;
    public double temp;

    public WeatherData(String description, String name, String icon, double temp) {
        this.description = description;
        this.name = name;
        this.icon = icon;
        this.temp = temp;
    }

    // TODO muss implementiert werden
}
