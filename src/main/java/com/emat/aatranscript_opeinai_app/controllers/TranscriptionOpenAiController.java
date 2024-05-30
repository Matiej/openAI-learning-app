package com.emat.aatranscript_opeinai_app.controllers;


import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.services.TranscriptionOpenAiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transcript/openai")
public class TranscriptionOpenAiController {
    private final TranscriptionOpenAiService transcriptionOpenAiService;

    public TranscriptionOpenAiController(TranscriptionOpenAiService transcriptionOpenAiService) {
        this.transcriptionOpenAiService = transcriptionOpenAiService;
    }

    @PostMapping
    public ResponseEntity<Answer> askQuestion(@RequestBody @Valid RestOpenAiRequest request) {
        return null;
    }
}
