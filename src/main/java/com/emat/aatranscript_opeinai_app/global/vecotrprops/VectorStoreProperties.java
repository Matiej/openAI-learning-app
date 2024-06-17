package com.emat.aatranscript_opeinai_app.global.vecotrprops;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sfg.aiapp")
public class VectorStoreProperties {

    private String simpleVectorStorePath;
    private DocumentsToLoad documentsToLoad;


    public String getSimpleVectorStorePath() {
        return simpleVectorStorePath;
    }

    public void setSimpleVectorStorePath(String simpleVectorStorePath) {
        this.simpleVectorStorePath = simpleVectorStorePath;
    }

    public DocumentsToLoad getDocumentsToLoad() {
        return documentsToLoad;
    }

    public void setDocumentsToLoad(DocumentsToLoad documentsToLoad) {
        this.documentsToLoad = documentsToLoad;
    }
}
