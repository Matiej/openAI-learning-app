package com.emat.aatranscript_opeinai_app.image_artist.services;

import com.emat.aatranscript_opeinai_app.global.OpenAiClientFactory;
import com.emat.aatranscript_opeinai_app.image_artist.model.ImageArtistRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
class ImageArtistServiceImpl implements ImageArtistService {

    private final OpenAiClientFactory openAiClientFactory;

    @Override
    public byte[] generateImage(ImageArtistRequest request) {
        var imageOptions = OpenAiImageOptions.builder()
                .withHeight(1024)
                .withWidth(1024)
                .withResponseFormat("b64_json")
                .withQuality("hd")
                .withStyle("natural")
                
                .withModel(OpenAiImageApi.ImageModel.DALL_E_3.getValue())
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(request.imagePrompt(), imageOptions);
        var imageResponse = openAiClientFactory.createImageModel()
                .call(imagePrompt);
        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }
}
