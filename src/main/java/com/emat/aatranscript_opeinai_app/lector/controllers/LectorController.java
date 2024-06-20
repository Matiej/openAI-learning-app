package com.emat.aatranscript_opeinai_app.lector.controllers;

import com.emat.aatranscript_opeinai_app.lector.model.LectorRequest;
import com.emat.aatranscript_opeinai_app.lector.model.LectorResponse;
import com.emat.aatranscript_opeinai_app.lector.services.LectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Slf4j
@RestController
@RequestMapping("/lector")
@RequiredArgsConstructor
public class LectorController {

    private final LectorService lectorService;

    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> read(@RequestBody RestLectorRequest request) {
        log.info("Received request to read text: '{}......', total text length: {}", request.textToRead().substring(0, 10), request.textToRead().length());

        LectorRequest lectorRequest = new LectorRequest(request.textToRead());

        byte[] speechAudio = lectorService.readText(lectorRequest).speechAudio();
        log.info("Returning audio response, size: {}", calculateSize(speechAudio));
        
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf("audio/mp3"))
                .header("Content-Disposition", "attachment; filename=audio.mp3")
                .body(speechAudio);
    }

    private String calculateSize(byte[] audioFile) {
        int sizeInBytes = audioFile.length;
        double sizeInMb = (double) sizeInBytes / (1024*1024);
        return String.format("%.2f MB", sizeInMb);
    }
}
