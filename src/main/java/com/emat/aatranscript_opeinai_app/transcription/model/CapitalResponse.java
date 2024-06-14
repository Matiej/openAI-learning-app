package com.emat.aatranscript_opeinai_app.model;

public class CapitalResponse {
    private String capital;
    private String country;

    public CapitalResponse(String capital, String country) {
        this.capital = capital;
        this.country = country;
    }

    public CapitalResponse() {
    }

    public String getCapital() {
        return capital;
    }

    public String getCountry() {
        return country;
    }
}

