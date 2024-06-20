package com.emat.aatranscript_opeinai_app.lector.model;

public enum LectorLanguage {

    PL("You are a Polish speaker. Please communicate in Polish for all responses."),
    EN("You are an English speaker. Please communicate in English for all responses.");

    private final String languagePrompt;

    LectorLanguage(String languagePrompt) {
        this.languagePrompt = languagePrompt;
    }

    public String getLanguagePrompt() {
        return languagePrompt;
    }

    public static boolean isValidLanguage(String language) {
        for (LectorLanguage lang : values()) {
            if (lang.name().equalsIgnoreCase(language)) {
                return true;
            }
        }
        return false;
    }
}
