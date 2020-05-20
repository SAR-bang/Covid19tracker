package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private TextView textView_load;
    private TextView loadNews;
    private TextView danger_area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView_load = findViewById(R.id.load_text);
        textView_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,homePage.class));
            }
        });



        // next text


        loadNews = findViewById(R.id.news_text);
        loadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, homePage.class));
            }
        });

            // GPS location

        danger_area = findViewById(R.id.danger_text);
        danger_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Danger_area.class));
            }
        });

    }


}
