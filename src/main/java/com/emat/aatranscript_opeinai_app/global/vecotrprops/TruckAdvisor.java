package com.emat.aatranscript_opeinai_app.global.vecotrprops;

import org.springframework.core.io.Resource;

import java.util.List;

public class TruckAdvisor {
    private List<Resource> filesToLoad;
    private List<String> pathsToLoad;

    public List<Resource> getFilesToLoad() {
        return filesToLoad;
    }

    public void setFilesToLoad(List<Resource> filesToLoad) {
        this.filesToLoad = filesToLoad;
    }

    public List<String> getPathsToLoad() {
        return pathsToLoad;
    }

    public void setPathsToLoad(List<String> pathsToLoad) {
        this.pathsToLoad = pathsToLoad;
    }
}
