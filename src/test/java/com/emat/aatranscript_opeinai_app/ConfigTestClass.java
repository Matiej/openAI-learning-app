package com.emat.aatranscript_opeinai_app;

import com.emat.aatranscript_opeinai_app.global.OpeApiParams;
import com.emat.aatranscript_opeinai_app.global.OpenApiConfiguration;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@PropertySource("classpath:application-test.properties")
public class ConfigTestClass {

    @Value("${spring-opeanai-test-key}")
    private String apiKey;

    @Bean
    public OpenAiChatModel openAiChatModel() {
        OpenAiApi openAiApi = new OpenAiApi(apiKey);
        OpenAiChatOptions openAiChatOptions = new OpenAiChatOptions();
        openAiChatOptions.setModel(OpenAiApi.ChatModel.GPT_4.value);
        OpenAiChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);
        // Tutaj zdefiniuj instancję OpenAiChatModel dla testów
        return openAiChatModel;
    }

    @Bean
    public OpenAiChatProperties openAiChatProperties() {
        OpenAiChatProperties openAiChatProperties = new OpenAiChatProperties();
//        openAiChatProperties.setApiKey(apiKey);
//        openAiChatProperties.se(OpenAiApi.ChatModel.GPT_4.value);
        return openAiChatProperties;
    }


}
