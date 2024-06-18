package com.emat.aatranscript_opeinai_app.global;

import com.emat.aatranscript_opeinai_app.global.weatherprops.WeatherClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WeatherClientConfig {

    private final WeatherClientProperties properties;

    @Autowired
    public WeatherClientConfig(WeatherClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("X-Api-Key", properties.getApiKey());
            headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().setAll(headers);
            return execution.execute(request, body);
        };
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }
}
