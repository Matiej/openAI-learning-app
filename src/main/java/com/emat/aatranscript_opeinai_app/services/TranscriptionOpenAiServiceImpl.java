package com.emat.aatranscript_opeinai_app.services;

import ch.qos.logback.classic.Logger;
import com.emat.aatranscript_opeinai_app.controllers.TranscriptionOpenAiController;
import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.Question;
import groovy.util.logging.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import org.springframework.stereotype.Service;

@Slf4j
@Service
class TranscriptionOpenAiServiceImpl implements TranscriptionOpenAiService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TranscriptionOpenAiServiceImpl.class);
        private final ChatClient client;

    public TranscriptionOpenAiServiceImpl(ChatClient client) {
        this.client = client;
    }

    @Override
    public Answer getAnswer(Question question) {
                PromptTemplate promptTemplate = new PromptTemplate(question.getQuestion() + "?");
        Prompt prompt = promptTemplate.create();
        ChatResponse response = client.call(prompt);
        Generation result = response.getResult();
        AssistantMessage output = result.getOutput();
        log.info("Received response from OpenAI: {}", output.getContent());
        return new Answer(output.getContent());
    }
}
