package com.alex_lehtman.countrieslist.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class Country {

    @SerializedName("name")
    CountryName name;

    @SerializedName("capital")
    ArrayList<String> capital;

    @SerializedName("region")
    String region;

    @SerializedName("subregion")
    String subregion;

    @SerializedName("population")
    String population;

    @SerializedName("flags")
    FlagUrl flag;

    @SerializedName("languages")
    HashMap<String, String> languages;

    @SerializedName("latlng")
    ArrayList<Double> latlan;

    @SerializedName("maps")
    CountryMap map;

    public CountryName getName() {
        return name;
    }

    public ArrayList<String> getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getPopulation() {
        return population;
    }

    public FlagUrl getFlag() {
        return flag;
    }

    public HashMap<String, String> getLanguages() {
        return languages;
    }

    public ArrayList<Double> getLatlan() {
        return latlan;
    }

    public CountryMap getMap() {
        return map;
    }
}
