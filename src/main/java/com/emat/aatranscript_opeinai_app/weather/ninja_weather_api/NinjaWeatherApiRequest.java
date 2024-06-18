package com.emat.aatranscript_opeinai_app.weather.ninja_weather_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

  record NinjaWeatherApiRequest (
        @JsonProperty(required = true) @JsonPropertyDescription("The city and state e.g. San Francisco, CA") String city,
        @JsonProperty(required = false) @JsonPropertyDescription("Optional State for US Cities Only. Use full name of State") String state,
        @JsonProperty(required = false) @JsonPropertyDescription("Optional Country name") String country
) {
}
