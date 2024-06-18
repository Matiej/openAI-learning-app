package com.emat.aatranscript_opeinai_app.weather.ninja_weather_api;

import com.emat.aatranscript_opeinai_app.weather.model.WeatherRequest;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherResponse;

public interface NinjaWeatherApiService {
    WeatherResponse getWeather(WeatherRequest request);
}
