package com.alex_lehtman.countrieslist.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alex_lehtman.countrieslist.model.Country;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    DataRepository repo = new DataRepository();
    LiveData<ArrayList<Country>> countries = repo.getCountriesFromApi();


    public LiveData<ArrayList<Country>> getCountries() {
        repo.getCountriesFromApi();
        return countries;
    }
}
