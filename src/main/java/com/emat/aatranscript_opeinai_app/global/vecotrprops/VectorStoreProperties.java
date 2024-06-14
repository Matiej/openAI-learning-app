package com.emat.aatranscript_opeinai_app.global.vecotrprops;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sfg.aiapp")
public class VectorStoreProperties {

    private String vectorStorePath;
    private DocumentsToLoad documentsToLoad;


    public String getVectorStorePath() {
        return vectorStorePath;
    }

    public void setVectorStorePath(String vectorStorePath) {
        this.vectorStorePath = vectorStorePath;
    }

    public DocumentsToLoad getDocumentsToLoad() {
        return documentsToLoad;
    }

    public void setDocumentsToLoad(DocumentsToLoad documentsToLoad) {
        this.documentsToLoad = documentsToLoad;
    }
}
