package com.emat.aatranscript_opeinai_app.modelpromptengineering;

import com.emat.aatranscript_opeinai_app.ConfigTestClass;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")

@ContextConfiguration(classes = ConfigTestClass.class)
public class BaseTest {
    @Autowired
    OpenAiChatModel openAiChatClient;

    String chat(String prompt) {
        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        Prompt promptToSend = promptTemplate.create();

        return openAiChatClient.call(promptToSend).getResult().getOutput().getContent();
    }
}
