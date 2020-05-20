package com.example.finalproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class homefragment extends Fragment {

    private AdapterRV imgadapter;
    private String worldPreferences = "worldPrefs";
    private String NepalPreferences = "NepPrefs";
    private PieChart pieChart;
    private Button adhik;
    private int errorc=0;

    private ArrayList<images> imagesList = new ArrayList<>();

    // shared preferences is used to store data of the application
    private SharedPreferences sharedPreferences;
    String countryNamet = "country";
    String casesT = "Confirmed";
    String activeT = "Active";
    String recoveredT = "Recovered";
    String DeathT = "Deaths";



    String cases="";
    String active ="";
    String recovered="" ;
    String Death ="";
    TextView global;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        RequestQueue queue2 = Volley.newRequestQueue(container.getContext());

        final View view = inflater.inflate(R.layout.home_fragment, container, false);
        final TextView txtCases = view.findViewById(R.id.data_nepal);
        global = view.findViewById(R.id.caseworldwide);


        // code to return the pie chart
        pieChart = view.findViewById(R.id.chart);

        SetData(3,100);
        adhik = view.findViewById(R.id.adhik);
        adhik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.setLayoutParams(new LinearLayout.LayoutParams(view.getWidth(),view.getHeight()/2));
                pieChart.setVisibility(view.VISIBLE);

            }
        });




        String url = "https://corona.lmao.ninja/v2/countries/Nepal";
        String url2 = "https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Parsing2Clas globalClass = gson.fromJson(response, Parsing2Clas.class);
                String globalData = String.valueOf(globalClass.getCases());
                global.setText(globalData);

                sharedPreferences = view.getContext().getSharedPreferences(worldPreferences, Context.MODE_PRIVATE);
                // Saving the data in cache
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(countryNamet, "world");
                editor.putString(casesT, globalData);
                editor.commit();
//                sharedPreferences = getSharedPreferences(M)

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                SharedPreferences sp = getActivity().getSharedPreferences(worldPreferences, Context.MODE_PRIVATE);
                global.setText(sp.getString(casesT, ""));
            }
        });
        queue2.add(stringRequest2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ApiModel apiModel = gson.fromJson(response, ApiModel.class);
                cases = apiModel.getCases().toString();
                active = apiModel.getActive().toString();
                recovered = apiModel.getRecovered().toString();
                Death = apiModel.getDeaths().toString();

                txtCases.setText(cases);


                sharedPreferences = view.getContext().getSharedPreferences(NepalPreferences, Context.MODE_PRIVATE);
                // Saving the data in cache

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(countryNamet, "Nepal");
                editor.putString(casesT, cases);
                editor.putString(activeT,active);
                editor.putString(recoveredT,recovered);
                editor.putString(DeathT,Death);

                editor.commit();     //commit saves the data in the cache

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                errorc=1;
                SharedPreferences sp = getActivity().getSharedPreferences(NepalPreferences, Context.MODE_PRIVATE);
                txtCases.setText(sp.getString(casesT, ""));


            }
        });
        queue.add(stringRequest);


        // this above code is responsible to retrieve the latest data by hitting the api endpoint

        //
        /// Now creating a recycler view to show the symptoms
        final RecyclerView rv = view.findViewById(R.id.symptomsList);
        imagesList.add(new images(R.drawable.cgh, "खोकी"));
        imagesList.add(new images(R.drawable.fvr, "१०२ डिग्री माथि ताप"));

        imagesList.add(new images(R.drawable.shrtnes, "सास फेर्न गाह्रो"));
        imagesList.add(new images(R.drawable.cgh, "खोकी"));
        imagesList.add(new images(R.drawable.fvr, "१०२ डिग्री माथि ताप"));

        imagesList.add(new images(R.drawable.shrtnes, "सास फेर्न गाह्रो"));

        imgadapter = new AdapterRV(getActivity().getApplicationContext(), imagesList);

        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        view.findViewById(R.id.img_id);

        rv.setAdapter(imgadapter);
        return view;
    }


    // this method is used to showcase the piechart


    //  labels stored in array
    String valuesCache[] = {casesT,activeT,recoveredT,DeathT};
    String values[]={};

    //this sets the data into the pie chart

    private void SetData(int count, int range) {
        ArrayList<PieEntry> values = new ArrayList<>();

            sharedPreferences = getActivity().getSharedPreferences(NepalPreferences, Context.MODE_PRIVATE);


            for (int i = 0; i < 4; i++) {
                int data = Integer.parseInt(sharedPreferences.getString(valuesCache[i], "0"));
                values.add(new PieEntry(data, valuesCache[i]));
            }
            PieDataSet dataSet = new PieDataSet(values, "Types"); // partner acts as legend
            dataSet.setSelectionShift(5f);
            dataSet.setSliceSpace(3f);
            dataSet.setColors(Color.DKGRAY);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(15f);
            data.setValueTextColor(Color.WHITE);

            pieChart.setData(data);
            pieChart.invalidate();
            pieChart.setRotationEnabled(false);
    }

}



