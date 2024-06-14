package com.emat.aatranscript_opeinai_app.transcription.services;

import com.emat.aatranscript_opeinai_app.transcription.model.Answer;
import com.emat.aatranscript_opeinai_app.transcription.model.CapitalDetailsResponse;
import com.emat.aatranscript_opeinai_app.transcription.model.CapitalResponse;
import com.emat.aatranscript_opeinai_app.transcription.model.Question;

public interface TranscriptionOpenAiService {
    Answer getAnswer(Question question);
    CapitalResponse getCapital(Question country);

    CapitalDetailsResponse getCapitalWithDetails(Question question);

    Answer getAnswerWithVectorStore(Question question);
}
