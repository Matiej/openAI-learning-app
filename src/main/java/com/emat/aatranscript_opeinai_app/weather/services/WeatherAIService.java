package com.emat.aatranscript_opeinai_app.weather.services;

import com.emat.aatranscript_opeinai_app.weather.model.WeatherAnswer;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherQuestion;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherRequest;

public interface WeatherAIService {
    WeatherAnswer getWeatherBasicAnswer(String city);
}
