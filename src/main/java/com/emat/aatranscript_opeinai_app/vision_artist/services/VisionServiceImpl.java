package com.emat.aatranscript_opeinai_app.vision_artist.services;

import com.emat.aatranscript_opeinai_app.global.OpenAiClientFactory;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionRequest;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.framework.qual.StubFiles;
import org.springframework.ai.chat.messages.Media;
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

    @Override
    public VisionResponse getImageDescription(VisionRequest request) throws IOException {
        OpenAiChatModel chatModel = openAiClientFactory.createChatModel(OpenAiApi.ChatModel.GPT_4_O);

        UserMessage userMessage = new UserMessage(request.question(),
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG, request.file().getBytes())));

        String result = chatModel.call(new Prompt(List.of(userMessage))).getResult().getOutput().getContent();
        return new VisionResponse(result);
    }
}
