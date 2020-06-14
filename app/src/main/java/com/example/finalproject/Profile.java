package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.applozic.mobicommons.commons.core.utils.Utils;

import java.util.ArrayList;
import java.util.List;


import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.users.KMUser;


public class Profile extends AppCompatActivity {

    private TextView textView_load;
    private TextView loadNews;
    private TextView danger_area;
    private TextView logout;
    private TextView recommend_text;


    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView_load = findViewById(R.id.load_text);
        logout = findViewById(R.id.logout_text);
        recommend_text = findViewById(R.id.recommend_text);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                // deleting the login data
                // now opening the main page
                startActivity(new Intent(Profile.this, homePage.class));
            }
        });


        textView_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, homePage.class));
            }
        });


        // next text


        loadNews = findViewById(R.id.news_text);
        loadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, News.class));
            }
        });

        // GPS location

        danger_area = findViewById(R.id.danger_text);
        danger_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Danger_area.class));
            }
        });


        // integrating the chatbot

        recommend_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kommunicate.init(Profile.this, "6cb97199a62e740dfda34722ae780f1");
                //        intializing the kommunicate to enable the chatbot
                //        the kommincate should be intialized at first
                // the kommunication provides KmConversationBuilder to create and
                //        launch  conversation directly


                // for pre registered users

                KMUser user = new KMUser();
                user.setUserId("1221"); // Pass a unique key
                user.setDisplayName("Addy"); //Optional


                //  opening the screen
                //  Kommunicate.openConversation(MainActivity.this);

                // fro a  callback
                Kommunicate.openConversation(Profile.this, null, new KmCallback() {
                    @Override
                    public void onSuccess(Object message) {
                    }

                    @Override
                    public void onFailure(Object error) {
                    }
                });


                // adding the bot
                List<String> botIds = new ArrayList<>();
                botIds.add("addy-bez4g"); // Add BOT_ID(s) to this list. Go to Manage Bots(https://dashboard.kommunicate.io/bots/manage-bots) -> Copy botID


                // if you want to add messages just add .setWithPreChat(true)

                new KmConversationBuilder(Profile.this)
                        .setSingleConversation(true)
                        .setConversationTitle("Test for Corona")
                        .setAgentIds(botIds)
                        .createConversation(new KmCallback() {
                            @Override
                            public void onSuccess(Object message) {
                                Log.d("Conversation", "Success : " + message);
                            }

                            @Override
                            public void onFailure(Object error) {
                                Log.d("Conversation", "Failure : " + error);
                            }
                        });


            }
        });
    }


}
