package com.emat.aatranscript_opeinai_app.image_artist.services;

import com.emat.aatranscript_opeinai_app.image_artist.model.ImageArtistRequest;

public interface ImageArtistService {

    byte[] generateImage(ImageArtistRequest request);

}
