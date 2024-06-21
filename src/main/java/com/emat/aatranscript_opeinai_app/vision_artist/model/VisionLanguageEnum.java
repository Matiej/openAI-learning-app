package com.emat.aatranscript_opeinai_app.vision_artist.model;

public enum VisionLanguageEnum {

    PL("You are a translator. Please translate the following text to Polish"),
    EN("You are a translator. Please translate the following text to English."),
    DE("You are a translator. Please translate the following text to German.");


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
