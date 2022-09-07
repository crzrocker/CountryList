package com.alex_lehtman.countrieslist.mvvm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alex_lehtman.countrieslist.application.MyApplication;
import com.alex_lehtman.countrieslist.model.Country;
import com.alex_lehtman.countrieslist.network.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    public static final String COUNTRY_LIST = "COUNTRY_LIST";
    private MutableLiveData<ArrayList<Country>> mCountriesResponse;

    public DataRepository() {
        mCountriesResponse = new MutableLiveData<>();
    }

    //fetch data from API.
    //if completed successfully- save the data locally
    //otherwise, get saved data and notify listeners
    public LiveData<ArrayList<Country>> getCountriesFromApi() {

        RequestManager.getInstance().getCountries(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                if (response.body() == null || !response.isSuccessful()){
                    onFailure(call, new Throwable("Something went wrong"));
                    return;
                }

                saveCountriesToStorage();

                mCountriesResponse.setValue(response.body());
                mCountriesResponse.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                getCountriesFromPrefs();
            }
        });
        return  mCountriesResponse;
    }


    //save data locally.
    private void saveCountriesToStorage() {
        Gson gson = new Gson();
        String json = gson.toJson(mCountriesResponse.getValue());

        SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = pm.edit();
        editor.putString(COUNTRY_LIST, json);
        editor.apply();
    }

    //fetch saved data
    private void getCountriesFromPrefs() {
        SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        String json = pm.getString(COUNTRY_LIST, "");
        Gson gson = new Gson();
        ArrayList<Country> response = gson.fromJson(json, new TypeToken<List<Country>>() {}.getType());

        mCountriesResponse.setValue(response);
        mCountriesResponse.postValue(response);
    }
}
