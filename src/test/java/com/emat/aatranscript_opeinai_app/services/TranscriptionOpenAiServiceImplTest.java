package com.emat.aatranscript_opeinai_app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TranscriptionOpenAiServiceImplTest {

    @Autowired
    TranscriptionOpenAiServiceImpl transcriptionOpenAiService;

    @Test
    void getAnswer() {
        String question = "What is the capital of Poland and what are two biggest cites in that country?";
        String answer = transcriptionOpenAiService.getAnswer(question);
        assertNotNull(answer);
        assertTrue(answer.contains("Warsaw") && answer.contains("Krakow"));
        System.out.printf(answer);
    }
}