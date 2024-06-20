package com.emat.aatranscript_opeinai_app.lector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class LectorRequest {

    private String textToRead;
    private LectorLanguage language;
}
