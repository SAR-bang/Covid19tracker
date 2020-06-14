package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class homefragment extends Fragment {

    private AdapterRV imgadapter;
    private String worldPreferences = "worldPrefs";
    private String NepalPreferences = "NepPrefs";
    private PieChart pieChart;
    private Button adhik;
    private ImageButton cancel;

    private ArrayList<images> imagesList = new ArrayList<>();

    // shared preferences is used to store data of the application
    private SharedPreferences sharedPreferences;
    String countryNamet = "country";
    String casesT = "Confirmed";
    String activeT = "Active";
    String recoveredT = "Recovered";
    String DeathT = "Deaths";


    String cases = "";
    String active = "";
    String recovered = "";
    String Death = "";
    TextView global;

    private ArrayList<images> imagesList2 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        RequestQueue queue2 = Volley.newRequestQueue(container.getContext());

        final View view = inflater.inflate(R.layout.home_fragment, container, false);
        final TextView txtCases = view.findViewById(R.id.data_nepal);
        final TextView txtCases2 = view.findViewById(R.id.data_death);
        global = view.findViewById(R.id.caseworldwide);


        // code to return the pie chart
        pieChart = view.findViewById(R.id.chart);

        SetData(3, 100);
        adhik = view.findViewById(R.id.adhik);
        cancel = view.findViewById(R.id.cancel);

        // hiding the pie chart
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                cancel.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                pieChart.setVisibility(view.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });

        // showing the pie chart

        adhik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.setLayoutParams(new LinearLayout.LayoutParams(view.getWidth(), view.getHeight() / 2));
                pieChart.setVisibility(view.VISIBLE);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                params.setMargins(850, 0, 0, 0);
                cancel.setLayoutParams(params);


                cancel.setVisibility(View.VISIBLE);
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
//                Toast.makeText(getContext(),"Internet connection failed",Toast.LENGTH_SHORT);
//                // showing previously stored data
                try {
                    SharedPreferences sp = getActivity().getSharedPreferences(worldPreferences, Context.MODE_PRIVATE);
                    global.setText(sp.getString(casesT, ""));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Internet connection required", Toast.LENGTH_SHORT).show();
                }
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
                txtCases2.setText(Death);


                sharedPreferences = view.getContext().getSharedPreferences(NepalPreferences, Context.MODE_PRIVATE);


                // Saving the data in cache
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(countryNamet, "Nepal");
                editor.putString(casesT, cases);
                editor.putString(activeT, active);
                editor.putString(recoveredT, recovered);
                editor.putString(DeathT, Death);

                editor.commit();     //commit saves the data in the cache


                // retrieving the data from cache to notify user if data is added
                notifyuser(Long.parseLong(sharedPreferences.getString(casesT, "")) - apiModel.getCases());


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    SharedPreferences sp = getActivity().getSharedPreferences(NepalPreferences, Context.MODE_PRIVATE);
                    txtCases.setText(sp.getString(casesT, ""));
                    txtCases2.setText(sp.getString(DeathT, ""));
                } catch (Exception e) {
                }


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


        // for second recycler view
        final RecyclerView rv2 = view.findViewById(R.id.preventionlist);
        imagesList2.add(new images(R.drawable.p1, "बारम्बार हात धुनुहोस्"));
        imagesList2.add(new images(R.drawable.p2, "मास्क लगाउनुहोस्"));
        imagesList2.add(new images(R.drawable.p3, "घरमै बस"));
        imagesList2.add(new images(R.drawable.p4, "कीटाणुरहित"));
        imagesList2.add(new images(R.drawable.p1, "बारम्बार हात धुनुहोस्"));
        imagesList2.add(new images(R.drawable.p2, "मास्क लगाउनुहोस्"));

        imgadapter = new AdapterRV(getActivity().getApplicationContext(), imagesList2);

        rv2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        view.findViewById(R.id.img_id);

        rv2.setAdapter(imgadapter);

        return view;
    }


    // this method is used to showcase the piechart


    //  labels stored in array
    String valuesCache[] = {casesT, recoveredT, activeT, DeathT};
    String values[] = {};
    // array values to store the value retrieving from the shared preferences


    //this sets the data into the pie chart
    private void SetData(int count, int range) {
        ArrayList<PieEntry> values = new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences(NepalPreferences, Context.MODE_PRIVATE);
        // to get the data saved in cache

        for (int i = 0; i < 4; i++) {
            int data = Integer.parseInt(sharedPreferences.getString(valuesCache[i], "0"));
            values.add(new PieEntry(data, valuesCache[i]));
        }

        PieDataSet dataSet = new PieDataSet(values, "Types"); // types acts as legend
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(3f);
        dataSet.setColors(R.color.km_resolve_status_item_divider_color);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setRotationEnabled(true);
    }


    // method to start a service which shows notification in case increase

    private void notifyuser(long l) {

        Intent intent = new Intent(getActivity().getBaseContext(), Aservice.class);
        intent.putExtra("new", l + "");

        getActivity().startService(intent);
    }
}



