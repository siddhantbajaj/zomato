package com.example.siddhant.zomato1;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ABC on 04-07-2016.
 */
 public  class CNclient {
    public static CNInterface service;

    public static CNInterface getService() {
        if(service==null)
        {
           service= new Retrofit.Builder().baseUrl("https://developers.zomato.com").addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create())).build().create(CNInterface.class);
        }
        return service;
    }
}
