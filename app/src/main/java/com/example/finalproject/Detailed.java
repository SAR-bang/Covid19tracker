package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detailed extends AppCompatActivity {

    TextView tvcountry, tvconfirmed, Activecases, deathcases,recoveredcases,tvcritical,tvtested;
    ImageView imageView;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Activecases = findViewById(R.id.active_id);
        deathcases = findViewById(R.id.dead_id);
        recoveredcases =findViewById(R.id.recovered_id);
        tvcritical = findViewById(R.id.critical_id);
        tvtested = findViewById(R.id.tested_id);
        tvconfirmed= findViewById(R.id.confirmed_id);

        tvcountry = findViewById(R.id.tvCountry);
        imageView = findViewById(R.id.imageView);
        loader = findViewById(R.id.loader);


        // getting the data from the previous intente
        Intent intent = getIntent();
        String country = intent.getStringExtra("country");
        String confirmed = intent.getStringExtra("confirmed");
        String dead = intent.getStringExtra("dead");
        String recovered = intent.getStringExtra("recovered");
        String flag = intent.getStringExtra("flag");
        String critical = intent.getStringExtra("critical");
        String active = intent.getStringExtra("active");
        String test = intent.getStringExtra("tested");


        // setting the text from the previous intent

        Activecases.setText(active);
        deathcases.setText(dead);
        recoveredcases.setText(recovered);
        tvcountry.setText(country);
        tvcritical.setText(critical);
        tvconfirmed.setText(confirmed);
        tvtested.setText(test);
        Picasso.with(Detailed.this).load(flag).into(imageView);
        loader.setEnabled(false);

        // disabling the progress bar

    }
}
