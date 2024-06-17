package com.emat.aatranscript_opeinai_app.weather.controllers;

import com.emat.aatranscript_opeinai_app.weather.model.WeatherAnswer;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherQuestion;
import com.emat.aatranscript_opeinai_app.weather.services.WeatherAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherAIService weatherService;


    @PostMapping(value = "/get-weather", consumes = "application/json", produces = "application/json")
    public ResponseEntity<WeatherAnswer> getWeather(@RequestBody @Valid RestWeatherQuestion request) {
        log.info("Received request to ask question: {}, on the endpoint: {}", request.weatherQuestion(), "/weather/getWeather");
        WeatherAnswer answer = weatherService.getWeatherBasicAnswer(new WeatherQuestion(request.city()));
        return ResponseEntity.ok(answer);
    }
}
