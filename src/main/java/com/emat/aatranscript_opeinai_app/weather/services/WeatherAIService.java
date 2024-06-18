package com.emat.aatranscript_opeinai_app.weather.services;

import com.emat.aatranscript_opeinai_app.weather.model.WeatherAnswer;

public interface WeatherAIService {
    WeatherAnswer getWeatherBasicAnswer(String city);
}
