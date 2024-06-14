package com.emat.aatranscript_opeinai_app.services;

import com.emat.aatranscript_opeinai_app.model.Answer;
import com.emat.aatranscript_opeinai_app.model.CapitalDetailsResponse;
import com.emat.aatranscript_opeinai_app.model.CapitalResponse;
import com.emat.aatranscript_opeinai_app.model.Question;

public interface TranscriptionOpenAiService {
    Answer getAnswer(Question question);
    CapitalResponse getCapital(Question country);

    CapitalDetailsResponse getCapitalWithDetails(Question question);

    Answer getAnswerWithVectorStore(Question question);
}
