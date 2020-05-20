package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homePage extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        navigationView = findViewById(R.id.nav_bottom_id);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id,new homefragment()).commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.home_id:
                    selectedFragment = new homefragment();
                    break;
                case R.id.stat_id:
                    selectedFragment = new statisticsfragment();
                    break;
                case R.id.user:
                    selectedFragment = new userfragment();
                    break;
            }

            // displaying the selected fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_id,selectedFragment).commit();
            return true;
        }
    };
}
