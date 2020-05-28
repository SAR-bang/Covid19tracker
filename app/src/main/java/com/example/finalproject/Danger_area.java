package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ListView;

public class Danger_area extends AppCompatActivity {


    ListView list;
    String tag[] = {"ambulance", "police"};
    long[] number = {+988864554, +988864645};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_area);
        adaptercontact adaptercontact = new adaptercontact(this,tag,number);
        list = findViewById(R.id.contact_id);
        list.setAdapter(adaptercontact);

    }
}
