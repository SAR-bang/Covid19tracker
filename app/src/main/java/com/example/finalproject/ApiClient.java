package com.example.finalproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://corona.lmao.ninja/v2/";
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    private ApiClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiInterface getInstance(){
        if (apiClient == null){
            apiClient = new ApiClient();
        }
        ApiInterface api = retrofit.create(ApiInterface.class);
        return api;
    }

}
