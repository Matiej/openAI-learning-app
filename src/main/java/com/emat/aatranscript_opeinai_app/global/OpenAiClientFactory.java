package com.emat.aatranscript_opeinai_app.global;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.EnumMap;
import java.util.Map;

@Component
public class OpenAiClientFactory {

    private static final int MAX_TOKENS = 240;
    private static final float TEMPERATURE = 0.7f;
    private static final Map<OpenAiApi.ChatModel, ChatClient> clientMap = new EnumMap<>(OpenAiApi.ChatModel.class);
    private static final Map<OpenAiApi.ChatModel, OpenAiChatModel> openAiChatModelMap = new EnumMap<>(OpenAiApi.ChatModel.class);
    private final OpeApiParams opeApiParams;
    private final RestClient.Builder restClientBuilder;

    public OpenAiClientFactory(OpeApiParams opeApiParams, RestClient.Builder restClientBuilder) {
        this.opeApiParams = opeApiParams;
        this.restClientBuilder = restClientBuilder;
        initializeClients();
        initializeOpenAiChatModels();
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

    private void initializeOpenAiChatModels() {
        for (OpenAiApi.ChatModel chatModelEnum : OpenAiApi.ChatModel.values()) {
            OpenAiChatOptions options = OpenAiChatOptions.builder()
                    .withModel(chatModelEnum)
                    .withMaxTokens(MAX_TOKENS)
                    .withTemperature(TEMPERATURE)
                    .withLogprobs(true) // Enable log probabilities
                    .build();
            OpenAiChatModel openAiChatModel = new OpenAiChatModel(new OpenAiApi(opeApiParams.getOpenAIKey()), options);
            openAiChatModelMap.put(chatModelEnum, openAiChatModel);
        }
    }

    public ChatClient createChatClient(OpenAiApi.ChatModel chatModel) {
        return clientMap.get(chatModel);
    }

    public OpenAiChatModel createChatModel(OpenAiApi.ChatModel chatModel) {
        return openAiChatModelMap.get(chatModel);
    }

    public OpenAiImageModel createImageModel() {
        // model with longer timeout restClient
        OpenAiImageApi openAiImageApi = new OpenAiImageApi("https://api.openai.com", opeApiParams.getOpenAIKey(), restClientBuilder);
        OpenAiImageModel openAiImageModel = new OpenAiImageModel(openAiImageApi);
        return openAiImageModel;
    }

    public OpenAiAudioSpeechModel createAudioSpeechModel() {
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
                .withSpeed(1.0f)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1_HD.value)
                .build();
        OpenAiAudioApi openAiAudioApi = new OpenAiAudioApi(opeApiParams.getOpenAIKey());
        return new OpenAiAudioSpeechModel(openAiAudioApi,options);
    }
}
