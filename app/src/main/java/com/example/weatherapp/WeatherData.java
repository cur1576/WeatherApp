package com.example.weatherapp;

import java.io.Serializable;

class WeatherData implements Serializable {
    private String description;
    private String name;
    private String icon;
    private double temp;

    public WeatherData(String description, String name, String icon, double temp) {
        this.description = description;
        this.name = name;
        this.icon = icon;
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }
}
