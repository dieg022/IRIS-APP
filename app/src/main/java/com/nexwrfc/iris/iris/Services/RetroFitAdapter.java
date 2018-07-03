package com.nexwrfc.iris.iris.Services;

import android.app.AlertDialog;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexwrfc.iris.iris.ClasesGenericas.DialogoError;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitAdapter {
   //private final String servidor = "http://10.0.2.2:8080/";
    //private final String servidor = "http://89.140.16.113:8080/";
    private final String servidor = "http://iris.internetinfraestructuras.es:8080/iris/";
    public Retrofit getAdaptador() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(servidor)
                    .addConverterFactory(GsonConverterFactory.create(gsonAdapter()))
                    .build();

        return retrofit;
    }


    public Gson gsonAdapter() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return gson;
    }
}

