package com.emat.aatranscript_opeinai_app.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Bean
    public OpeApiParams getOpeApiParams() {
        return new OpeApiParams(apiKey);
    }

}
