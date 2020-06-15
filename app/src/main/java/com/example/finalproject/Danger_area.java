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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Danger_area extends AppCompatActivity {


    ListView list;
    String tag[] = {"ambulance", "police", "Doctor Consult", "local leader"};
    int[] number = {+988864554, +988864645, +07155555555, +78587575};
    TextView status;
    SharedPreferences sp;
    CardView cardView;
    Intent callIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_area);
        status = findViewById(R.id.status_id);
        cardView = findViewById(R.id.userstatus);


        adaptercontact adaptercontact = new adaptercontact(this, tag, number);
        list = findViewById(R.id.contact_id);
        list.setAdapter(adaptercontact);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        String zone = sp.getString("sign", "");


        // if the user is in red zone this is displayed as :
        if (zone.equalsIgnoreCase("red")) {

            status.setTextColor(getColor(R.color.white));
            status.setText(R.string.danger);
            cardView.setCardBackgroundColor(getColor(R.color.red));

        } else {
            // for yellow and green zone
            status.setTextColor(getColor(R.color.white));
            status.setText(R.string.safe);
            cardView.setCardBackgroundColor(getColor(R.color.green));
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                callIntent = new Intent(Intent.ACTION_CALL);
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
                        == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }


            }
        });

    }


    //     on request granted the phone call is made else the toast message is shown
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            } else {
                Toast.makeText(Danger_area.this,
                        "Phone call Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


}
