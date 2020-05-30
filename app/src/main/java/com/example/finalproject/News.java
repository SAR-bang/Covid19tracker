package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class News extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final VideoView videoView = findViewById(R.id.video_id);
        Button play = findViewById(R.id.play_id);
        Button pause = findViewById(R.id.pause_id);



        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("https://www.youtube.com/watch?v=7zzfdYShvQU");

        // setting media controller
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

    }
}
