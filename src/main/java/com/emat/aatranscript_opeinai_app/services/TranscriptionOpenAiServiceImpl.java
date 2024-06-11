package com.emat.aatranscript_opeinai_app.services;

import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.CapitalDetailsResponse;
import com.emat.aatranscript_opeinai_app.model.CapitalResponse;
import com.emat.aatranscript_opeinai_app.model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import groovy.util.logging.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
class TranscriptionOpenAiServiceImpl implements TranscriptionOpenAiService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TranscriptionOpenAiServiceImpl.class);
    private final String LANGUAGE_PROMPT = "Answer using english language.";
    private final Configuration freemarkerConfig;
    private final ChatClientFactory chatClientFactory;
    private final ObjectMapper objectMapper;

    public TranscriptionOpenAiServiceImpl(Configuration freemarkerConfig, ChatClientFactory chatClientFactory, ObjectMapper objectMapper) {
        this.freemarkerConfig = freemarkerConfig;
        this.chatClientFactory = chatClientFactory;
        this.objectMapper = objectMapper;
    }

    @Value("classpath:templates/get-capital-system-prompt.st")
    private Resource systemPromptResource;
    @Value("classpath:templates/get-capital-user-prompt.st")
    private Resource userPromptResource;

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
    public CapitalResponse getCapital(Question country) {
        ChatClient client = chatClientFactory.createClient(OpenAiApi.ChatModel.GPT_3_5_TURBO);

        BeanOutputConverter<CapitalResponse> parser = new BeanOutputConverter<>(CapitalResponse.class);
        String capitalResponseExample = parser.getFormat();

        PromptTemplate systemPromptTemplate = new PromptTemplate(systemPromptResource);
        Prompt systemPrompt = systemPromptTemplate.create(Map.of("capitalResponse", capitalResponseExample));
        PromptTemplate userPromptTemplate = new PromptTemplate(userPromptResource);
        Prompt userPrompt = userPromptTemplate.create(Map.of("country", country.getQuestion()));

        List<Message> instructions = new ArrayList<>(systemPrompt.getInstructions());
        instructions.addAll(userPrompt.getInstructions());

        ChatResponse chatResponse = client.prompt()
                .system(LANGUAGE_PROMPT)
                .messages(instructions)
                .call().chatResponse();
        String openAiJsonResponse = chatResponse.getResult().getOutput().getContent();

        log.info("Received 'getCapital' openAiJsonResponse from OpenAI: {}", openAiJsonResponse);
        return parser.convert(openAiJsonResponse);
    }

    @Override
    public CapitalDetailsResponse getCapitalWithDetails(Question question) {
        ChatClient client = chatClientFactory.createClient(OpenAiApi.ChatModel.GPT_3_5_TURBO);
        BeanOutputConverter<CapitalDetailsResponse> parser = new BeanOutputConverter<>(CapitalDetailsResponse.class);
        String capitalDetailResponseJSON = parser.getFormat();
        List<Message> instructions = getInstructions(systemPromptResource, "capitalResponse", capitalDetailResponseJSON);
        instructions.addAll(getInstructions(userPromptResource, "country", question.getQuestion()));

        ChatResponse chatResponse = client.prompt()
                .system(LANGUAGE_PROMPT)
                .messages(instructions)
                .call().chatResponse();
        String openAiJsonResponse = chatResponse.getResult().getOutput().getContent();

        log.info("Received 'getCapitalWithDetails' openAiJsonResponse from OpenAI: {}", openAiJsonResponse);
        return parser.convert(openAiJsonResponse);
    }

    private List<Message> getInstructions(Resource template, String keyWord, String value) {
        PromptTemplate userPromptTemplate = new PromptTemplate(template);
        Prompt prompt = userPromptTemplate.create(Map.of(keyWord, value));
        List<Message> instructions = prompt.getInstructions();
//        UserMessage userInstruction = new UserMessage(instructions.getFirst().getMedia());
        return new ArrayList<>(instructions);
    }


}
