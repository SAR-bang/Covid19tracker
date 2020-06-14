package com.example.finalproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface newsinter {
    //    making the get request

    @GET("news/")
    Call<News2Model> getnews();
}
