package com.emat.aatranscript_opeinai_app.vision_artist.model;

import org.springframework.web.multipart.MultipartFile;

public record VisionSpeechRequest (MultipartFile file, String question, VisionLanguageEnum language) {

}
