package com.alex_lehtman.countrieslist.model;

import com.google.gson.annotations.SerializedName;

public class CountryMap {

    @SerializedName("googleMaps")
    String googleMaps;

    @SerializedName("openStreetMaps")
    String streetMaps;

}
