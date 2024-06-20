package com.emat.aatranscript_opeinai_app.vision_artist.model;

import org.springframework.web.multipart.MultipartFile;

public record VisionRequest (String question, MultipartFile file) {
}
