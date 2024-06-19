package com.emat.aatranscript_opeinai_app.image_artist.controllers;

import com.emat.aatranscript_opeinai_app.image_artist.model.ImageArtistRequest;
import com.emat.aatranscript_opeinai_app.image_artist.services.ImageArtistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/image-artist")
public class ImageArtistController {
    private final ImageArtistService imageArtistService;

    @PostMapping(value = "/generate-image", produces = {MediaType.IMAGE_PNG_VALUE },
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateImage(@RequestBody @Valid RestImageArtistRequest request) {
        log.info("Generating image for the prompt: {}", request.imagePrompt());
        byte[] image = imageArtistService.generateImage(new ImageArtistRequest(request.imagePrompt()));
        return ResponseEntity.ok().body(image);
    }


}
