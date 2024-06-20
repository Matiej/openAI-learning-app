package com.emat.aatranscript_opeinai_app.vision_artist.services;

import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionRequest;
import com.emat.aatranscript_opeinai_app.vision_artist.model.VisionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VisionService {
    VisionResponse getImageDescription(VisionRequest request) throws IOException;
}
