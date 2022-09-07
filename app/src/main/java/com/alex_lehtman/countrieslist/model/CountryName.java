package com.alex_lehtman.countrieslist.model;

import com.google.gson.annotations.SerializedName;

public class CountryName {

    @SerializedName("official")
    String officialName;
    @SerializedName("common")
    String commonName;

    public String getOfficialName() {
        return officialName;
    }

    public String getCommonName() {
        return commonName;
    }
}
