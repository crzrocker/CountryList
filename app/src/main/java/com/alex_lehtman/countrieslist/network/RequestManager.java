package com.alex_lehtman.countrieslist.network;

import com.alex_lehtman.countrieslist.model.Country;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {
    private static RequestManager msInstance;
    private Retrofit mRetrofit;
    private OkHttpClient okHttpClient;

    public static RequestManager getInstance() {
        if (msInstance == null) {
            msInstance = new RequestManager();
        }
        return msInstance;
    }

    private ServiceRequests getRetroFitServiceRequests(String url) {

        ConnectionPool pool = new ConnectionPool(3, 8, TimeUnit.SECONDS);

        if (mRetrofit == null || !mRetrofit.baseUrl().toString().equals(url)) {

            if (okHttpClient == null) {

                Dispatcher dispatcher = new okhttp3.Dispatcher();
                dispatcher.setMaxRequests(1);
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                okHttpClient = new OkHttpClient.Builder()
                        .connectionPool(pool)
                        .addInterceptor(interceptor).dispatcher(dispatcher)
                        .build();
            }
            mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).client(okHttpClient).build();
        }
        return mRetrofit.create(ServiceRequests.class);
    }




    public void getCountries(Callback<ArrayList<Country>> callback) {
        getRetroFitServiceRequests("https://restcountries.com/").getCountries().enqueue(callback);
    }


}
