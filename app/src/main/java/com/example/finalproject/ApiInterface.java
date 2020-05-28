package com.example.finalproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ApiInterface {

//    making the get request
    @GET("countries/")

    Call<List<ApiModel>>getcountry();

    @GET("countries/{q}")
    Call<ApiModel> getSpecificData(
            @Path("q") String query
    );


}
