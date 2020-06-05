package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


        sp = getSharedPreferences("district", Context.MODE_PRIVATE);
        String zone = sp.getString("sign", "");


        // if the user is in red zone this is displayed as :
        if (zone.equalsIgnoreCase("Green")) {

            status.setText(R.string.safe);
            cardView.setCardBackgroundColor(getColor(R.color.green));
        } else {
            // for yellow and green zone
            status.setText(R.string.danger);
            cardView.setCardBackgroundColor(getColor(R.color.red));
        }

        adaptercontact adaptercontact = new adaptercontact(this, tag, number);
        list = findViewById(R.id.contact_id);
        list.setAdapter(adaptercontact);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number[position]));

                    if (ContextCompat.checkSelfPermission(view.getContext(),
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                Danger_area.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                100);
                    }

                    // for granted permission make a call
                if (ContextCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED){startActivity(callIntent);}


            }
        });

    }
}
