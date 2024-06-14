package com.emat.aatranscript_opeinai_app.model;

import com.emat.aatranscript_opeinai_app.controllers.RestCapitalRequest;
import com.emat.aatranscript_opeinai_app.controllers.RestOpenAiRequest;

public class Question {
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question(String question) {
        this.question = question;
    }

    public static Question fromRestOpenAiRequest(RestOpenAiRequest restOpenAiRequest) {
        String question1 = restOpenAiRequest.question();
        return new Question(question1);
    }

    public static Question fromRestCapitalRequest(RestCapitalRequest restCapitalRequest) {
        String question1 = restCapitalRequest.countryName();
        return new Question(question1);
    }
}
