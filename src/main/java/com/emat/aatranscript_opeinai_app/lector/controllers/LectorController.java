package com.emat.aatranscript_opeinai_app.lector.controllers;

import com.emat.aatranscript_opeinai_app.lector.model.LectorLanguage;
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

@Slf4j
@RestController
@RequestMapping("/lector")
@RequiredArgsConstructor
public class LectorController {

    private final LectorService lectorService;

    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LectorResponse> read(@RequestBody RestLectorRequest request) {
        log.info("Received request to read text, using language: {}", request.language());

        LectorRequest.LectorRequestBuilder lectorRequestBuilder = LectorRequest.builder()
                .textToRead(request.textToRead())
                .language(request.language());

        if (!LectorLanguage.isValidLanguage(request.language().name())) {
            log.error("Invalid language provided: {}, default English language used", request.language());
            lectorRequestBuilder.language(LectorLanguage.EN);
        }

        return ResponseEntity.ok(lectorService.readText(lectorRequestBuilder.build()));
    }
}
