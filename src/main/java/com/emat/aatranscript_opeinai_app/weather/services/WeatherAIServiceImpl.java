package com.emat.aatranscript_opeinai_app.weather.services;

import com.emat.aatranscript_opeinai_app.global.OpenAiClientFactory;
import com.emat.aatranscript_opeinai_app.weather.function.WeatherServiceFunction;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherAnswer;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherRequest;
import com.emat.aatranscript_opeinai_app.weather.model.WeatherResponse;
import com.emat.aatranscript_opeinai_app.weather.ninja_weather_api.NinjaWeatherApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherAIServiceImpl implements WeatherAIService {
    private final OpenAiClientFactory openAiClientFactory;
    private final NinjaWeatherApiService ninjaWeatherApiService;

    @Value("classpath:templates/weather-user-prompt.st")
    private Resource weatherUserPromptResource;

    @Value("classpath:templates/weather-system-prompt.st")
    private Resource systemPromptResource;

    @Override
    public WeatherAnswer getWeatherBasicAnswer(String city) {
        OpenAiApi.ChatModel gpt4 = OpenAiApi.ChatModel.GPT_4;
        ChatClient client = openAiClientFactory.createClient(gpt4);
        log.info("Asking weather for the city: {}, using model: {}", city, gpt4);

        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new WeatherServiceFunction(ninjaWeatherApiService))
                        .withName("CurrentWeather")
                        .withDescription("Get the current weather for a location")
                        .withResponseConverter((response) -> {
                            String jsonSchema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                            String jsonString = ModelOptionsUtils.toJsonString(response);
                            return jsonSchema + "\n" + jsonString;
                        })
                        .build()))
                .build();
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemPromptTemplate(systemPromptResource).createMessage());
        messages.addAll(new ArrayList<>(new PromptTemplate(weatherUserPromptResource).create(Map.of("city", city)).getInstructions()));

        Prompt prompt = new Prompt(messages, promptOptions);

        ChatResponse chatResponse = client.prompt(prompt)
                .call().chatResponse();
        String chatOutput = chatResponse.getResult().getOutput().getContent();
        log.info("Received chat response: {}", chatOutput);
        return new WeatherAnswer(chatOutput);
    }

    private WeatherResponse getWeatherFromApi(WeatherRequest request) {
        WeatherServiceFunction weatherServiceFunction = new WeatherServiceFunction(ninjaWeatherApiService);
        WeatherResponse response = weatherServiceFunction.apply(request);
        log.info("Received weather from Ninja API: {} for the city: {}", response, request.location());
        return response;
    }
}
