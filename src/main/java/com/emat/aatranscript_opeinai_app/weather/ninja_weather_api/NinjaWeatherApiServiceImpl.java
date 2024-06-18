package com.emat.aatranscript_opeinai_app.weather.ninja_weather_api;

import com.emat.aatranscript_opeinai_app.global.weatherprops.WeatherClientProperties;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherRequest;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
class NinjaWeatherApiServiceImpl implements NinjaWeatherApiService {
    private final RestTemplate restTemplate;
    private final WeatherClientProperties configProperties;

    @Override
    public WeatherResponse getWeather(WeatherRequest request) {
        log.info("Getting weather for the request: {}", request);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(configProperties.getUrl())
                .path(configProperties.getWeatherPath())
                .queryParam("city", request.location());
        if (request.country() != null && !request.country().isBlank()) {
            uriComponentsBuilder.queryParam("country", request.country());
        }
        if (request.state() != null && !request.state().isBlank()) {
            uriComponentsBuilder.queryParam("state", request.state());
        }
        URI uri = uriComponentsBuilder
                .build()
                .toUri();
        log.info("Sending request to: {}", uri);

        try {
            ResponseEntity<NinjaWeatherResponse> responseEntity = restTemplate.getForEntity(uri, NinjaWeatherResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                NinjaWeatherResponse response = responseEntity.getBody();
                return response.toWeatherResponse();
            } else {
                log.error("Failed to get weather data, status code: {}, for request: {}", responseEntity.getStatusCode(), uri);
                throw new RuntimeException("Failed to get weather data");
            }
        } catch (Exception e) {
            log.error("Failed to get weather data for request: {}", uri, e);
            throw new RuntimeException("Failed to get weather data", e);
        }


    }
}
