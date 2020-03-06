package com.example.stormy.weather;

public class Forecast {

    public Current getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Current currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Hourly[] getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(Hourly[] hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    private Current currentWeather;
    private Hourly[] hourlyForecast;

    

}
