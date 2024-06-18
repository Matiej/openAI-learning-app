package com.emat.aatranscript_opeinai_app.weather.ninjaweathertapi;

import com.emat.aatranscript_opeinai_app.global.weatherprops.WeatherClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
class NinjaWeatherApiServiceImpl implements NinjaWeatherApiService {
    private final String CITY = "city";
    private final RestTemplate restTemplate;
    private final WeatherClientProperties  configProperties;

    @Override
    public NinjaWeatherResponse getWeather(String city) {
        log.info("Getting weather for the city: {}", city);
        URI uri = UriComponentsBuilder.fromHttpUrl(configProperties.getUrl())
                .path(configProperties.getWeatherPath())
                .queryParam(CITY, city)
                .build()
                .toUri();
        log.info("Sending request to: {}", uri);
        return restTemplate.getForObject(uri, NinjaWeatherResponse.class);
    }
}
