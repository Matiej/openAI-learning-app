package com.emat.aatranscript_opeinai_app.lector.services;

import com.emat.aatranscript_opeinai_app.lector.model.LectorRequest;
import com.emat.aatranscript_opeinai_app.lector.model.LectorResponse;

public interface LectorService {

    LectorResponse readText(LectorRequest lectorRequest);
}
