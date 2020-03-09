package com.example.stormy.weather;

public class Forecast {

    private Current currentWeather;
    private Hour[] hourForecast;

    public Current getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Current currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Hour[] getHourForecast() {
        return hourForecast;
    }

    public void setHourForecast(Hour[] hourForecast) {
        this.hourForecast = hourForecast;
    }

    

}
