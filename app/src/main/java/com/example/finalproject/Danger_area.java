package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Danger_area extends AppCompatActivity {


    ListView list;
    String tag[] = {"ambulance", "police", "Doctor Consult"};
    int[] number = {+988864554, +988864645, +071555555};
    TextView status;
    SharedPreferences sp;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_area);
        status = findViewById(R.id.status_id);
        cardView = findViewById(R.id.userstatus);


        sp =getSharedPreferences("district", Context.MODE_PRIVATE);
        String zone = sp.getString("sign", "");


        // if the user is in red zone this is displayed as :
        if(zone.equalsIgnoreCase("Green")) {
            status.setText(R.string.danger);
            cardView.setCardBackgroundColor(getColor(R.color.red));
        }else{
            // for yellow and green zone
            status.setText(R.string.safe);
            cardView.setCardBackgroundColor(getColor(R.color.green));
        }

        adaptercontact adaptercontact = new adaptercontact(this,tag,number);
        list = findViewById(R.id.contact_id);
        list.setAdapter(adaptercontact);


    }
}
