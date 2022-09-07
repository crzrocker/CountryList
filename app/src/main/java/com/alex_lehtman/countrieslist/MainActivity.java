package com.alex_lehtman.countrieslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;

import com.alex_lehtman.countrieslist.adapters.CountriesAdapter;
import com.alex_lehtman.countrieslist.model.Country;
import com.alex_lehtman.countrieslist.mvvm.MainViewModel;
import com.alex_lehtman.countrieslist.network.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String COUNTRY_INDEX = "COUNTRY_INDEX";
    private ArrayList<Country> mCountries;
    private MainViewModel mainViewModel;
    private ProgressBar mProgressBar;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setDataModel();
    }

    //setup the viewModel and fetch data
    private void setDataModel() {
        mProgressBar.setVisibility(View.VISIBLE);
        mainViewModel = new ViewModelProvider(MainActivity.this).get(MainViewModel.class);
        mainViewModel.getCountries().observe(this, countries -> {
            mCountries = countries;
            displayData();
            mProgressBar.setVisibility(View.INVISIBLE);
        });
    }

    //find views
    private void initViews() {
        mProgressBar = findViewById(R.id.main_progress_bar);
        mRecycler = findViewById(R.id.main_rv);
        displayData();
    }

    //display data in the recycler view
    private void displayData() {
        CountriesAdapter adapter = new CountriesAdapter(this, mCountries);
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(COUNTRY_INDEX, position);
            startActivity(intent);
        });
    }

    //clean context before when destroying the activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.getCountries().removeObservers(MainActivity.this);
    }
}