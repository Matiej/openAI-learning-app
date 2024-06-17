package com.emat.aatranscript_opeinai_app.bootstrap;

import com.emat.aatranscript_opeinai_app.global.vecotrprops.VectorStoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LoadVectorStore implements CommandLineRunner {

    @Autowired
    VectorStore vectorStore;
    @Autowired
    VectorStoreProperties vectorStoreProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading Vector milvus Store");
        List<Document> documents = new ArrayList<>();
        if (vectorStore.similaritySearch("Sportsman").isEmpty() && vectorStore.similaritySearch("Truck").isEmpty()){
            log.info("Vector Store is empty, loading new data");
            vectorStoreProperties.getDocumentsToLoad().getTruckAdvisor().getFilesToLoad().forEach(document -> {
                log.info("Loading document: {}", document.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documentToSplitList = documentReader.get();
                TextSplitter splitter = new TokenTextSplitter();
                documents.addAll(splitter.apply(documentToSplitList));

            });
            vectorStoreProperties.getDocumentsToLoad().getTruckAdvisor().getPathsToLoad().forEach(path -> {
                log.info("Loading url document: {}", path);
                TikaDocumentReader documentReader = new TikaDocumentReader(path);
                List<Document> documentToSplitList = documentReader.get();
                TextSplitter splitter = new TokenTextSplitter();

                documents.addAll(splitter.apply(documentToSplitList));

            });
            if (!documents.isEmpty()) {
                vectorStore.add(documents);
                log.info("Loaded {} documents into Vector Store", documents.size());
            } else {
                log.info("No documents loaded into Vector Store");
            }

        } else {
            log.info("Vector Store is not empty, skipping loading data");
        }
//        List<String> sportsman = vectorStore.similaritySearch("Sportsman").stream().map(Document::getId).toList();
//        Optional<Boolean> delete = vectorStore.delete(sportsman);
        int sportsman1 = vectorStore.similaritySearch("Sportsman").size();

        log.info("Number of documents with id: {} in Vector Store", sportsman1);
    }

}
