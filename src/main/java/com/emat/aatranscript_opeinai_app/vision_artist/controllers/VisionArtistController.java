package com.emat.aatranscript_opeinai_app.vision_artist.controllers;

import com.emat.aatranscript_opeinai_app.vision_artist.model.*;
import com.emat.aatranscript_opeinai_app.vision_artist.services.VisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/vision-artist")
public class VisionArtistController {
    private final VisionService visionService;

    @PostMapping(value = "/my-vision", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisionResponse> upload(@RequestParam("file") @Validated MultipartFile file, @RequestParam() String pictureQuestion) throws IOException {
        log.info("Received request to get vision for file: {}", file.getName());
        return ResponseEntity.ok(visionService.getImageDescription(new VisionRequest(pictureQuestion, file)));
    }

    @PostMapping(value = "/tell-me-about", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadToSpeech(@RequestParam("file") @Validated MultipartFile file, @RequestParam() String pictureQuestion, @RequestParam() VisionLanguageEnum language) throws IOException {


        log.info("Received request to get vision for file: {} to speech", file.getName());

        VisionSpeechResponse response = visionService.getImageDescAndSpeech(new VisionSpeechRequest(file, pictureQuestion, language));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mp3"))
                .header("Content-Disposition", "attachment; filename=audio.mp3")
                .body(response.speechAudio());
    }

}
