package com.example.finalproject;

public class LocationModel {

    private String ReferenceName, District, municipality;

    public LocationModel(String id, String referenceName, String district, String municipality) {
        ReferenceName = referenceName;
        District = district;
        this.municipality = municipality;
    }

    public String getReferenceName() {
        return ReferenceName;
    }

    public String getDistrict() {
        return District;
    }

    public String getMunicipality() {
        return municipality;
    }

}
