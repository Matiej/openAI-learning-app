package com.emat.aatranscript_opeinai_app.services;

import ch.qos.logback.classic.Logger;
import com.emat.aatranscript_opeinai_app.controllers.TranscriptionOpenAiController;
import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.Question;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import groovy.util.logging.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@Service
class TranscriptionOpenAiServiceImpl implements TranscriptionOpenAiService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TranscriptionOpenAiServiceImpl.class);
    private final Configuration freemarkerConfig;
    private final ChatClientFactory chatClientFactory;

    public TranscriptionOpenAiServiceImpl(Configuration freemarkerConfig, ChatClientFactory chatClientFactory) {
        this.freemarkerConfig = freemarkerConfig;
        this.chatClientFactory = chatClientFactory;
    }

    @Value("classpath:templates/get-capital-prompt.ftl")
    private Resource getCapitalResource;

    @Override
    public Answer getAnswer(Question question) {
        ChatClient client = chatClientFactory.createClient(OpenAiApi.ChatModel.GPT_4);
        String response = client.prompt()
                .user(question.getQuestion() + "?")
                .call()
                .content();
        log.info("Received response from OpenAI: {}", response);
        return new Answer(response);
    }

    @Override
    public Answer getCapital(Question country) {
        ChatClient client = chatClientFactory.createClient(OpenAiApi.ChatModel.GPT_3_5_TURBO);
        StringWriter stringWriter = new StringWriter();
        try {
            Template template = freemarkerConfig.getTemplate(getCapitalResource.getFilename());
            template.process(Map.of("country", country.getQuestion()), stringWriter);
        } catch (IOException | TemplateException e) {
            log.error("Error while processing template: {}", e.getMessage());
        }

        String processedTemplate = stringWriter.toString();
        String[] templateParts = processedTemplate.split("\\R", 2);
        if (templateParts.length != 2) {
            log.error("Error while processing template: {}", processedTemplate);
            throw new RuntimeException("Template processing error: not enough template parts");
        }
        String response = client.prompt()
                .system(templateParts[0])
                .user(templateParts[1])
                .call().content();
        log.info("Received 'getCapital' response from OpenAI: {}", response);
        return new Answer(response);
    }
}
