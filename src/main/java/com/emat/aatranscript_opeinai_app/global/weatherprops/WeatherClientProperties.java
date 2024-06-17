package com.emat.aatranscript_opeinai_app.global.weatherprops;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sfg.weather.client")
public class WeatherClientProperties {
    private String apiKey;
    private String url;
    private String weatherPath;

    public String getWeatherPath() {
        return weatherPath;
    }

    public void setWeatherPath(String weatherPath) {
        this.weatherPath = weatherPath;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
