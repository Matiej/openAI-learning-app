package com.emat.aatranscript_opeinai_app.vision_artist.model;

public enum VisionLanguageEnum {

    PL("You are a Polish speaker. Please communicate in Polish for all responses."),
    EN("You are an English speaker. Please communicate in English for all responses."),
    DE("You are an German speaker. Please communicate in English for all responses.");

    private final String languagePrompt;

    VisionLanguageEnum(String languagePrompt) {
        this.languagePrompt = languagePrompt;
    }

    public String getLanguagePrompt() {
        return languagePrompt;
    }

    public static boolean isValidLanguage(String language) {
        for (VisionLanguageEnum lang : values()) {
            if (lang.name().equalsIgnoreCase(language)) {
                return true;
            }
        }
        return false;
    }
}
