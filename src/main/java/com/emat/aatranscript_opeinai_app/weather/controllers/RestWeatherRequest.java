package com.emat.aatranscript_opeinai_app.weather.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestWeatherRequest(@NotNull(message = "city variable can't be null or empty") String city) {
}
