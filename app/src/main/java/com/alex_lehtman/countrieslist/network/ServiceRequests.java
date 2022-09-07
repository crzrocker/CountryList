package com.alex_lehtman.countrieslist.network;

import com.alex_lehtman.countrieslist.model.Country;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceRequests {

    @GET("v3.1/all")
    Call<ArrayList<Country>> getCountries();

}
