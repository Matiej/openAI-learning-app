package com.emat.aatranscript_opeinai_app.model;

public class CapitalDetailsResponse extends CapitalResponse {
    private String currency;
    private String countryPopulation;
    private String capitalPopulation;
    private String language;

    public CapitalDetailsResponse() {
    }

    public CapitalDetailsResponse(String capital, String country, String currency, String countryPopulation, String capitalPopulation, String language) {
        super(capital, country);
        this.currency = currency;
        this.countryPopulation = countryPopulation;
        this.capitalPopulation = capitalPopulation;
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountryPopulation() {
        return countryPopulation;
    }

    public String getLanguage() {
        return language;
    }

    public String getCapitalPopulation() {
        return capitalPopulation;
    }
}
