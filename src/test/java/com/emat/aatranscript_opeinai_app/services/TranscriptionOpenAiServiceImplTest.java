package com.emat.aatranscript_opeinai_app.services;

import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.Question;
import com.emat.aatranscript_opeinai_app.modelpromptengineering.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TranscriptionOpenAiServiceImplTest extends BaseTest {

    @Autowired
    TranscriptionOpenAiServiceImpl transcriptionOpenAiService;

    @Test
    void getAnswer() {
        Question question = new Question("What is the capital of Poland and what are two biggest cites in that country?");
        Answer answer = transcriptionOpenAiService.getAnswer(question);
        assertNotNull(answer);
        String answerContent = answer.getAnswer();
        assertTrue(answerContent.contains("Warsaw") && answerContent.contains("Krakow"));
        System.out.printf(answerContent);
    }
}