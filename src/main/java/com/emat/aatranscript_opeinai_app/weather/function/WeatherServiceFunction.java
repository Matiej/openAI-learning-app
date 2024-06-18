package com.emat.aatranscript_opeinai_app.weather.function;

import com.emat.aatranscript_opeinai_app.weather.model.WeatherRequest;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherResponse;
import com.emat.aatranscript_opeinai_app.weather.ninja_weather_api.NinjaWeatherApiService;

import java.util.function.Function;

public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {
    private final NinjaWeatherApiService ninjaWeatherApiService;

    public WeatherServiceFunction(NinjaWeatherApiService ninjaWeatherApiService) {
        this.ninjaWeatherApiService = ninjaWeatherApiService;
    }


    @Override
    public WeatherResponse apply(WeatherRequest request) {
        WeatherResponse weatherResponse = ninjaWeatherApiService.getWeather(request);
        return weatherResponse;
    }
}
