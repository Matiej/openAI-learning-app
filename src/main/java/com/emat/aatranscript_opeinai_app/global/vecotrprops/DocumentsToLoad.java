package com.emat.aatranscript_opeinai_app.global.vecotrprops;

import org.springframework.core.io.Resource;

public class DocumentsToLoad {
    private Resource moviesDataFile;
    private TruckAdvisor truckAdvisor;

    public Resource getMoviesDataFile() {
        return moviesDataFile;
    }

    public void setMoviesDataFile(Resource moviesDataFile) {
        this.moviesDataFile = moviesDataFile;
    }

    public TruckAdvisor getTruckAdvisor() {
        return truckAdvisor;
    }

    public void setTruckAdvisor(TruckAdvisor truckAdvisor) {
        this.truckAdvisor = truckAdvisor;
    }
}
