package com.emat.aatranscript_opeinai_app.lector.services;

import com.emat.aatranscript_opeinai_app.global.OpenAiClientFactory;
import com.emat.aatranscript_opeinai_app.lector.model.LectorRequest;
import com.emat.aatranscript_opeinai_app.lector.model.LectorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectorServiceImpl implements LectorService {
    private final OpenAiClientFactory openAiClientFactory;

    @Override
    public LectorResponse readText(LectorRequest lectorRequest) {
        SpeechPrompt speechPrompt = new SpeechPrompt(lectorRequest.textToRead());
        byte[] speechAudio = openAiClientFactory.createAudioSpeechModel().call(speechPrompt).getResult().getOutput();
        return new LectorResponse(speechAudio);
    }
}
