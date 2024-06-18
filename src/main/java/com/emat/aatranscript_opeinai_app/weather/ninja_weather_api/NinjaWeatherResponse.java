package com.emat.aatranscript_opeinai_app.weather.ninja_weather_api;

import com.emat.aatranscript_opeinai_app.weather.model.WeatherResponse;

import java.math.BigDecimal;

record NinjaWeatherResponse(
        Integer cloudPct,
        Integer temp,
        Integer feelsLike,
        Integer humidity,
        Integer minTemp,
        Integer maxTemp,
        BigDecimal windSpeed,
        Integer windDegrees,
        Integer sunrise,
        Integer sunset) {

    public WeatherResponse toWeatherResponse() {
        return new WeatherResponse(
                windSpeed,
                windDegrees,
                temp,
                humidity,
                sunset,
                sunrise,
                minTemp,
                cloudPct,
                feelsLike,
                maxTemp
        );
    }
}
