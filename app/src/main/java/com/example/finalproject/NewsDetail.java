package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetail extends AppCompatActivity {

    ImageView imageView, imageBack, imagemenu;
    TextView tv_source, tv_date, tv_summary, tv_title;
    ProgressBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // intializing the views;

        tv_source = findViewById(R.id.tvSource);
        tv_date = findViewById(R.id.tvdate);
        tv_summary = findViewById(R.id.tvDesc);
        tv_title = findViewById(R.id.tvTitle);
        imageView = findViewById(R.id.imageView);
        imageBack = findViewById(R.id.imageback);
        imagemenu = findViewById(R.id.menubtn);

        bar = findViewById(R.id.loader);

        // extracting the data send from previous intent
        Intent intent = getIntent();
        String source = intent.getStringExtra("Source");
        String date = intent.getStringExtra("date");
        String imageurl = intent.getStringExtra("image");
        String summary = intent.getStringExtra("summary");
        String Title = intent.getStringExtra("Title");

        // setting the data

        tv_title.setText(Title);
        tv_summary.setText(summary);
        tv_date.setText(date);
        tv_source.setText(source);
        Picasso.with(NewsDetail.this).load(imageurl).into(imageView);
        bar.setEnabled(false);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsDetail.this, News.class));
            }
        });

        imagemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsDetail.this, Profile.class));
            }
        });

    }


}
