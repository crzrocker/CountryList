package com.alex_lehtman.countrieslist.model;

import com.google.gson.annotations.SerializedName;

public class FlagUrl {

    @SerializedName("png")
    String png;
    @SerializedName("svg")
    String svg;

    public String getPng() {
        return png;
    }

    public String getSvg() {
        return svg;
    }
}
