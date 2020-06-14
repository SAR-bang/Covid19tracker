package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homePage extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        navigationView = findViewById(R.id.nav_bottom_id);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, new homefragment()).commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            int i = 0;

            switch (item.getItemId()) {
                case R.id.home_id:
                    selectedFragment = new homefragment();
                    break;
                case R.id.stat_id:
                    selectedFragment = new statisticsfragment();
                    break;
                case R.id.user:
                    i = 10;
                    selectedFragment = new userfragment();
                    break;

            }


            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.LoginPreferences, Context.MODE_PRIVATE);
            if ((sharedPreferences.getString("username", "").equals(""))) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, selectedFragment).commit();
                // this shows the selected fragment


            } else {
                if (i == 10) {
                    startActivity(new Intent(homePage.this, Profile.class));
                } else {

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, selectedFragment).commit();
                    // this shows the selected fragment
                }

            }
            return true;
        }
    };
}
