package com.emat.aatranscript_opeinai_app.global;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;


    @Bean
    public OpeApiParams getOpeApiParams() {
        return new OpeApiParams(apiKey);
    }

//    @Bean
//    public ChatClient chatClient() {
//        ChatModel chatModel = new OpenAiChatModel(new OpenAiApi(apiKey));
//        return ChatClient.create(chatModel);
//    }

    @Bean
    public freemarker.template.Configuration freemarkerConfig() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }
}
