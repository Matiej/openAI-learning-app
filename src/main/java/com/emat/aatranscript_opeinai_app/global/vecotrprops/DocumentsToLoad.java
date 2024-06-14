package com.emat.aatranscript_opeinai_app.global.vecotrprops;

import org.springframework.core.io.Resource;

public class DocumentsToLoad {
    private Resource transcript;
    private TruckAdvisor truckadvisor;

    public Resource getTranscript() {
        return transcript;
    }

    public void setTranscript(Resource transcript) {
        this.transcript = transcript;
    }

    public TruckAdvisor getTruckadvisor() {
        return truckadvisor;
    }

    public void setTruckadvisor(TruckAdvisor truckadvisor) {
        this.truckadvisor = truckadvisor;
    }
}
