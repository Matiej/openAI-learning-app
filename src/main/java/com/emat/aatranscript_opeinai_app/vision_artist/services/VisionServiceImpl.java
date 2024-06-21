package com.emat.aatranscript_opeinai_app.vision_artist.services;

import com.emat.aatranscript_opeinai_app.global.OpenAiClientFactory;
import com.emat.aatranscript_opeinai_app.lector.model.LectorRequest;
import com.emat.aatranscript_opeinai_app.lector.model.LectorResponse;
import com.emat.aatranscript_opeinai_app.lector.services.LectorService;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionRequest;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionResponse;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionSpeechRequest;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionSpeechResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.checkerframework.framework.qual.StubFiles;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionServiceImpl implements VisionService {

    private final OpenAiClientFactory openAiClientFactory;
    private final LectorService lectorService;

    @Override
    public VisionResponse getImageDescription(VisionRequest request) throws IOException {
        OpenAiChatModel chatModel = openAiClientFactory.createChatModel(OpenAiApi.ChatModel.GPT_4_O);

        UserMessage userMessage = new UserMessage(request.question(),
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG, request.file().getBytes())));

        String result = chatModel.call(new Prompt(List.of(userMessage))).getResult().getOutput().getContent();
        return new VisionResponse(result);
    }

    @Override
    public VisionSpeechResponse getImageDescAndSpeech(VisionSpeechRequest request) throws IOException {
        OpenAiApi.ChatModel gpt4O = OpenAiApi.ChatModel.GPT_4_O;
        OpenAiChatModel chatModel = openAiClientFactory.createChatModel(gpt4O);
        log.info("Received request to get vision of picture, describe it and tell about it: {}, using chat model {}", request.file().getName(), gpt4O);

        ChatClient client = openAiClientFactory.createChatClient(OpenAiApi.ChatModel.GPT_4_O);
        SystemMessage systemMessage = new SystemMessage(request.language().getLanguagePrompt());
        UserMessage userMessage = new UserMessage(request.question());
        String translatedQuestion = client.prompt()
                .messages(List.of(systemMessage, userMessage))
                .call().chatResponse().getResult().getOutput().getContent();

        List<Message> messageList = List.of(new UserMessage(translatedQuestion,
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG, request.file().getBytes()))));

        String pictureDescription = chatModel.call(new Prompt(messageList)).getResult().getOutput().getContent();
        LectorResponse lectorResponse = lectorService.readText(new LectorRequest(pictureDescription));

        return new VisionSpeechResponse(pictureDescription, lectorResponse.speechAudio());
    }
}
