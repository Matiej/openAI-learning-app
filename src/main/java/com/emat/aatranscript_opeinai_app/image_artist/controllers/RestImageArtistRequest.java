package com.emat.aatranscript_opeinai_app.image_artist.controllers;

import jakarta.validation.constraints.NotNull;

public record RestImageArtistRequest(
        @NotNull(message = "Image prompt can't be empty or null") String imagePrompt
) {
}
