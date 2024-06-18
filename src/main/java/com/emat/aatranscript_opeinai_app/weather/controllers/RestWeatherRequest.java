package com.emat.aatranscript_opeinai_app.weather.controllers;

import jakarta.validation.constraints.NotNull;

public record RestWeatherQuestion(@NotNull(message = "city variable can't be null or empty") String city,
                                  @NotNull(message = "weatherQuestion variable can't be null or empty") String weatherQuestion){
}
