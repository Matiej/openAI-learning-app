package com.emat.aatranscript_opeinai_app.transcription.controllers;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RestOpenAiRequest(
        @Size(min = 10, max = 4000, message = "Question must be between 10 and 4000 characters")
        @NotNull(message = "Question cannot be null")
        String question
) {}
