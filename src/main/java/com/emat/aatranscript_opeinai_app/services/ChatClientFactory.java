package com.emat.aatranscript_opeinai_app.services;

import com.emat.aatranscript_opeinai_app.global.OpeApiParams;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class ChatClientFactory {

    private static final int MAX_TOKENS = 240;
    private static final float TEMPERATURE = 0.7f;
    private static final Map<OpenAiApi.ChatModel, ChatClient> clientMap = new EnumMap<>(OpenAiApi.ChatModel.class);
    private final OpeApiParams opeApiParams;

    public ChatClientFactory(OpeApiParams opeApiParams) {
        this.opeApiParams = opeApiParams;
        initializeClients();
    }

    private void initializeClients() {
        for (OpenAiApi.ChatModel chatModelEnum : OpenAiApi.ChatModel.values()) {
            OpenAiChatOptions options = OpenAiChatOptions.builder()
                    .withModel(chatModelEnum)
                    .withMaxTokens(MAX_TOKENS)
                    .withTemperature(TEMPERATURE)
                    .withLogprobs(true) // Enable log probabilities
                    .build();
            OpenAiChatModel openAiChatModel = new OpenAiChatModel(new OpenAiApi(opeApiParams.getOpenAIKey()), options);
            ChatClient client = ChatClient.create(openAiChatModel);
            ;
            clientMap.put(chatModelEnum, client);
        }
    }

    public ChatClient createClient(OpenAiApi.ChatModel chatModel) {
        return clientMap.get(chatModel);
    }

}
