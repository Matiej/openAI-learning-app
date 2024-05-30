package com.emat.aatranscript_opeinai_app.services;

import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.Question;

public interface TranscriptionOpenAiService {
    Answer getAnswer(Question question);
    Answer getCapital(Question country);
}
