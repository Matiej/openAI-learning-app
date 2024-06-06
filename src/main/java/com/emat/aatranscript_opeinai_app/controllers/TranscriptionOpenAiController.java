package com.emat.aatranscript_opeinai_app.controllers;


import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.CapitalDetailsResponse;
import com.emat.aatranscript_opeinai_app.model.CapitalResponse;
import com.emat.aatranscript_opeinai_app.model.Question;
import com.emat.aatranscript_opeinai_app.services.TranscriptionOpenAiService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transcript/openai")
public class TranscriptionOpenAiController {
    private static final Logger log = LoggerFactory.getLogger(TranscriptionOpenAiController.class);
    private final TranscriptionOpenAiService transcriptionOpenAiService;

    public TranscriptionOpenAiController(TranscriptionOpenAiService transcriptionOpenAiService) {
        this.transcriptionOpenAiService = transcriptionOpenAiService;
    }

    @PostMapping(value = "/ask", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Answer> askQuestion(@RequestBody @Valid RestOpenAiRequest request) {
        log.info("Received request to ask question: {}", request.question());
        Answer answer = transcriptionOpenAiService.getAnswer(Question.fromRestOpenAiRequest(request));
        return ResponseEntity.ok(answer);
    }

    @PostMapping(value = "/capital", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CapitalResponse> getCapital(@RequestBody @Valid RestCapitalRequest request) {
        log.info("Received request to get capital: {}", request.countryName());
        CapitalResponse response = transcriptionOpenAiService.getCapital(Question.fromRestCapitalRequest(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "capital/details", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CapitalDetailsResponse> getCapitalWithDetails(@RequestBody @Valid RestCapitalRequest request) {
        log.info("Received request to get capital with details: {}", request.countryName());
        CapitalDetailsResponse response = transcriptionOpenAiService.getCapitalWithDetails(Question.fromRestCapitalRequest(request));
        return ResponseEntity.ok(response);
    }
}
