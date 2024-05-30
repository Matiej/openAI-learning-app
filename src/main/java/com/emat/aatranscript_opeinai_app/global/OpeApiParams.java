package com.emat.aatranscript_opeinai_app.global;

public class OpeApiParams {
    private final String openApiKey;

    public OpeApiParams(String openApiKey) {
        this.openApiKey = openApiKey;
    }

    public String getOpenAIKey() {
        return this.openApiKey;
    }
}
