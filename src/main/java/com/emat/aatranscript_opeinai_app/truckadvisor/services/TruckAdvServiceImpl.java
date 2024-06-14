package com.emat.aatranscript_opeinai_app.truckadvisor.services;

import com.emat.aatranscript_opeinai_app.transcription.services.ChatClientFactory;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvAnswer;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvQuestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TruckAdvServiceImpl implements TruckAdvService {
    private final String LANGUAGE_PROMPT = "answer using the same language in which the question was asked.";
    private final ChatClientFactory chatClientFactory;

    @Value("classpath:templates/rag-truck-advisor-prompt.st")
    private Resource truckAdvisorPrompt;


    @Override
    public TruckAdvAnswer getBasicAdvice(TruckAdvQuestion question) {
        ChatClient client = chatClientFactory.createClient(OpenAiApi.ChatModel.GPT_3_5_TURBO);
        log.info("Asking truck advisor question: {}, using model: {}", question.question(), OpenAiApi.ChatModel.GPT_3_5_TURBO);
        PromptTemplate userPromptTemplate = new PromptTemplate(truckAdvisorPrompt);
        Prompt userPrompt = userPromptTemplate.create(Map.of("input", question.question()));
        ChatResponse chatResponse = client.prompt()
                .system(LANGUAGE_PROMPT)
                .messages(userPrompt.getInstructions())
                .call()
                .chatResponse();
        String chatOutput = chatResponse.getResult().getOutput().getContent();
        log.info("Received response from OpenAI: {}", chatOutput);
        return new TruckAdvAnswer(chatOutput);
    }
}
