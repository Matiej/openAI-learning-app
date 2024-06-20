package com.emat.aatranscript_opeinai_app.vision_artist.controllers;

import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionRequest;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionResponse;
import com.emat.aatranscript_opeinai_app.vision_artist.services.VisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
