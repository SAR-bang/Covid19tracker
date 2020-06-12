package com.example.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class statisticsfragment extends Fragment {


    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etQuery;
    Button btnsearch, btnAboutUs;
    //    Dialog dialog;
    AdapterClas adapterClas;
    List<ApiModel> apiModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewstat = inflater.inflate(R.layout.stat_fragment,container , false);

        swipeRefreshLayout =viewstat.findViewById(R.id.swipeRefresh);

        rv = viewstat.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(viewstat.getContext()));
        etQuery = viewstat.findViewById(R.id.etQuery);
        btnsearch = viewstat.findViewById(R.id.btnSearch);

        btnAboutUs = viewstat.findViewById(R.id.aboutUs);
        //dialog = new Dialog(MainActivity.this);

        //
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponse("");
            }


        });
        // GETTING response retrieves response into list and attach into rv
        getResponse("");
        // action listener in search btn

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuery.getText().toString().equals("")) {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            getResponse(etQuery.getText().toString());
                        }
                    });
                    getResponse(etQuery.getText().toString());
                } else {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            getResponse("");
                        }
                    });
                    getResponse("");
                }
            }
        });



        return viewstat;
    }

    private void getResponse(String query) {
        swipeRefreshLayout.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://corona.lmao.ninja/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Call<List<ApiModel>> call;

        Call<ApiModel> call2;

        if (query.equals("")) {

            call = apiInterface.getcountry();

            call.enqueue(new Callback<List<ApiModel>>() {
                @Override
                public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        apiModelList.clear();

                        apiModelList = new ArrayList<>(response.body());
                        adapterClas = new AdapterClas(getContext(), apiModelList);
                        rv.setAdapter(adapterClas);
                    }
                }


                @Override
                public void onFailure(Call<List<ApiModel>> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Internet connection Required", Toast.LENGTH_LONG).show();
                }
            });
        } else  {

            //
            call2 = apiInterface.getSpecificData(query);
            call2.enqueue(new Callback<ApiModel>() {
                @Override
                public void onResponse(Call<ApiModel> call2, Response<ApiModel> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.body() != null) {
                        ApiModel apimodel = response.body();
                        adapterClas = new AdapterClas(getContext(), apimodel);
                        rv.setAdapter(adapterClas);
                    }
                }

                @Override
                public void onFailure(Call<ApiModel> call2, Throwable t) {

                }
            });
            //


        }



    }


}
