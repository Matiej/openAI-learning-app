package com.emat.aatranscript_opeinai_app.truckadvisor.services;

import com.emat.aatranscript_opeinai_app.global.OpenAiClientFactory;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvAnswer;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvQuestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TruckAdvServiceImpl implements TruckAdvService {
    private final String LANGUAGE_PROMPT = "answer using the same language in which the question was asked.";
    private final OpenAiClientFactory openAiClientFactory;
    private final VectorStore vectorStore;

    @Value("classpath:templates/rag-truck-advisor-prompt.st")
    private Resource truckAdvisorPrompt;
    @Value("classpath:templates/rag-truckadvisor-system-prompt.st")
    private Resource truckAdvisorSystemPrompt;
    @Value("classpath:templates/rag-truckadvisor-user-prompt.st")
    private Resource truckAdvisorUserPrompt;

    @Override
    public TruckAdvAnswer getBasicAdvice(TruckAdvQuestion question) {
        ChatClient client = openAiClientFactory.createChatClient(OpenAiApi.ChatModel.GPT_3_5_TURBO);
        log.info("Asking truck advisor question: {}, using model: {}", question.question(), OpenAiApi.ChatModel.GPT_3_5_TURBO);
        PromptTemplate userPromptTemplate = new PromptTemplate(truckAdvisorPrompt);
        Prompt userPrompt = userPromptTemplate.create(Map.of("input", question.question()));
        ChatResponse chatResponse = client.prompt()
                .system(LANGUAGE_PROMPT)
                .messages(userPrompt.getInstructions())
                .call()
                .chatResponse();
        String chatOutput = chatResponse.getResult().getOutput().getContent();
        log.info("Received response from OpenAI: {}", chatOutput);
        return new TruckAdvAnswer(chatOutput);
    }

    @Override
    public TruckAdvAnswer getLowestPriceTruckAdvice(TruckAdvQuestion question) {
        OpenAiApi.ChatModel gpt4 = OpenAiApi.ChatModel.GPT_4;
        ChatClient client = openAiClientFactory.createChatClient(gpt4);
        log.info("Asking truck advisor lowest price truck question: {}, using model: {}", question.question(), gpt4);

        Message systemMessage = new SystemPromptTemplate(truckAdvisorSystemPrompt).createMessage();

        PromptTemplate truckAdvisorUserPromptTemplate = new PromptTemplate(truckAdvisorUserPrompt);
        Message userMessage = truckAdvisorUserPromptTemplate
                .createMessage(Map.of("input", question.question(),
                        "documents", String.join("\n", getContentFromVector(question.question()))));

        ChatResponse chatResponse = client.prompt()
                .system(LANGUAGE_PROMPT)
                .messages(List.of(systemMessage, userMessage))
                .call()
                .chatResponse();
        String chatOutput = chatResponse.getResult().getOutput().getContent();
        log.info("Received response from OpenAI: {}", chatOutput);
        return new TruckAdvAnswer(chatOutput);
    }

    private List<String> getContentFromVector(String question) {
        List<Document> documentList = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(4));
        return documentList.stream().map(Document::getContent).toList();
    }
}
