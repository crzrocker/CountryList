package com.alex_lehtman.countrieslist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alex_lehtman.countrieslist.model.Country;
import com.alex_lehtman.countrieslist.mvvm.DataRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Country mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int countryPosition = getIntent().getExtras().getInt(MainActivity.COUNTRY_INDEX);

        getCountriesFromPrefs(countryPosition);

        setCountryDetails();

    }

    private void setCountryDetails() {
        TextView tvRegion = (TextView) findViewById(R.id.detailsRegion);
        TextView tvSubregion = (TextView) findViewById(R.id.detailsSubregion);
        TextView tvLanguages = (TextView) findViewById(R.id.detailsLanguages);
        Button showOnMap = (Button) findViewById(R.id.detailsShowOnMap);

        tvRegion.setText(mCountry.getRegion());
        tvSubregion.setText(mCountry.getSubregion());

        String languages = "";
        for (String value : mCountry.getLanguages().values()) {
            languages += value + "\n";
        }
        tvLanguages.setText(languages);

        showOnMap.setOnClickListener(view -> {
            String lat = mCountry.getLatlan().get(0).toString();
            String lng = mCountry.getLatlan().get(1).toString();
            Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lng + "?z=6");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            startActivity(mapIntent);
        });
    }

    private void getCountriesFromPrefs(int position) {
        SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(this);
        String json = pm.getString(DataRepository.COUNTRY_LIST, "");
        Gson gson = new Gson();
        ArrayList<Country> countries = gson.fromJson(json,new TypeToken<List<Country>>(){}.getType());
        mCountry = countries.get(position);
    }


}
