package com.emat.aatranscript_opeinai_app.weather.services;

import com.emat.aatranscript_opeinai_app.transcription.services.ChatClientFactory;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherAnswer;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherRequest;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherResponse;
import com.emat.aatranscript_opeinai_app.weather.ninja_weather_api.NinjaWeatherApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherAIServiceImpl implements WeatherAIService {
    private final String LANGUAGE_PROMPT = "answer using the same language in which the question was asked. Use only Polish or English, by default the answer will be in English.";
    private final ChatClientFactory chatClientFactory;
    private final NinjaWeatherApiService ninjaWeatherApiService;

    @Value("classpath:templates/weather-user-prompt.st")
    private Resource weatherUserPromptResource;

    @Override
    public WeatherAnswer getWeatherBasicAnswer(String city) {
        OpenAiApi.ChatModel gpt4 = OpenAiApi.ChatModel.GPT_4;
        ChatClient client = chatClientFactory.createClient(gpt4);
        log.info("Asking weather for the city: {}, using model: {}",city, gpt4);
        PromptTemplate userPromptTemplate = new PromptTemplate(weatherUserPromptResource);
        Prompt userPrompt = userPromptTemplate.create(Map.of("city", city));

        WeatherResponse weatherFromApi = getWeatherFromApi(new WeatherRequest(city, null, null));

//        ChatResponse chatResponse = client.prompt()
//                .system(LANGUAGE_PROMPT)
//                .messages(userPrompt.getInstructions())
//                .call()
//                .chatResponse();
//        String chatOutput = chatResponse.getResult().getOutput().getContent();
//        log.info("Received response from OpenAI: {}", chatOutput);
//        return new WeatherAnswer(chatOutput);
        return new WeatherAnswer("Temporary answer from WeatherAIServiceImpl.");
    }

    private WeatherResponse getWeatherFromApi(WeatherRequest request) {
        WeatherResponse weatherResponse = ninjaWeatherApiService.getWeather(request);
        log.info("Received weather from Ninja API: {} for the city: {}", weatherResponse, request.location());
        return weatherResponse;
    }
}
