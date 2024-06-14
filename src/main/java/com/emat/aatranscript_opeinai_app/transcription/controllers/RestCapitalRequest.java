package com.emat.aatranscript_opeinai_app.transcription.controllers;

import jakarta.validation.constraints.NotNull;

public record RestCapitalRequest(@NotNull(message = "Variable 'countryName' cant be null") String countryName) {
}
