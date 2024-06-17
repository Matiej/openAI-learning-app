package com.emat.aatranscript_opeinai_app.truckadvisor.controllers;

import com.emat.aatranscript_opeinai_app.transcription.controllers.RestOpenAiRequest;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvAnswer;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvQuestion;
import com.emat.aatranscript_opeinai_app.truckadvisor.services.TruckAdvService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/truckadv")
@RequiredArgsConstructor
public class TruckAdvController {

    private final TruckAdvService truckAdvService;

    @PostMapping(value = "/get-advise", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TruckAdvAnswer> getTruckAdvise(@RequestBody @Valid RestOpenAiRequest request) {
        log.info("Received request to ask question: {}, on the endpoint: {}", request.question(), "/truckadv/getAdvise");
        TruckAdvAnswer answer = truckAdvService.getBasicAdvice(new TruckAdvQuestion(request.question()));
        return ResponseEntity.ok(answer);
    }

    @PostMapping(value = "/get-best-price-truck-advice", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TruckAdvAnswer> getBestPriceTruckAdvice(@RequestBody @Valid RestOpenAiRequest request) {
        log.info("Received request to ask question: {}, on the endpoint: {}", request.question(), "/truckadv/getAdvise");
        TruckAdvAnswer answer = truckAdvService.getLowestPriceTruckAdvice(new TruckAdvQuestion(request.question()));
        return ResponseEntity.ok(answer);
    }
}
