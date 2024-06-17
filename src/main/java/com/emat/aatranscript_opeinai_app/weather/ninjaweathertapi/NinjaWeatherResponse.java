package com.emat.aatranscript_opeinai_app.weather.ninjaweathertapi;

public record NinjaWeatherResponse(
        int cloudPct,
        int temp,
        int feelsLike,
        int humidity,
        int minTemp,
        int maxTemp,
        double windSpeed,
        int windDegrees,
        long sunrise,
        long sunset) {
}
