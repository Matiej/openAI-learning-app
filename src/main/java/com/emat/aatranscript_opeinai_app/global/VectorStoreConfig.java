package com.emat.aatranscript_opeinai_app.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.embedding.EmbeddingModel;

import java.io.File;
import java.util.List;


@Slf4j
@Configuration
public class VectorStoreConfig {

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingmodel, VectorStoreProperties vectorStoreProperties) {
        var store = new SimpleVectorStore(embeddingmodel);
        File file = new File(vectorStoreProperties.getVectorStorePath());
        if(file.exists()) {
            store.load(file);
            log.info("Loaded vector store from file: {} ", file.getAbsolutePath());
        } else {
            log.debug("Loading documents into vector store");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.debug("Loading document: {}", document.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documentList = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(documentList);
                store.add(splitDocs);
            });

            store.save(file);
        }

        return store;
    }
}
