package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class News extends AppCompatActivity {


    RecyclerView rv;
    String url = "https://nepalcorona.info/api/v1/";
    private adapterNews adapterClas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        rv = findViewById(R.id.rv_news);

        rv.setLayoutManager(new LinearLayoutManager(this));
        getResponse();

    }

    // method to retrieve the data from api

    private void getResponse() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        newsinter apiInterface = retrofit.create(newsinter.class);


        Call<News2Model> call;

        call = apiInterface.getnews();

        call.enqueue(new Callback<News2Model>() {
            @Override
            public void onResponse(Call<News2Model> call, retrofit2.Response<News2Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    News2Model news2Model = response.body();
                    adapterClas = new adapterNews(getApplicationContext(), news2Model);
                    rv.setAdapter(adapterClas);
                }
            }


            @Override
            public void onFailure(Call<News2Model> call, Throwable t) {
                Toast.makeText(News.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
