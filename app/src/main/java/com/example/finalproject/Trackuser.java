package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Trackuser extends AppCompatActivity {

    EditText refname, district, municipality;
    TextView label;
    ImageButton back;
    TextView submit;
    Switch aSwitch;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String[] districtslist = new String[]{"Okhaldhunga", "Rasuwa", "Manang", "Mustang"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackuser);
        // intializing the data

        refname = findViewById(R.id.reference_name);
        district = findViewById(R.id.district_id);
        aSwitch = findViewById(R.id.switchButton);
        label = findViewById(R.id.municaplitylabel);
        municipality = findViewById(R.id.municipality_id);
        submit = findViewById(R.id.btn_submit);
        back = findViewById(R.id.back);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    label.setVisibility(View.VISIBLE);
                    municipality.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(80, 20, 0, 0);
                    label.setLayoutParams(params);
                    params.setMargins(80, 10, 0, 0);
                    municipality.setLayoutParams(params);
                } else {
                    label.setVisibility(View.INVISIBLE);
                    municipality.setVisibility(View.INVISIBLE);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
                    params.setMargins(0, 10, 0, 0);
                    label.setLayoutParams(params);
                    municipality.setLayoutParams(params);
                }
            }
        });


        // for back properties

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Trackuser.this, Profile.class));
            }
        });

        // for registration in firebase

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ref = refname.getText().toString().trim();
                String Districtname = district.getText().toString().trim();
                if (aSwitch.isChecked()) {
                    String municipalityname = municipality.getText().toString().trim();
                    // calling the method to register the victims
                    Register(ref, Districtname, municipalityname);
                }
                Register(ref, Districtname, "");
                startActivity(new Intent(Trackuser.this, Danger_area.class));

            }
        });

    }


    private void Register(String ref, String districtname, String s) {
        try {
            // storing the victims data in firebase data

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("victims");
            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            LocationModel locationModel = new LocationModel(id, ref, districtname, s);
            databaseReference.child(id).setValue(locationModel);

            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            // Saving the data in cache
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("district", districtname);
            boolean isred = true;
            for (String district : districtslist) {
                if (district.equalsIgnoreCase(districtname)) {
                    editor.putString("sign", "green");
                    isred = false;
                }
            }
            if (isred) {
                editor.putString("sign", "red");
            }

            editor.commit();
            // commit is used to store the data in cache


        } catch (Exception e) {
            Toast.makeText(Trackuser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
